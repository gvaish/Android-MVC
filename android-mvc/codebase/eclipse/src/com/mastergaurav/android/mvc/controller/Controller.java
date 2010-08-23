package com.mastergaurav.android.mvc.controller;

import java.util.HashMap;

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

	// public static final int ACTIVITY_ID_PROGRESS = 6;
	// Useful for moving from error to previous or hitting the "Back" button

	private BaseActivity currentActivity;
	// private Activity errorActivity;

	private final HashMap<Integer, Class<? extends BaseActivity>> registeredActivities = new HashMap<Integer, Class<? extends BaseActivity>>();

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

	@Override
	public void onCreate()
	{
		theInstance = this;
		super.onCreate();

		registeredActivities.clear();
		// 1 to 10 are reserved. or better, use enums?
		registeredActivities.put(11, MainActivity.class);
		registeredActivities.put(87945, HomeActivity.class);

		CommandExecutor.getInstance().ensureInitialized();
	}

	public void registerActivity(int id, Class<? extends BaseActivity> clz)
	{
		registeredActivities.put(id, clz);
	}

	public void unregisterActivity(int id)
	{
		registeredActivities.remove(id);
	}

	// TODO: Get the initialization data, if available
	public void onActivityCreating(BaseActivity activity)
	{
		// activity.initialize(null);
	}

	public void onActivityCreated(BaseActivity activity)
	{
		// if(activity instanceof ProgressActivity)
		// {
		// progressActivity = activity;
		// } else if(activity instanceof ErrorActivity)
		// {
		// errorActivity = activity;
		// } else
		// {
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

	// public void go(int commandID, Request request, IResponseListener
	// listener)
	// {
	// go(commandID, request, listener, true);
	// }

	public void go(int commandID, Request request, IResponseListener listener, boolean showProgress)
	{
		Object[] newTag = {
			request.getTag(), listener
		};
		request.setTag(newTag);

		System.out.println("Enqueue command");
		CommandExecutor.getInstance().enqueueCommand(commandID, request, this);
		System.out.println("Enqueued command");
	}

	// private void stopError()
	// {
	// if(errorActivity != null)
	// {
	// errorActivity.finish();
	// errorActivity = null;
	// }
	// }

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

	// TODO: Look at the response's screenID / resultID
	// FIXME: Need to know whether we're
	private void processResponse(Message msg)
	{
		// stopProgress();
		// stopError();
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
			if(targetActivityID == 0)
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
}
