package com.mastergaurav.android.mvc.command;

import com.mastergaurav.android.mvc.common.IResponseListener;
import com.mastergaurav.android.mvc.common.Request;
import com.mastergaurav.android.mvc.common.Response;

public class AbstractBaseCommand
{

	private Request request;
	private Response response;
	private IResponseListener responseListener;
	private boolean terminated;

	public AbstractBaseCommand()
	{
		super();
	}

	public Request getRequest()
	{
		return request;
	}

	public void setRequest(Request request)
	{
		this.request = request;
	}

	public Response getResponse()
	{
		return response;
	}

	public void setResponse(Response response)
	{
		this.response = response;
	}

	public IResponseListener getResponseListener()
	{
		return responseListener;
	}

	public void setResponseListener(IResponseListener responseListener)
	{
		this.responseListener = responseListener;
	}

	public boolean isTerminated()
	{
		return terminated;
	}

	public void setTerminated(boolean terminated)
	{
		this.terminated = terminated;
	}

}
