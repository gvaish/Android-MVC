package com.mastergaurav.android.common.xml;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

/**
 * @see XMLDeserializer
 */
public class FieldInfo
{
	/**
	 * Name of the element
	 */
	private String elementName;
	/**
	 * Name of the field
	 */
	private String fieldName;
	/**
	 * Class information about the field type
	 */
	private Class<?> type;
	/**
	 * Type of the field
	 */
	private FieldType fieldType;
	/**
	 * Field reference
	 */
	private Field field;
	/**
	 * Method to get the value
	 */
	private Method getMethod;
	/**
	 * Method to set the value
	 */
	private Method setMethod;
	/**
	 * Method to add a item to the collection field
	 */
	private Method addMethod;

	/**
	 * Gets the name of the element
	 * 
	 * @return Name of the element
	 */
	public String getElementName()
	{
		return elementName;
	}

	/**
	 * Sets the name of the element
	 * 
	 * @param name
	 *            Name of the element
	 */
	public void setElementName(String name)
	{
		this.elementName = name;
	}

	/**
	 * Gets class of the field
	 * 
	 * @return Field class
	 */
	public Class<?> getType()
	{
		return type;
	}

	/**
	 * Sets the class of the field
	 * 
	 * @param type
	 *            Field class
	 */
	public void setType(Class<?> type)
	{
		this.type = type;
	}

	/**
	 * Gets the type of the field
	 * 
	 * @return Field type
	 */
	public FieldType getFieldType()
	{
		return fieldType;
	}

	/**
	 * Sets the type of the field
	 * 
	 * @param fieldType
	 *            Field type
	 */
	public void setFieldType(FieldType fieldType)
	{
		this.fieldType = fieldType;
	}

	/**
	 * Gets the field reference
	 * 
	 * @return The field reference
	 */
	public Field getField()
	{
		return field;
	}

	/**
	 * Sets the field reference
	 * 
	 * @param field
	 *            Field reference
	 */
	public void setField(Field field)
	{
		this.field = field;
	}

	/**
	 * Gets the method to get the value of the field
	 * 
	 * @return The method reference
	 */
	public Method getGetMethod()
	{
		return getMethod;
	}

	/**
	 * Sets the method to get the value of the field
	 * 
	 * @param getMethod
	 *            Method reference
	 */
	public void setGetMethod(Method getMethod)
	{
		this.getMethod = getMethod;
	}

	/**
	 * Gets the method to set the value of the field
	 * 
	 * @return The method reference
	 */
	public Method getSetMethod()
	{
		return setMethod;
	}

	/**
	 * Sets the method to set the value of the field
	 * 
	 * @param setMethod
	 *            Method reference
	 */
	public void setSetMethod(Method setMethod)
	{
		this.setMethod = setMethod;
	}

	/**
	 * Gets the method to add an item to the collection field
	 * 
	 * @return The method reference
	 */
	public Method getAddMethod()
	{
		return addMethod;
	}

	/**
	 * Sets the method to get the value of the field
	 * 
	 * @param addMethod
	 *            Method reference
	 */
	public void setAddMethod(Method addMethod)
	{
		this.addMethod = addMethod;
	}

	/**
	 * Gets the name of the field
	 * 
	 * @return Name of the element
	 */
	public String getFieldName()
	{
		return fieldName;
	}

	/**
	 * Sets the name of the field
	 * 
	 * @param fieldName
	 *            name of the field
	 */
	public void setFieldName(String fieldName)
	{
		this.fieldName = fieldName;
	}
}
