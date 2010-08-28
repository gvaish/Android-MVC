package com.mastergaurav.android.app.common;

import com.mastergaurav.android.app.command.CommandID;
import com.mastergaurav.android.app.command.LoginCommand;
import com.mastergaurav.android.app.view.ActivityID;
import com.mastergaurav.android.app.view.HomeActivity;
import com.mastergaurav.android.app.view.LoginActivity;
import com.mastergaurav.android.mvc.command.CommandExecutor;
import com.mastergaurav.android.mvc.controller.Controller;

public class Initializer
{
	public static void ensureInitialized()
	{
		Controller ctrl = Controller.getInstance();
		if(ctrl != null)
		{
			ctrl.registerActivity(ActivityID.ACTIVITY_ID_MAIN, LoginActivity.class);
			ctrl.registerActivity(ActivityID.ACTIVITY_ID_HOME, HomeActivity.class);
		}

		CommandExecutor ce = CommandExecutor.getInstance();
		if(ce != null)
		{
			ce.registerCommand(CommandID.LOGIN_DO, LoginCommand.class);
		}
	}
}
