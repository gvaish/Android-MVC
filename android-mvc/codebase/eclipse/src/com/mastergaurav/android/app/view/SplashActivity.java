package com.mastergaurav.android.app.view;

import java.util.Timer;
import java.util.TimerTask;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.Gravity;
import android.view.View;
import android.widget.TextView;

import com.mastergaurav.android.app.common.Initializer;
import com.mastergaurav.android.common.log.Logger;
import com.mastergaurav.android.common.view.BaseActivity;
import com.mastergaurav.android.mvc.command.CommandExecutor;
import com.mastergaurav.android.mvc.common.Request;

public class SplashActivity extends BaseActivity
{
	private static final String TAG = "App-View";
	private Handler maLauncher = new Handler()
	{
		public void handleMessage(Message msg)
		{
			launchMainActivity();
		}
	};

	@Override
	protected View createContentView()
	{
		TextView tv = new TextView(this);
		tv.setTextSize(40);
		tv.setGravity(Gravity.CENTER);
		tv.setText("Splash");

		return tv;
	}

	@Override
	protected void onBeforeCreate(Bundle savedInstanceState)
	{
		Initializer.ensureInitialized();
	}

	@Override
	protected void onAfterCreate(Bundle savedInstanceState)
	{
		TimerTask task = new TimerTask()
		{
			@Override
			public void run()
			{
				Logger.i(TAG, "SplashActivity//task//run");
				maLauncher.sendEmptyMessage(100);
			}
		};

		new Timer().schedule(task, 2000);
	}

	private void launchMainActivity()
	{
		Request request = new Request();
		request.setTag("splash-shared");
		request.setData(ActivityID.ACTIVITY_ID_MAIN);
		go(CommandExecutor.COMMAND_ID_IDENTITY, request, false, true, true);
	}
}
