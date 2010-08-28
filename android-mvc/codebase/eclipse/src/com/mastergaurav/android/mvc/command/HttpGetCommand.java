package com.mastergaurav.android.mvc.command;

import java.net.URI;

public class HttpGetCommand extends AbstractHttpCommand
{
	public HttpGetCommand(URI uri)
	{
		setURI(uri);
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
