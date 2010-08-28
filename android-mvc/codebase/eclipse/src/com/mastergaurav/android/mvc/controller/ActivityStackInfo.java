package com.mastergaurav.android.mvc.controller;

import com.mastergaurav.android.common.view.BaseActivity;
import com.mastergaurav.android.mvc.common.Request;
import com.mastergaurav.android.mvc.common.Response;

public class ActivityStackInfo
{
	private Class<? extends BaseActivity> activityClass;
	private int commandID;
	private Request request;
	private boolean record;
	private boolean resetStack;
	private Response response;

	public ActivityStackInfo()
	{
	}

	public ActivityStackInfo(int commandID, Request request, boolean record, boolean resetStack)
	{
		this.commandID = commandID;
		this.request = request;
		this.record = record;
		this.resetStack = resetStack;
	}

	public ActivityStackInfo(Class<? extends BaseActivity> activityClass, int commandID, Request request)
	{
		this.activityClass = activityClass;
		this.commandID = commandID;
		this.request = request;
	}

	public ActivityStackInfo(Class<? extends BaseActivity> activityClass, int commandID, Request request,
			boolean record, boolean resetStack)
	{
		this.activityClass = activityClass;
		this.commandID = commandID;
		this.request = request;
		this.record = record;
		this.resetStack = resetStack;
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

	public Response getResponse()
	{
		return response;
	}

	public void setResponse(Response response)
	{
		this.response = response;
	}

	public boolean isResetStack()
	{
		return resetStack;
	}

	public void setResetStack(boolean resetStack)
	{
		this.resetStack = resetStack;
	}
}
