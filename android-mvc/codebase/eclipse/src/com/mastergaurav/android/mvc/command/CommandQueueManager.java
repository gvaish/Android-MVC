package com.mastergaurav.android.mvc.command;

public final class CommandQueueManager
{

	private static CommandQueueManager instance = new CommandQueueManager();

	private boolean initialized = false;
	private ThreadPool pool;
	private CommandQueue queue;

	private CommandQueueManager()
	{
	}

	public static CommandQueueManager getInstance()
	{
		return instance;
	}

	public void initialize()
	{
		System.out.println("CommandQueueManager::initialize");
		if(!initialized)
		{
			System.out.println("CommandQueueManager::initializing");
			queue = new CommandQueue();
			pool = ThreadPool.getInstance();
			System.out.println("CommandQueueManager::initialized");

			pool.start();
			initialized = true;
		}
		System.out.println("CommandQueueManager::initialize - done");
	}

	/**
	 * This acts as the consumer for the queue...
	 * 
	 * @return
	 */
	public ICommand getNextCommand()
	{
		System.out.println("CommandQueueManager::getNextCommand");
		ICommand cmd = queue.getNextCommand();
		System.out.println("CommandQueueManager::getNextCommand - done: " + cmd);
		return cmd;
	}

	/**
	 * This acts as the producer for the queue...
	 * Generally used by command executor to enqueue
	 * 
	 * @param cmd
	 */
	public void enqueue(ICommand cmd)
	{
		System.out.println("CommandQueueManager::enqueue: " + cmd);
		queue.enqueue(cmd);
		System.out.println("CommandQueueManager::enqueued: " + cmd);
	}

	public void clear()
	{
		queue.clear();
	}

	public void shutdown()
	{
		if(initialized)
		{
			queue.clear();
			pool.shutdown();
			initialized = false;
		}
	}
}
