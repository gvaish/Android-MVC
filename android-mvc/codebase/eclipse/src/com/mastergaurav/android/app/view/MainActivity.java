package com.mastergaurav.android.app.view;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

import com.mastergaurav.android.R;
import com.mastergaurav.android.common.view.BaseActivity;
import com.mastergaurav.android.mvc.command.CommandID;
import com.mastergaurav.android.mvc.common.Request;
import com.mastergaurav.android.mvc.common.Response;
import com.mastergaurav.android.mvc.model.LoginRequest;

public class MainActivity extends BaseActivity
{
	protected void onCreateContent(Bundle savedInstanceState)
	{
		setContentView(R.layout.main);

		Button btn = (Button) findViewById(R.id.loginBtn);

		btn.setOnClickListener(new OnClickListener()
		{
			public void onClick(View v)
			{
				LoginRequest data = new LoginRequest("1", "1");
				Object tag = "L";

				Request request = new Request(tag, data);

				System.out.println("Starting controller...");
				getController().go(CommandID.LOGIN_DO, request, MainActivity.this);
			}
		});
	}

	@Override
	public void onSuccess(Response response)
	{
		System.out.println("Received some response: " + Thread.currentThread().getName());
		TextView tv = (TextView) findViewById(R.id.hw);
		tv.setText("Response: " + response.getData());
	}

	@Override
	public void onError(Response response)
	{
		System.out.println("Received some response: " + Thread.currentThread().getName());
		TextView tv = (TextView) findViewById(R.id.hw);
		tv.setText("Error Response: " + response.getData());
	}

	@Override
	protected int getID()
	{
		return 12345;
	}
}
