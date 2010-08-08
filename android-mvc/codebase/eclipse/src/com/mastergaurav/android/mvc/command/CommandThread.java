package com.mastergaurav.android.mvc.command;

public class CommandThread implements Runnable
{
	private int threadId;
	private Thread thread = null;
	private boolean running = false;
	private boolean stop = false;

	public CommandThread(int threadId)
	{
		System.out.println("CommandThread::ctor");
		this.threadId = threadId;
		thread = new Thread(this);
	}

	public void run()
	{
		System.out.println("CommandThread::run-enter");
		while(!stop)
		{
			System.out.println("CommandThread::get-next-command");
			ICommand cmd = CommandQueueManager.getInstance().getNextCommand();
			System.out.println("CommandThread::to-execute");
			cmd.execute();
			System.out.println("CommandThread::executed");
		}
		System.out.println("CommandThread::run-exit");
	}

	public void start()
	{
		thread.start();
		running = true;
	}

	public void stop()
	{
		stop = true;
		running = false;
	}

	public boolean isRunning()
	{
		return running;
	}

	public int getThreadId()
	{
		return threadId;
	}
}
