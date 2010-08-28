package com.mastergaurav.android.mvc.common;

public interface IMemento
{
	Object saveMemento();

	void loadMemento(Object savedMemento);
}
