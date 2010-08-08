package com.mastergaurav.android.mvc.command;

import java.lang.reflect.Modifier;
import java.util.HashMap;

import com.mastergaurav.android.app.command.LoginCommand;
import com.mastergaurav.android.mvc.common.IResponseListener;
import com.mastergaurav.android.mvc.common.Request;

public final class CommandExecutor
{
	private final HashMap<Integer, Class<? extends ICommand>> commands = new HashMap<Integer, Class<? extends ICommand>>();

	private static final CommandExecutor instance = new CommandExecutor();

	private CommandExecutor()
	{
		commands.put(CommandID.LOGIN_DO, LoginCommand.class);
	}

	public static CommandExecutor getInstance()
	{
		return instance;
	}

	public void initialize()
	{
		System.out.println("CommandExecutor::initialize");
		CommandQueueManager.getInstance().initialize();
		System.out.println("CommandExecutor::initialized");
	}

	public void enqueueCommand(int commandId, Request request, IResponseListener listener)
	{
		System.out.println("[CommandExecutor::enqueueCommand] Retrieving Command");
		final ICommand cmd = getCommand(commandId);
		if(cmd != null)
		{
			cmd.setRequest(request);
			cmd.setResponseListener(listener);
			// Runnable r = new Runnable() {
			// public void run()
			// {
			// try
			// {
			// Thread.sleep(1500);
			// } catch (InterruptedException e)
			// {
			// e.printStackTrace();
			// }
			// cmd.execute();
			// }
			// };
			// System.out.println("[CommandExecutor::enqueueCommand] Enqueue Command: "
			// + cmd);
			CommandQueueManager.getInstance().enqueue(cmd);
			// new Thread(r).start();
			// System.out.println("[CommandExecutor::enqueueCommand] Enqueued Command");
		}
	}

	// Always create a new instance of the command -- commands are not singleton
	// but miltiton
	private ICommand getCommand(int commandId)
	{
		ICommand rv = null;

		if(commands.containsKey(commandId))
		{
			Class<? extends ICommand> cmd = commands.get(commandId);
			if(cmd != null)
			{
				int modifiers = cmd.getModifiers();
				if((modifiers & Modifier.ABSTRACT) == 0 && (modifiers & Modifier.INTERFACE) == 0)
				{
					try
					{
						rv = cmd.newInstance();
					} catch(Exception e)
					{
						e.printStackTrace();
					}
				}
			}
		}

		return rv;
	}

	public void registerCommand(int commandId, Class<? extends ICommand> command)
	{
		if(command != null)
		{
			commands.put(commandId, command);
		}
	}

	public void unregisterCommand(int commandId)
	{
		commands.remove(commandId);
	}
}
