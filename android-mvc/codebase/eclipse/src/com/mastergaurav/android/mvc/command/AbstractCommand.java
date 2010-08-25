package com.mastergaurav.android.mvc.command;

import com.mastergaurav.android.mvc.common.IResponseListener;
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
public abstract class AbstractCommand extends AbstractBaseCommand
{
	public final void execute()
	{
		prepare();
		onBeforeExecute();
		go();
		onAfterExecute();

		Response response = getResponse();

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
		IResponseListener responseListener = getResponseListener();

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
}
