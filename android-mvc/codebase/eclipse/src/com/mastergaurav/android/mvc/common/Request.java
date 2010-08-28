package com.mastergaurav.android.mvc.common;

public class Request
{
	private Object tag;
	private Object data;
	private int activityID;

	public Request()
	{
	}

	public Request(Object tag, Object data)
	{
		this.tag = tag;
		this.data = data;
	}

	public Object getTag()
	{
		return tag;
	}

	public void setTag(Object tag)
	{
		this.tag = tag;
	}

	public Object getData()
	{
		return data;
	}

	public void setData(Object data)
	{
		this.data = data;
	}

	public int getActivityID()
	{
		return activityID;
	}

	public void setActivityID(int activityID)
	{
		this.activityID = activityID;
	}
}
