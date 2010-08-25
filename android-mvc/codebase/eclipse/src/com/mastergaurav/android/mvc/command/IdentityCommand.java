package com.mastergaurav.android.mvc.command;

import com.mastergaurav.android.mvc.common.IResponseListener;
import com.mastergaurav.android.mvc.common.Request;
import com.mastergaurav.android.mvc.common.Response;

public class IdentityCommand extends AbstractBaseCommand implements ICommand
{
	public void execute()
	{
		Request request = getRequest();
		Response response = new Response(request.getTag(), request.getData());
		response.setError(false);
		response.setTargetActivityID(request.getActivityID());

		setResponse(response);
		notifyListener(true);
	}

	protected void notifyListener(boolean success)
	{
		IResponseListener responseListener = getResponseListener();
		if(responseListener != null)
		{
			responseListener.onSuccess(getResponse());
		}
	}
}
