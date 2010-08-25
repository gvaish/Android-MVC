package com.mastergaurav.android.mvc.controller;

import java.util.HashMap;
import java.util.Stack;

import android.app.Application;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;

import com.mastergaurav.android.app.view.HomeActivity;
import com.mastergaurav.android.app.view.MainActivity;
import com.mastergaurav.android.common.view.BaseActivity;
import com.mastergaurav.android.mvc.command.CommandExecutor;
import com.mastergaurav.android.mvc.common.IResponseListener;
import com.mastergaurav.android.mvc.common.Request;
import com.mastergaurav.android.mvc.common.Response;

public class Controller extends Application implements IResponseListener
{
	private static Controller theInstance;

	public static final int ACTIVITY_ID_UPDATE_SAME = 0;
	public static final int ACTIVITY_ID_SHOW_PREVIOUS = 1;

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
		registeredActivities.put(11, MainActivity.class);
		registeredActivities.put(87945, HomeActivity.class);

		CommandExecutor.getInstance().ensureInitialized();
	}

	// TODO: Get the initialization data, if available
	public void onActivityCreating(BaseActivity activity)
	{
		// activity.initialize(null);
	}

	public void onActivityCreated(BaseActivity activity)
	{
		if(currentActivity != null)
		{
			currentActivity.finish();
		}
		currentActivity = activity;
		// TODO/FIXME: Get relevant data
		// currentActivity.processInitialData(response)
		// currentActivity.loadMemento(savedMemento)
		// }
	}

	// TODO: Do house keeping of saved memento
	// TODO: Is the instance of activity required as a parameter. I think
	// currentActivity should just do all fine
	public void onActivityPaused()
	{
		if(currentActivity != null)
		{
			// currentActivity.saveMemento()
		}
	}

	public void go(int commandID, Request request, IResponseListener listener)
	{
		go(commandID, request, listener, true);
	}

	public void go(int commandID, Request request, IResponseListener listener, boolean record)
	{
		// if(!record) => Don't push it on stack
		// if(record) => push it on stack

		currentNavigationDirection = NavigationDirection.Forward;

		Object[] newTag = {
			request.getTag(), listener
		};
		request.setTag(newTag);

		System.out.println("Enqueue command");
		CommandExecutor.getInstance().enqueueCommand(commandID, request, this);
		System.out.println("Enqueued command");
		
		//ActivityStackInfo item = new ActivityStackInfo(activityID, commandID, request)
	}

	public void back()
	{
		currentNavigationDirection = NavigationDirection.Backward;
		//
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

	private void processResponse(Message msg)
	{
		System.out.println("Handle Message [thread]: " + Thread.currentThread().getName());
		System.out.println("Handle Message [what]: " + msg.what);
		System.out.println("Handle Message [obj]: " + msg.obj);

		Response response = (Response) msg.obj;
		System.out.println("Handle Message [isError]: " + response.isError());

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
			} else	// go back or forward
			{
				Class<? extends BaseActivity> cls = registeredActivities.get(targetActivityID);

				switch(currentNavigationDirection)
				{
					case Forward:
						//TODO: pop the command from the stack if(!info.isRecord())
						break;
					case Backward:
						//TODO: pop the last command from the stack
						break;
				}

				if(cls != null)
				{
					System.out.println("Will launch: " + cls);
					Intent launcherIntent = new Intent(currentActivity, cls);
					currentActivity.startActivity(launcherIntent);
					currentActivity.finish();
					// TODO: Manage the stack!
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
}
