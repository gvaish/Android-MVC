package com.mastergaurav.android.app.view;

import java.util.Collection;
import java.util.List;

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

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
		return items.size();
	}

	public Object getItem(int position)
	{
		return items.get(position);
	}

	public long getItemId(int position)
	{
		return items.get(position).hashCode();
	}

	public View getView(int position, View convertView, ViewGroup parent)
	{

		TextView toShow = null;

		if(convertView != null)
		{
			toShow = (TextView) convertView;
		} else
		{
			toShow = new TextView(activity);
		}
		toShow.setText(items.get(position));

		System.out.println("Got the view with text: " + items.get(position));
		return toShow;
	}

}
