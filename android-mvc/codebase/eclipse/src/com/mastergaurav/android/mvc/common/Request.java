package com.mastergaurav.android.mvc.common;

public class Request
{
	private Object tag;
	private Object data;
	private int screenID;

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

	public int getScreenID()
	{
		return screenID;
	}

	public void setScreenID(int screenID)
	{
		this.screenID = screenID;
	}
}
