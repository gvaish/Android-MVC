package com.mastergaurav.android.common.view;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;

import com.mastergaurav.android.mvc.common.IResponseListener;
import com.mastergaurav.android.mvc.common.Request;
import com.mastergaurav.android.mvc.common.Response;

public interface IViewActivity extends IResponseListener
{
	void preProcessData(Response response);
	void processData(Response response);

	void onBeforeCreate(Bundle savedInstanceState);
	void onAfterCreate(Bundle savedInstanceState);
	void onCreateContent(Bundle savedInstanceState);
	
	View createContentView();
	int getContentViewID();
	
	boolean hasCustomOptionsMenu();
	boolean createCustomOptionsMenu(Menu menu);

	void showProgress();
	void hideProgress();
	boolean doNativeBack();
	void back();

	void finish();

	void go(int commandID, Request request);
	void go(int commandID, Request request, boolean showProgress);
	void go(int commandID, Request request, boolean showProgress, boolean record);
	void go(int commandID, Request request, boolean showProgress, boolean record, boolean resetStack);

	void startActivity(Intent intent);
}

