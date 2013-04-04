package com.mastergaurav.android.common.xml;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Annotation to specify the item type when the field is a collection. <br/>
 * See {@link XMLDeserializer} for usage
 * 
 * @see XMLDeserializer
 */
@Retention(RetentionPolicy.RUNTIME)
@Target( {
	ElementType.FIELD
})
public @interface ItemType
{
	/**
	 * Represents the type of the item in the collection field.
	 */
	Class<?> value();
}
