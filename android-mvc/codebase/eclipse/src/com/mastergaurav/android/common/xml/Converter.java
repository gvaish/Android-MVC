package com.mastergaurav.android.common.xml;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Helper class to convert from {@link String} to pseudo-primitive types. <br/>
 * Currently, the following types are considered to be pseudo-primitive types:
 * <ol>
 * <li>{@link String}</li>
 * <li>{@link Short}</li>
 * <li>{@link Integer}</li>
 * <li>{@link Long}</li>
 * <li>{@link Character}</li>
 * <li>{@link Float}</li>
 * <li>{@link Double}</li>
 * <li>{@link Boolean}</li>
 * <li>{@link Date}</li>
 * </ol>
 */
public final class Converter
{
	/**
	 * Map between pseudo-primitive type and it's code for easy access
	 */
	private static Map<Class<?>, Integer> clzTypeKeyMap = new HashMap<Class<?>, Integer>();

	/**
	 * Code for {@link String}
	 */
	private static final int TYPE_STRING = 1;
	/**
	 * Code for {@link Short}
	 */
	private static final int TYPE_SHORT = 2;
	/**
	 * Code for {@link Integer}
	 */
	private static final int TYPE_INT = 3;
	/**
	 * Code for {@link Long}
	 */
	private static final int TYPE_LONG = 4;
	/**
	 * Code for {@link Character}
	 */
	private static final int TYPE_CHAR = 5;
	/**
	 * Code for {@link Flat}
	 */
	private static final int TYPE_FLOAT = 6;
	/**
	 * Code for {@link Double}
	 */
	private static final int TYPE_DOUBLE = 7;
	/**
	 * Code for {@link Boolean}
	 */
	private static final int TYPE_BOOLEAN = 8;
	/**
	 * Code for {@link Date}
	 */
	private static final int TYPE_DATE = 9;

	static
	{
		clzTypeKeyMap.put(String.class, TYPE_STRING);
		clzTypeKeyMap.put(short.class, TYPE_SHORT);
		clzTypeKeyMap.put(int.class, TYPE_INT);
		clzTypeKeyMap.put(long.class, TYPE_LONG);
		clzTypeKeyMap.put(char.class, TYPE_CHAR);
		clzTypeKeyMap.put(float.class, TYPE_FLOAT);
		clzTypeKeyMap.put(double.class, TYPE_DOUBLE);
		clzTypeKeyMap.put(boolean.class, TYPE_BOOLEAN);
		clzTypeKeyMap.put(Date.class, TYPE_DATE);
	}

	/**
	 * Constructor has been marked private to disallow any instantiation
	 * 
	 */
	private Converter()
	{
	}

	/**
	 * Check if a type is a pseudo-primitive type or not.
	 * 
	 * @param clz
	 *            Type being checked
	 * @return true} if type is a pseudo-primitive type or not
	 * @see {@link Converter}
	 */
	public static boolean isPseudoPrimitive(final Class<?> clz)
	{
		return clzTypeKeyMap.containsKey(clz);
	}

	/**
	 * Converts a {@link String} value to specified type, if possible.
	 * 
	 * @param raw
	 *            Raw, string value, to be converted
	 * @param clz
	 *            Target type to be converted to
	 * @return Converted value, if converstion was possible, null otherwise
	 * @throws NumberFormatException
	 *             If the value was not in correct format, while converting to
	 *             numeric type
	 * @throws RuntimeException
	 *             If the value was not in correct format, while converting to
	 *             Date or Boolean type
	 */
	public static Object convertTo(final String raw, final Class<?> clz)
	{
		Object value = null;
		if(clzTypeKeyMap.containsKey(clz))
		{
			final int code = clzTypeKeyMap.get(clz);
			switch(code)
			{
				case TYPE_STRING:
					value = raw;
					break;
				case TYPE_SHORT:
					value = Short.parseShort(raw);
					break;
				case TYPE_INT:
					value = Integer.parseInt(raw);
					break;
				case TYPE_LONG:
					value = Long.parseLong(raw);
					break;
				case TYPE_CHAR:
					if(raw != null && raw.length() > 0)
					{
						value = raw.charAt(0);
					} else
					{
						value = '\0';
					}
					break;
				case TYPE_FLOAT:
					value = Float.parseFloat(raw);
					break;
				case TYPE_DOUBLE:
					value = Double.parseDouble(raw);
					break;
				case TYPE_BOOLEAN:
					value = Boolean.parseBoolean(raw);
					break;
				case TYPE_DATE:
					value = Date.parse(raw);
					break;
				default:
					break;
			}
		}
		return value;
	}
}
