package com.mastergaurav.android.mvc.command;

import java.net.URI;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpRequestBase;

public class HttpGetCommand extends AbstractHttpCommand
{

	public HttpGetCommand(URI uri)
	{
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
		return new HttpGet(uri);
	}

	@Override
	protected byte[] getBody()
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected String getContentType()
	{
		// TODO Auto-generated method stub
		return null;
	}
}
