package com.mastergaurav.android.app.view;

import java.util.List;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ListView;

import com.mastergaurav.android.R;
import com.mastergaurav.android.app.common.CollectionUtils;
import com.mastergaurav.android.common.view.BaseActivity;

public class HomeActivity extends BaseActivity
{

	@Override
	protected int getContentViewID()
	{
		return R.layout.layout_screen_complex_list_view_activity;
	}

	private List<String> items1 = CollectionUtils.asList("One", "Two", "Three", "Four", "Five", "Six");
	private List<String> items2 = CollectionUtils.asList("2-One", "2-Two", "2-Three", "2-Four", "2-Five", "2-Six", "7-Seven");

	private int usedItem = 0;
	private MainContentAdapter adapter;

	@Override
	protected void onAfterCreate(Bundle savedInstanceState)
	{
		super.onAfterCreate(savedInstanceState);
		adapter = new MainContentAdapter(this, items1);

		Button btn = (Button) findViewById(R.id.complex_view_activity_refresh_btn);
		ListView lv = (ListView) findViewById(R.id.complex_view_activity_list);

		lv.setAdapter(adapter);
		System.out.println("List View -- have set the adapter");

		btn.setOnClickListener(new OnClickListener()
		{
			public void onClick(View v)
			{
				System.out.println("used item: " + usedItem);
				usedItem = usedItem == 0 ? 1 : 0;
				List<String> data = usedItem == 1 ? items2 : items1;
				System.out.println("used data: " + data);
				adapter.updateData(data);
			}
		});
	}

	@Override
	protected void onCreateContent(Bundle savedInstanceState)
	{
		super.onCreateContent(savedInstanceState);
	}
}
