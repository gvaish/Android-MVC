package com.mastergaurav.android.app.view;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.mastergaurav.android.R;
import com.mastergaurav.android.app.command.CommandID;
import com.mastergaurav.android.app.model.LoginRequest;
import com.mastergaurav.android.app.model.xml.LoginResponse;
import com.mastergaurav.android.common.log.Logger;
import com.mastergaurav.android.common.view.BaseActivity;
import com.mastergaurav.android.mvc.common.Request;
import com.mastergaurav.android.mvc.common.Response;

public class LoginActivity extends BaseActivity
{
	private static final String TAG = "App-View";

	@Override
	public void onCreateContent(Bundle savedInstanceState)
	{
		setContentView(R.layout.main);

		Button btn = (Button) findViewById(R.id.loginBtn);

		btn.setOnClickListener(new OnClickListener()
		{
			public void onClick(View v)
			{
				String username = ((EditText) findViewById(R.id.main_usernameEdit)).getText().toString();
				String password = ((EditText) findViewById(R.id.main_passwordEdit)).getText().toString();

				LoginRequest data = new LoginRequest(username, password);
				Object tag = "Login-Tag";

				Request request = new Request(tag, data);

				System.out.println("Starting controller...");
				go(CommandID.LOGIN_DO, request, true, true, true);
			}
		});
	}

	@Override
	public void onSuccess(Response response)
	{
		hideProgress();
		Logger.i(TAG, "Received some response: " + Thread.currentThread().getName());
		TextView tv = (TextView) findViewById(R.id.hw);
		tv.setText("Response: " + response.getData());
	}

	@Override
	public void onError(Response response)
	{
		hideProgress();
		System.out.println("Received some response: " + Thread.currentThread().getName());
		TextView tv = (TextView) findViewById(R.id.hw);

		Object data = response.getData();

		if(data instanceof Throwable)
		{
			Throwable t = (Throwable) data;
			tv.setText("Exception: " + t.getClass().getName() + "\n" + t.getMessage());
		} else if(data instanceof LoginResponse)
		{
			LoginResponse lr = (LoginResponse) data;
			tv.setText("Login Failed: " + lr.getResponse().getMessage());
		} else
		{
			tv.setText("Error Response: " + response.getData());
		}
	}
}
