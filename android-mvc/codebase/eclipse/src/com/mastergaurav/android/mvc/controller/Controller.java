package com.mastergaurav.android.mvc.controller;

import java.util.HashMap;
import java.util.Stack;

import android.app.Application;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;

import com.mastergaurav.android.app.view.HomeActivity;
import com.mastergaurav.android.app.view.LoginActivity;
import com.mastergaurav.android.common.log.Logger;
import com.mastergaurav.android.common.view.BaseActivity;
import com.mastergaurav.android.mvc.command.CommandExecutor;
import com.mastergaurav.android.mvc.common.IResponseListener;
import com.mastergaurav.android.mvc.common.Request;
import com.mastergaurav.android.mvc.common.Response;

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

	private BaseActivity currentActivity;

	private final HashMap<Integer, Class<? extends BaseActivity>> registeredActivities = new HashMap<Integer, Class<? extends BaseActivity>>();
	private Stack<ActivityStackInfo> activityStack = new Stack<ActivityStackInfo>();
	private NavigationDirection currentNavigationDirection;

	@Override
	public void onCreate()
	{
		super.onCreate();
		theInstance = this;

		registeredActivities.clear();
		// 1 to 10 are reserved. or better, use enums?
		registeredActivities.put(11, LoginActivity.class);
		registeredActivities.put(87945, HomeActivity.class);

		CommandExecutor.getInstance().ensureInitialized();
	}

	public void onActivityCreating(BaseActivity activity)
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
		Logger.i(TAG, "go with cmdid=" + commandID + ", record: " + record + ",rs: " + resetStack + ", request: "
				+ request);
		if(resetStack)
		{
			activityStack.clear();
		}

		currentNavigationDirection = NavigationDirection.Forward;

		ActivityStackInfo info = new ActivityStackInfo(commandID, request, record, resetStack);
		activityStack.add(info);

		Object[] newTag = {
			request.getTag(), listener
		};
		request.setTag(newTag);

		Logger.i(TAG, "Enqueue-ing command");
		CommandExecutor.getInstance().enqueueCommand(commandID, request, this);
		Logger.i(TAG, "Enqueued command");
	}

	public void back()
	{
		Logger.i(TAG, "ActivityStack Size: " + activityStack.size());
		if(activityStack != null && activityStack.size() != 0)
		{
			if(activityStack.size() >= 2)
			{
				// Throw-away the last command, but only if there are at least
				// two commands
				activityStack.pop();
			}

			currentNavigationDirection = NavigationDirection.Backward;
			ActivityStackInfo info = activityStack.peek();
			CommandExecutor.getInstance().enqueueCommand(info.getCommandID(), info.getRequest(), this);
		}
	}

	private void processResponse(Message msg)
	{
		System.out.println("Handle Message [thread]: " + Thread.currentThread().getName());
		System.out.println("Handle Message [what]: " + msg.what);
		System.out.println("Handle Message [obj]: " + msg.obj);

		Response response = (Response) msg.obj;
		System.out.println("Handle Message [isError]: " + response.isError());

		ActivityStackInfo top = activityStack.peek();
		top.setResponse(response);
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
				System.out.println("Original listener: " + originalListener);
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
				Class<? extends BaseActivity> cls = registeredActivities.get(targetActivityID);
				Logger.i(TAG, "Launching new activity // else, current Direction: " + currentNavigationDirection);

				int asize = activityStack.size();
				Logger.i(TAG, "Current Stack Size (before processing): " + asize);

				switch(currentNavigationDirection)
				{
					case Forward:
						if(asize >= 2)
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
				Logger.i(TAG, "Current Stack Size (after processing): " + activityStack.size());

				if(cls != null)
				{
					Logger.i(TAG, "Will launch: " + cls);
					Intent launcherIntent = new Intent(currentActivity, cls);
					currentActivity.startActivity(launcherIntent);
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
