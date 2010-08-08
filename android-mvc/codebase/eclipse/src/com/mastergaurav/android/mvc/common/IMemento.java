/**
 * IMemento.java $version 1.0 Mar 16, 2010
 * 
 * Copyright 2010 NHN Corp. All rights Reserved.
 * NHN PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */

package com.mastergaurav.android.mvc.common;

/**
 * 
 * @author Accenture India
 */
public interface IMemento
{
	Object saveMemento();

	void loadMemento(Object savedMemento);
}
