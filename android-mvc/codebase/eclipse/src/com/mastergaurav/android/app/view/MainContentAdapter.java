package com.mastergaurav.android.app.view;

import java.util.Collection;
import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.mastergaurav.android.R;
import com.mastergaurav.android.common.view.BaseActivity;

public class MainContentAdapter extends BaseAdapter
{

	private BaseActivity activity;
	private List<String> items;

	public MainContentAdapter(BaseActivity activity, List<String> items)
	{
		this.activity = activity;
		this.items = items;
	}

	public BaseActivity getActivity()
	{
		return activity;
	}

	// TODO: Ensure that the method is thread-safe
	public void addItem(String item)
	{
		items.add(item);
		notifyDataSetChanged();
	}

	public void addAll(Collection<String> toAdd)
	{
		items.addAll(toAdd);
		notifyDataSetChanged();
	}

	public void updateData(List<String> newItems)
	{
		this.items = newItems;
		System.out.println("Data changed...");
		notifyDataSetChanged();
	}

	public int getCount()
	{
		System.out.println("MainContentAdapter::getCount => " + items.size());
		return items.size();
	}

	public Object getItem(int position)
	{
		System.out.println("MainContentAdapter::getItem@:: " + position + " => " + items.get(position));
		return items.get(position);
	}

	public long getItemId(int position)
	{
		System.out.println("MainContentAdapter::getItemId@ => " + position);
		return items.get(position).hashCode();
	}

	public View getView(int position, View convertView, ViewGroup parent)
	{
		ViewGroup toShow = null;
		TextView tv = null;

		System.out.println("MainContentAdapter::getView called");
		
		if(convertView != null)
		{
			System.out.println("convertView is NOT null, NOT creating anything...");
			toShow = (ViewGroup) convertView;
			tv = (TextView) toShow.findViewById(R.id.lha_tv);
		} else
		{
			System.out.println("convertView is null, creating something...");
			LayoutInflater li = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			toShow = (ViewGroup) li.inflate(R.layout.layout_home_activity_ca_item, null, false);
			tv = (TextView) toShow.findViewById(R.id.lha_tv);
		}

		if(tv != null)
		{
			tv.setText(items.get(position));
		}

		System.out.println("Got the view with text: " + items.get(position));
		System.out.println("Something to be shown is: " + toShow);
		return toShow;
	}

}
