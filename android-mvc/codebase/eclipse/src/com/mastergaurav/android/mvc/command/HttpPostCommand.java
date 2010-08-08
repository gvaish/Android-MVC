package com.mastergaurav.android.mvc.command;

import java.net.URI;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.entity.ByteArrayEntity;

public abstract class HttpPostCommand extends AbstractHttpCommand
{

	protected HttpPostCommand(URI uri)
	{
		// super(uri);
	}

	protected abstract byte[] getPostBody();

	protected abstract String getContentType();

	@Override
	protected void onBeforeExecute(HttpRequestBase request)
	{
		byte[] body = getPostBody();
		request.addHeader("Content-Length", String.valueOf(body.length));

		HttpPost post = (HttpPost) request;

		ByteArrayEntity bodyData = new ByteArrayEntity(body);
		bodyData.setContentType(getContentType());
		post.setEntity(bodyData);
	}

	@Override
	protected Object getErrorResponse(HttpResponse response)
	{
		return null;
	}

	@Override
	protected Object getErrorResponse(Exception error)
	{
		return null;
	}

	protected HttpRequestBase getHttpCommand(URI uri)
	{
		return null;
	}
}
