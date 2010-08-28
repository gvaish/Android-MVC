package com.mastergaurav.android.mvc.common;

public class Response
{
	private Object tag;
	private Object data;
	private boolean error;
	private int targetActivityID;

	public Response()
	{
	}

	public Response(Object tag, Object data)
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

	public boolean isError()
	{
		return error;
	}

	public void setError(boolean error)
	{
		this.error = error;
	}

	public int getTargetActivityID()
	{
		return targetActivityID;
	}

	public void setTargetActivityID(int targetActivityID)
	{
		this.targetActivityID = targetActivityID;
	}
}
