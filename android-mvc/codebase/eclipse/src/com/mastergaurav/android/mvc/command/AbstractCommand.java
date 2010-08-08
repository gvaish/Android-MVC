package com.mastergaurav.android.mvc.command;

import com.mastergaurav.android.mvc.common.IResponseListener;
import com.mastergaurav.android.mvc.common.Request;
import com.mastergaurav.android.mvc.common.Response;

/**
 * <p>
 * Steps for a general command:
 * </p>
 * <ol>
 * <li>Prepare: Initialize the command</li>
 * <li>onBeforeExecute: About to execute</li>
 * <li>go: Actual execution</li>
 * <li>onAfterExecute: Just executed. Notify the response listener.</li>
 * </ol>
 * 
 * @author Gaurav Vaish
 */
public abstract class AbstractCommand implements ICommand
{
	private Request request;
	private Response response;
	private IResponseListener responseListener;

	public final void execute()
	{
		prepare();
		onBeforeExecute();
		go();
		onAfterExecute();

		if(response != null)
		{
			notifyListener(!response.isError());
		}
	}

	protected void prepare()
	{
	}

	protected abstract void go();

	protected void onBeforeExecute()
	{
	}

	protected void onAfterExecute()
	{
	}

	protected void notifyListener(boolean success)
	{
		System.out.println("AbstractCommand::notifyListener => " + success + " => " + responseListener);
		if(responseListener != null)
		{
			if(success)
			{
				responseListener.onSuccess(getResponse());
			} else
			{
				responseListener.onError(getResponse());
			}
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
