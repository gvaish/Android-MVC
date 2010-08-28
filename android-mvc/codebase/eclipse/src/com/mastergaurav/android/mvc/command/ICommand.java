package com.mastergaurav.android.mvc.command;

import com.mastergaurav.android.mvc.common.IResponseListener;
import com.mastergaurav.android.mvc.common.Request;
import com.mastergaurav.android.mvc.common.Response;

public interface ICommand
{
	Request getRequest();

	void setRequest(Request request);

	Response getResponse();

	void setResponse(Response response);

	void execute();

	IResponseListener getResponseListener();

	void setResponseListener(IResponseListener listener);

	void setTerminated(boolean terminated);

	boolean isTerminated();
}
