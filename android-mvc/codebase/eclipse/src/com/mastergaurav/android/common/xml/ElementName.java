package com.mastergaurav.android.common.xml;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Represents name of the element to which a field in model may map to. <br/>
 * 
 * @see XMLDeserializer
 */
@Retention(RetentionPolicy.RUNTIME)
@Target( {
	ElementType.FIELD
})
public @interface ElementName
{
	String value();
}
