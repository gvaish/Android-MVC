package com.mastergaurav.android.mvc.controller;

import java.util.HashMap;
import java.util.Stack;

import android.app.Activity;
import android.app.Application;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;

import com.mastergaurav.android.common.log.Logger;
import com.mastergaurav.android.common.view.BaseActivity;
import com.mastergaurav.android.common.view.IViewActivity;
import com.mastergaurav.android.mvc.command.CommandExecutor;
import com.mastergaurav.android.mvc.common.IResponseListener;
import com.mastergaurav.android.mvc.common.Request;
import com.mastergaurav.android.mvc.common.Response;

@SuppressWarnings("rawtypes")
public class Controller extends Application implements IResponseListener
{
	private static final String TAG = "Controller";

	private static Controller theInstance;

	public static final int ACTIVITY_ID_UPDATE_SAME = 0;
	// will be handled using "back" method.
	// Assumption: Previous navigation is never triggered by a command execution
	// (similar to that on the web => Clicking a button/link/form-submit only
	// takes you forward)
	// public static final int ACTIVITY_ID_SHOW_PREVIOUS = 1;

	public static final int ACTIVITY_ID_BASE = 1000;

	private IViewActivity currentActivity;

	/**
	 * I cannot use Class<? extends Activity & IViewActivity>.
	 * See http://docs.oracle.com/javase/specs/jls/se7/html/jls-4.html#jls-4.5.1
	 */
	private final HashMap<Integer, Class> registeredActivities = new HashMap<Integer, Class>();
	private Stack<ActivityStackInfo> activityStack = new Stack<ActivityStackInfo>();
	private NavigationDirection currentNavigationDirection;

	@Override
	public void onCreate()
	{
		super.onCreate();
		theInstance = this;

		registeredActivities.clear();

		CommandExecutor.getInstance().ensureInitialized();
	}

	public void onActivityCreating(IViewActivity activity)
	{
		if(activityStack.size() > 0)
		{
			ActivityStackInfo info = activityStack.peek();
			if(info != null)
			{
				Response response = info.getResponse();
				activity.preProcessData(response);
			}
		}
	}

	public void onActivityCreated(BaseActivity activity)
	{
		if(currentActivity != null)
		{
			currentActivity.finish();
		}
		currentActivity = activity;
		int size = activityStack.size();

		if(size > 0)
		{
			ActivityStackInfo info = activityStack.peek();
			if(info != null)
			{
				Response response = info.getResponse();
				activity.processData(response);

				if(size >= 2 && !info.isRecord())
				{
					activityStack.pop();
				}
			}
		}
	}

	public void go(int commandID, Request request, IResponseListener listener, boolean record, boolean resetStack)
	{
		Logger.i(TAG, "[go] cmdid=" + commandID + ", rec: " + record + ",rs: " + resetStack + ", req: " + request);

		currentNavigationDirection = NavigationDirection.Forward;

		ActivityStackInfo info = new ActivityStackInfo(commandID, request, record, resetStack);
		activityStack.add(info);

		Object[] newTag = {
			request.getTag(), listener
		};
		request.setTag(newTag);

		Logger.v(TAG, "Enqueue-ing command");
		CommandExecutor.getInstance().enqueueCommand(commandID, request, this);
		Logger.v(TAG, "Enqueued command");
	}

	public void back()
	{
		Logger.i(TAG, "[back] ActivityStack Size: " + activityStack.size());
		if(activityStack != null && activityStack.size() != 0)
		{
			if(activityStack.size() == 1)
			{
				return;
			}
			if(activityStack.size() >= 2)
			{
				// Throw-away the last command, but only if there are at least two commands
				activityStack.pop();
			}

			currentNavigationDirection = NavigationDirection.Backward;
			ActivityStackInfo info = activityStack.peek();
			CommandExecutor.getInstance().enqueueCommand(info.getCommandID(), info.getRequest(), this);
		}
	}

	@SuppressWarnings("unchecked")
	private void processResponse(Message msg)
	{
		Logger.v(TAG, "[processResponse] thread: " + Thread.currentThread().getName());
		Logger.v(TAG, "[processResponse] what: " + msg.what);
		Logger.v(TAG, "[processResponse] obj: " + msg.obj);

		Response response = (Response) msg.obj;
		Logger.v(TAG, "[processResponse] isError: " + response.isError());

		ActivityStackInfo top = activityStack.peek();
		top.setResponse(response);
		
		//FIXME: if response is null, onError with null-response
		//FIXME: if response is error, onError irrespective of the targetActivityID
		if(response != null)
		{
			int targetActivityID = response.getTargetActivityID();
			Object[] newTag = (Object[]) response.getTag();
			Object tag = newTag[0];
			IResponseListener originalListener = (IResponseListener) newTag[1];
			response.setTag(tag);

			// self-update
			if(targetActivityID == ACTIVITY_ID_UPDATE_SAME)
			{
				if(originalListener != null)
				{
					if(!response.isError())
					{
						originalListener.onSuccess(response);
					} else
					{
						originalListener.onError(response);
					}
				}
			} else
			{
				Class cls = registeredActivities.get(targetActivityID);
				Logger.i(TAG, "[processResponse] Launching new activity, navDir: " + currentNavigationDirection);

				Logger.i(TAG, "[processResponse] Stack Size (pre): " + activityStack.size());
				Logger.i(TAG, "[processResponse] Reset stack? " + top.isResetStack());

				switch(currentNavigationDirection)
				{
					case Forward:
						if(top.isResetStack())
						{
							activityStack.clear();
							activityStack.add(top);
						} else if(activityStack.size() >= 2)
						{
							if(!top.isRecord())
							{
								activityStack.pop();
							}
						}
						break;
					case Backward:
						// Popping of the last command from the stack would have
						// happened in (back)
						// Just reset the navigation direction
						currentNavigationDirection = NavigationDirection.Forward;
						break;
				}
				Logger.i(TAG, "[processResponse] Stack Size (post): " + activityStack.size());

				if(cls != null)
				{
					Logger.i(TAG, "Will launch: " + cls);
					Intent launcherIntent = new Intent((Activity) currentActivity, cls);
					currentActivity.startActivity(launcherIntent);
					//FIXME: Show I finish the current activity? Why?
					// IF not, must change doNativeBack() to return "true" by default,
					//   or rather create doCustomBack() and use that.
					currentActivity.finish();
					top.setActivityClass(cls);
				}
			}
		}
	}

	public void registerActivity(int id, Class<? extends BaseActivity> clz)
	{
		registeredActivities.put(id, clz);
	}

	public void unregisterActivity(int id)
	{
		registeredActivities.remove(id);
	}

	private Handler handler = new Handler()
	{
		public void handleMessage(Message msg)
		{
			processResponse(msg);
		}
	};

	public static Controller getInstance()
	{
		return theInstance;
	}

	public void onError(Response response)
	{
		handleResponse(response);
	}

	public void onSuccess(Response response)
	{
		handleResponse(response);
	}

	private void handleResponse(Response response)
	{
		Message msg = new Message();
		msg.what = 0;
		msg.obj = response;
		handler.sendMessage(msg);
	}
}
