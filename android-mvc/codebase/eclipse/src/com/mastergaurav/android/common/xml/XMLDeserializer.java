package com.mastergaurav.android.common.xml;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Stack;
import java.util.regex.Pattern;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import android.util.Xml;

public final class XMLDeserializer
{
	/**
	 * Cached names of the getter methods
	 */
	private Map<String, String> getMethodNameMap = new HashMap<String, String>();

	/**
	 * Cached names of the setter methods
	 */
	private Map<String, String> setMethodNameMap = new HashMap<String, String>();

	/**
	 * Cached names of the add-methods
	 */
	private Map<String, String> addMethodNameMap = new HashMap<String, String>();

	/**
	 * The single instance of the deserializer
	 */
	private static final XMLDeserializer instance = new XMLDeserializer();

	/**
	 * Regular expression patern for common baseline for Java identifier and XML
	 * element name
	 */
	private static final Pattern validFieldNamePattern = Pattern.compile("^[a-zA-Z][a-zA-Z0-9]*$");

	/**
	 * Private constructor to disallow any public instantiation
	 */
	private XMLDeserializer()
	{
	}

	public <T> T deserialize(InputStream input, Class<T> objType)
	{
		XmlPullParser parser = Xml.newPullParser();
		try
		{
			parser.setInput(input, null);
			return deserialize(parser, objType);
		} catch(Throwable e)
		{
			e.printStackTrace();
		}

		return null;
	}

	public <T> T deserialize(XmlPullParser parser, Class<T> objType)
	{
		T rv = null;
		Stack<ClassInfo> stack = new Stack<ClassInfo>();

		try
		{
			int evtType = -1;
			evtType = parser.next();

			while(evtType != XmlPullParser.END_DOCUMENT)
			{
				if(evtType == XmlPullParser.START_TAG)
				{
					String name = parser.getName();
					if(objType != null)
					{
						ClassInfo rootCI = new ClassInfo(objType, name);
						stack.push(rootCI);
						rv = objType.newInstance();
						deserialize(rv, parser, stack);
						break;
					}
				}
				evtType = parser.next();
			}
		} catch(Throwable t)
		{
			t.printStackTrace();
		}
		return rv;
	}

	private void deserialize(Object obj, XmlPullParser parser, Stack<ClassInfo> stack) throws XmlPullParserException,
			IOException
	{
		int evtType = parser.next();
		String name;
		ClassInfo ci = stack.peek();
		Class<?> clz = ci.getType();

		// Read until the end of the document
		while(evtType != XmlPullParser.END_DOCUMENT)
		{

			// If we are done with the parsing of the current element, we done
			// with this level
			if(evtType == XmlPullParser.END_TAG)
			{
				if(ci.getElementName().equals(parser.getName()))
				{
					break;
				}
			}

			// Start of a tag signifies a field to be populated
			if(evtType == XmlPullParser.START_TAG)
			{
				name = parser.getName();
				FieldInfo info = getFieldInfo(clz, name);
				FieldType ft = info.getFieldType();

				if(ft.equals(FieldType.PseudoPrimitive))
				{
					// For pseudo-primitive fields, directly convert the value
					// and set
					String value = parser.nextText();
					try
					{
						setFieldValue(obj, info, Converter.convertTo(value, info.getType()));
					} catch(Throwable e)
					{
						e.printStackTrace();
					}
				} else if(ft.equals(FieldType.Collection))
				{
					// For collection fields, deserialize the contents within
					// the element and add the value
					Field fld = info.getField();
					ItemType itemType = fld.getAnnotation(ItemType.class);
					if(itemType != null)
					{
						Class<?> itemValueType = itemType.value();
						Object value = null;
						if(Converter.isPseudoPrimitive(itemValueType))
						{
							value = Converter.convertTo(parser.nextText(), itemValueType);
						} else
						{
							ClassInfo itemCI = new ClassInfo(itemValueType, name);
							stack.push(itemCI);
							try
							{
								Object subObj = itemValueType.newInstance();
								addFieldValue(obj, info, subObj);
								deserialize(subObj, parser, stack);
							} catch(Throwable e)
							{
								e.printStackTrace();
							}
						}
						if(value != null)
						{
							try
							{
								addFieldValue(obj, info, value);
							} catch(Throwable e)
							{
								e.printStackTrace();
							}
						}
					}
				} else if(ft.equals(FieldType.Composite))
				{
					// For composite fields, instantiate appropriate data type
					// and set the value
					Class<?> subType = info.getType();
					ClassInfo itemCI = new ClassInfo(subType, name);
					stack.push(itemCI);
					try
					{
						Object subObj = subType.newInstance();
						setFieldValue(obj, info, subObj);
						deserialize(subObj, parser, stack);
					} catch(Throwable e)
					{
						e.printStackTrace();
					}
				} else if(ft.equals(FieldType.NotDefined))
				{
					// process till element end
					skipElement(parser, name);
				}
			}
			evtType = parser.next();
		}
		stack.pop();
	}

	private void skipElement(XmlPullParser parser, String elementName) throws XmlPullParserException, IOException
	{
		int indent = 0;
		int evtType = parser.next();

		boolean finished = indent == 0 && evtType == XmlPullParser.END_TAG && parser.getName().equals(elementName);

		while(!finished)
		{
			evtType = parser.next();
			if(evtType == XmlPullParser.START_TAG && parser.getName().equals(elementName))
			{
				indent++;
			} else if(evtType == XmlPullParser.START_TAG && parser.getName().equals(elementName))
			{
				indent--;
			}
			finished = indent == 0 && evtType == XmlPullParser.END_TAG && parser.getName().equals(elementName);
		}
	}

	private void addFieldValue(Object obj, FieldInfo info, Object value) throws IllegalArgumentException,
			IllegalAccessException, InvocationTargetException
	{
		Method addMethod = info.getAddMethod();
		addMethod.invoke(obj, value);
	}

	private void setFieldValue(Object obj, FieldInfo info, Object value) throws IllegalArgumentException,
			IllegalAccessException, InvocationTargetException
	{
		if(value != null)
		{
			Method setMethod = info.getSetMethod();
			setMethod.invoke(obj, value);
		}
	}

	private FieldInfo getFieldInfo(Class<?> clz, String elementName)
	{
		FieldInfo info = new FieldInfo();
		info.setElementName(elementName);
		info.setFieldType(FieldType.NotDefined);

		String fieldName = getFieldName(clz, elementName);
		if(fieldName == null)
		{
			return info;
		}
		info.setFieldName(fieldName);

		try
		{
			Field field = clz.getDeclaredField(fieldName);
			if(field != null)
			{
				Method method = null;
				Class<?> type = field.getType();
				info.setType(type);
				info.setField(field);
				if(List.class.isAssignableFrom(field.getType()))
				{
					String methodName = getAddMethodName(fieldName);
					ItemType itemType = field.getAnnotation(ItemType.class);
					Class<?> itemValueType = (itemType != null) ? itemType.value() : Object.class;
					method = clz.getDeclaredMethod(methodName, itemValueType);
					info.setAddMethod(method);
				} else
				{
					String methodName = getSetMethodName(fieldName);
					method = clz.getDeclaredMethod(methodName, type);
					info.setSetMethod(method);
				}
				if(method != null)
				{
					if(Converter.isPseudoPrimitive(type))
					{
						info.setFieldType(FieldType.PseudoPrimitive);
					} else if(List.class.isAssignableFrom(type))
					{
						info.setFieldType(FieldType.Collection);
					} else
					{
						info.setFieldType(FieldType.Composite);
					}
				}
			}
		} catch(Exception e)
		{
			e.printStackTrace();
		}

		return info;
	}

	private String getFieldName(Class<?> clz, String elementName)
	{
		if(validFieldNamePattern.matcher(elementName).matches())
		{
			return elementName;
		}
		Field[] fields = clz.getDeclaredFields();
		String fieldName = null;
		for(Field field : fields)
		{
			ElementName ename = field.getAnnotation(ElementName.class);
			if(ename != null && elementName.equals(ename.value()))
			{
				fieldName = field.getName();
				break;
			}
		}
		return fieldName;
	}

	public String getGetMethodName(String fieldName)
	{
		if(getMethodNameMap.containsKey(fieldName))
		{
			return getMethodNameMap.get(fieldName);
		}
		String method = "get" + Character.toUpperCase(fieldName.charAt(0)) + fieldName.substring(1);
		getMethodNameMap.put(fieldName, method);
		return method;
	}

	public String getSetMethodName(String fieldName)
	{
		if(setMethodNameMap.containsKey(fieldName))
		{
			return setMethodNameMap.get(fieldName);
		}
		String method = "set" + Character.toUpperCase(fieldName.charAt(0)) + fieldName.substring(1);
		setMethodNameMap.put(fieldName, method);
		return method;
	}

	public String getAddMethodName(String fieldName)
	{
		if(addMethodNameMap.containsKey(fieldName))
		{
			return addMethodNameMap.get(fieldName);
		}
		String method = "add" + Character.toUpperCase(fieldName.charAt(0)) + fieldName.substring(1);
		addMethodNameMap.put(fieldName, method);
		return method;
	}

	public static XMLDeserializer getInstance()
	{
		return instance;
	}
}
