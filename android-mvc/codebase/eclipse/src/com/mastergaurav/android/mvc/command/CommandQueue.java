package com.mastergaurav.android.mvc.command;

import java.util.concurrent.LinkedBlockingQueue;

public class CommandQueue
{
	private LinkedBlockingQueue<ICommand> theQueue = new LinkedBlockingQueue<ICommand>();

	public CommandQueue()
	{
		System.out.println("CommandQueue::ctor");
	}

	public void enqueue(ICommand cmd)
	{
		System.out.println("CommandQueue::enqueue");
		theQueue.add(cmd);
	}

	public synchronized ICommand getNextCommand()
	{
		System.out.println("CommandQueue::get-next-command");
		ICommand cmd = null;
		try
		{
			System.out.println("CommandQueue::to-take");
			cmd = theQueue.take();
			System.out.println("CommandQueue::taken");
		} catch(InterruptedException e)
		{
			e.printStackTrace();
		}
		System.out.println("CommandQueue::return: " + cmd);
		return cmd;
	}

	public synchronized void clear()
	{
		System.out.println("CommandQueue::clear");
		theQueue.clear();
	}
}
