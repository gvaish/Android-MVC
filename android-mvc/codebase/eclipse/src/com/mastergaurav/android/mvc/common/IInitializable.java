/**
 * IInitializable.java $version 1.0 Mar 16, 2010
 * 
 * Copyright 2010 NHN Corp. All rights Reserved.
 * NHN PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */

package com.mastergaurav.android.mvc.common;

/**
 * Repesents a component that can be initalized
 * 
 * @author Accenture India
 */
public interface IInitializable
{
	/**
	 * This method must be called immediately after component construction
	 * and needs data to initialize and take preliminary decisions
	 * 
	 * @param data
	 *            Data to initialize against
	 */
	void initialize(Object data);

	/**
	 * This method must be called immediately after component initialization and
	 * final creation, and needs data that must be made available to it
	 * 
	 * @param response
	 */
	void processInitialData(Response response);
}
