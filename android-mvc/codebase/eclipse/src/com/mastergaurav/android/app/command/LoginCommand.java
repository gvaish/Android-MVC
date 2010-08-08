package com.mastergaurav.android.app.command;

import com.mastergaurav.android.mvc.command.AbstractCommand;
import com.mastergaurav.android.mvc.common.Request;
import com.mastergaurav.android.mvc.common.Response;
import com.mastergaurav.android.mvc.model.LoginRequest;
import com.mastergaurav.android.mvc.model.LoginResponse;

public class LoginCommand extends AbstractCommand
{
	@Override
	public void go()
	{

		try
		{
			Thread.sleep(3000);
		} catch(InterruptedException e)
		{
			e.printStackTrace();
		}

		Request request = getRequest();
		System.out.println("Request: " + request);
		Object data = request.getData();

		LoginRequest lr = (LoginRequest) data;

		LoginResponse resp = new LoginResponse();

		if(lr.getUsername().equals("1") && lr.getPassword().equals("1"))
		{
			resp.setSuccessful(true);
		} else
		{
			resp.setSuccessful(false);
			resp.setResponseCode(123);
			resp.setResponseMessage("Error in Login");
		}

		Response r = new Response(request.getTag(), resp);
		r.setError(!resp.isSuccessful());
		r.setTargetScreenID(87945);
		setResponse(r);
		System.out.println("LoginCommand::go => " + r.isError());
	}
}
