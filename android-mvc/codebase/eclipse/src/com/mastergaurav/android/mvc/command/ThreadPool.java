package com.mastergaurav.android.mvc.command;

public class ThreadPool
{

	private static final int MAX_THREADS_COUNT = 2;

	static private ThreadPool instance = new ThreadPool();
	private CommandThread threads[] = null;

	private boolean started = false;

	private ThreadPool()
	{
	}

	public static ThreadPool getInstance()
	{
		return instance;
	}

	public void start()
	{
		if(!started)
		{
			System.out.println("ThreadPool::start");

			int threadCount = MAX_THREADS_COUNT;
			threads = new CommandThread[threadCount];

			for(int threadId = 0; threadId < threadCount; threadId++)
			{
				threads[threadId] = new CommandThread(threadId);
				threads[threadId].start();
			}
			started = true;
			System.out.println("ThreadPool::started");
		}
	}

	public void shutdown()
	{
		if(started)
		{
			for(CommandThread thread : threads)
			{
				thread.stop();
			}
			threads = null;
			started = false;
		}
	}
}
