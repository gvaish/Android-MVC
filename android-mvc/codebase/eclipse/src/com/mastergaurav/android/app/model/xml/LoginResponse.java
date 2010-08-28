package com.mastergaurav.android.app.model.xml;

public class LoginResponse
{
	private Response response;
	private Profile profile;

	public Response getResponse()
	{
		return response;
	}

	public void setResponse(Response response)
	{
		this.response = response;
	}

	public Profile getProfile()
	{
		return profile;
	}

	public void setProfile(Profile profile)
	{
		this.profile = profile;
	}
}
