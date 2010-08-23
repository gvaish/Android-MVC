package com.mastergaurav.android.mvc.command;

import java.net.URI;

import org.apache.http.HttpResponse;

public class HttpGetCommand extends AbstractHttpCommand
{
	public HttpGetCommand(URI uri)
	{
		setURI(uri);
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

	@Override
	protected byte[] getBody()
	{
		return null;
	}

	@Override
	protected String getContentType()
	{
		return null;
	}
}
