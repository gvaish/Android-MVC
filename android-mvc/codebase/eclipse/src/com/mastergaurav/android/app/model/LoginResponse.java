package com.mastergaurav.android.app.model;

public class LoginResponse
{
	private boolean successful;
	private int responseCode;
	private String responseMessage;

	public LoginResponse()
	{
	}

	public LoginResponse(boolean successful, int responseCode, String responseMessage)
	{
		this.successful = successful;
		this.responseCode = responseCode;
		this.responseMessage = responseMessage;
	}

	public boolean isSuccessful()
	{
		return successful;
	}

	public void setSuccessful(boolean successful)
	{
		this.successful = successful;
	}

	public int getResponseCode()
	{
		return responseCode;
	}

	public void setResponseCode(int responseCode)
	{
		this.responseCode = responseCode;
	}

	public String getResponseMessage()
	{
		return responseMessage;
	}

	public void setResponseMessage(String responseMessage)
	{
		this.responseMessage = responseMessage;
	}
}
