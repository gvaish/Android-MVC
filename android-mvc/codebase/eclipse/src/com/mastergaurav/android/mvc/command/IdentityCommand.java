package com.mastergaurav.android.mvc.command;

import com.mastergaurav.android.mvc.common.IResponseListener;
import com.mastergaurav.android.mvc.common.Request;
import com.mastergaurav.android.mvc.common.Response;

public class IdentityCommand implements ICommand
{
	private Request request;
	private Response response;
	private IResponseListener responseListener;

	public void execute()
	{
		Request request = getRequest();
		Response response = new Response(request.getTag(), request.getData());
		response.setTargetScreenID(request.getScreenID());

		setResponse(response);
		notifyListener(true);
	}

	protected void notifyListener(boolean success)
	{
		if(responseListener != null)
		{
			responseListener.onSuccess(getResponse());
		}
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
}
