package com.mastergaurav.android.app.common;

import java.util.ArrayList;
import java.util.List;

public class CollectionUtils
{
	public static <T> List<T> asList(T... args)
	{
		ArrayList<T> rv = new ArrayList<T>();

		for(T a : args)
		{
			rv.add(a);
		}

		return rv;
	}
}
