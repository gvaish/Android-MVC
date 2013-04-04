package com.mastergaurav.android.common.xml;

/**
 * Possible field types, during deserialization
 * See {@link XMLDeserializer} for more details on its usage
 * 
 * @see XMLDeserializer
 */
public enum FieldType
{
	/**
	 * Used to indicate that the type of the field is not defined
	 */
	NotDefined,
	/**
	 * Used to indicate that the type of the field is pseudo-primitive
	 */
	PseudoPrimitive,
	/**
	 * Used to indicate that the type of the field is composite
	 */
	Composite,
	/**
	 * Used to indicate that the type of the field is a collection
	 */
	Collection
}
