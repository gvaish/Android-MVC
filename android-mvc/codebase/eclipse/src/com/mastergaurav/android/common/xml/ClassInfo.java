package com.mastergaurav.android.common.xml;

/**
 * Represents a map between a model class and element name in XML. <br/>
 * This class is used by {@link XMLDeserializer} to identify the mapping
 * between an element (via its name) and the type that it maps to. <br/>
 * See {@link XMLDeserializer} for more details on its usage
 * 
 * @see XMLDeserializer
 */
public class ClassInfo
{
	/**
	 * Class being mapped to
	 */
	private Class<?> type;

	/**
	 * Name of the element to be deserialized
	 */
	private String elementName;

	/**
	 * Creates an intance of {@link ClassInfo} with unitialized element name and
	 * type.
	 */
	public ClassInfo()
	{
	}

	/**
	 * Creates an intance of {@link ClassInfo} with specified element name and
	 * type.
	 * 
	 * @param type
	 *            Class to which the element maps
	 * @param elementName
	 *            Name of the element being mapped
	 */
	public ClassInfo(Class<?> type, String elementName)
	{
		this.type = type;
		this.elementName = elementName;
	}

	/**
	 * Gets the class being mapped.
	 * 
	 * @return Class being mapped
	 */
	public Class<?> getType()
	{
		return type;
	}

	/**
	 * Sets the class being mapped.
	 * 
	 * @param type
	 *            Class being mapped
	 */
	public void setType(Class<?> type)
	{
		this.type = type;
	}

	/**
	 * Gets the element name under consideration.
	 * 
	 * @return Name of the element
	 */
	public String getElementName()
	{
		return elementName;
	}

	/**
	 * Sets the element name under consideration.
	 * 
	 * @return Name of the element
	 */
	public void setElementName(String elementName)
	{
		this.elementName = elementName;
	}
}
