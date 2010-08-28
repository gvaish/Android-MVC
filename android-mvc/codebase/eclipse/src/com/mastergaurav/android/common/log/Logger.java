package com.mastergaurav.android.common.log;

public class Logger
{
	private static final ILoggerStrategy logger = new LoggerStrategyDefault();

	public static void d(String tag, String message)
	{
		logger.d(tag, message);
	}

	public static void e(String tag, String message)
	{
		logger.e(tag, message);
	}

	public static void i(String tag, String message)
	{
		logger.i(tag, message);
	}

	public static void v(String tag, String message)
	{
		logger.v(tag, message);
	}

	public static void w(String tag, String message)
	{
		logger.w(tag, message);
	}

	public static void write(int level, String tag, String message)
	{
		logger.write(level, tag, message);
	}
}
