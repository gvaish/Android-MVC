package com.mastergaurav.android.app.command;

import java.io.InputStream;
import java.net.URI;
import java.net.URLEncoder;
import java.text.MessageFormat;

import org.apache.http.HttpResponse;

import com.mastergaurav.android.app.model.LoginRequest;
import com.mastergaurav.android.app.model.xml.LoginResponse;
import com.mastergaurav.android.app.view.ActivityID;
import com.mastergaurav.android.common.xml.XMLDeserializer;
import com.mastergaurav.android.mvc.command.HttpPostCommand;
import com.mastergaurav.android.mvc.common.Request;
import com.mastergaurav.android.mvc.common.Response;
import com.mastergaurav.android.mvc.controller.Controller;

public class LoginCommand extends HttpPostCommand
{
	private static final String BODY_FORMAT = "username={0}&password={1}";

	public LoginCommand()
	{
		super(URI.create("http://android-mvc.sourceforge.net/svc/login.php"));
	}

	@Override
	protected String getContentType()
	{
		return "application/x-www-form-urlencoded";
	}

	@Override
	protected byte[] getBody()
	{
		Request req = getRequest();
		LoginRequest lr = (LoginRequest) req.getData();
		String username = lr.getUsername();
		String password = lr.getPassword();

		String u = URLEncoder.encode(username);
		String p = URLEncoder.encode(password);

		String bodyString = MessageFormat.format(BODY_FORMAT, u, p);

		return bodyString.getBytes();
	}

	@Override
	protected void onAfterExecute()
	{
		Response response = getResponse();
		if(response.isError())
		{
			response.setTargetActivityID(Controller.ACTIVITY_ID_UPDATE_SAME);
		} else
		{
			LoginResponse lr = (LoginResponse) response.getData();
			if(lr.getResponse().getCode() != 200 || lr.getProfile() == null)
			{
				response.setError(true);
				response.setTargetActivityID(Controller.ACTIVITY_ID_UPDATE_SAME);
			} else
			{
				response.setTargetActivityID(ActivityID.ACTIVITY_ID_HOME);
			}
		}
	}

	@Override
	protected Object getSuccessResponse(HttpResponse response)
	{
		Object origResponse = super.getSuccessResponse(response);
		if(origResponse instanceof InputStream)
		{
			InputStream input = (InputStream) origResponse;
			LoginResponse model = XMLDeserializer.getInstance().deserialize(input, LoginResponse.class);

			return model;
		}

		return origResponse;
	}
}
