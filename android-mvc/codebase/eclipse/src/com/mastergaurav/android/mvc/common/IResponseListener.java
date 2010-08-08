package com.mastergaurav.android.mvc.common;

public interface IResponseListener
{
	void onSuccess(Response response);

	void onError(Response response);
}
