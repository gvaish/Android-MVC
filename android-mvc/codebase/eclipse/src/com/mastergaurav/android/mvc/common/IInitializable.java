package com.mastergaurav.android.mvc.common;

/**
 * Repesents a component that can be initalized
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
