package com.mastergaurav.android.mvc.controller;

import com.mastergaurav.android.common.view.BaseActivity;
import com.mastergaurav.android.mvc.common.Request;

public class ActivityStackInfo
{
	private Class<? extends BaseActivity> activityClass;
	private int commandID;
	private Request request;
	private boolean record;
	private boolean clearStack;

	public ActivityStackInfo()
	{
	}

	public ActivityStackInfo(Class<? extends BaseActivity> activityClass, int commandID, Request request)
	{
		this.activityClass = activityClass;
		this.commandID = commandID;
		this.request = request;
	}

	public ActivityStackInfo(Class<? extends BaseActivity> activityClass, int commandID, Request request,
			boolean record, boolean clearStack)
	{
		this.activityClass = activityClass;
		this.commandID = commandID;
		this.request = request;
		this.record = record;
		this.clearStack = clearStack;
	}

	public Class<? extends BaseActivity> getActivityClass()
	{
		return activityClass;
	}

	public void setActivityClass(Class<? extends BaseActivity> activityClass)
	{
		this.activityClass = activityClass;
	}

	public int getCommandID()
	{
		return commandID;
	}

	public void setCommandID(int commandID)
	{
		this.commandID = commandID;
	}

	public Request getRequest()
	{
		return request;
	}

	public void setRequest(Request request)
	{
		this.request = request;
	}

	public boolean isRecord()
	{
		return record;
	}

	public void setRecord(boolean record)
	{
		this.record = record;
	}

	public boolean isClearStack()
	{
		return clearStack;
	}

	public void setClearStack(boolean clearStack)
	{
		this.clearStack = clearStack;
	}
}
