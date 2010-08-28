package com.mastergaurav.android.mvc.command;

import java.lang.reflect.Modifier;
import java.util.HashMap;

import com.mastergaurav.android.mvc.common.IResponseListener;
import com.mastergaurav.android.mvc.common.Request;

public final class CommandExecutor
{
	private final HashMap<Integer, Class<? extends ICommand>> commands = new HashMap<Integer, Class<? extends ICommand>>();

	private static final CommandExecutor instance = new CommandExecutor();
	private boolean initialized = false;

	public static final int COMMAND_ID_IDENTITY = 1;
	public static final int COMMAND_ID_BASE = 1000;

	private CommandExecutor()
	{
		commands.put(COMMAND_ID_IDENTITY, IdentityCommand.class);
	}

	public static CommandExecutor getInstance()
	{
		return instance;
	}

	public void ensureInitialized()
	{
		if(!initialized)
		{
			initialized = true;
			System.out.println("CommandExecutor::initialize");
			CommandQueueManager.getInstance().initialize();
			System.out.println("CommandExecutor::initialized");
		}
	}

	public void terminateAll()
	{
		// TODO: Terminate or mark all commands as terminated
	}

	public void enqueueCommand(int commandId, Request request, IResponseListener listener)
	{
		System.out.println("[CommandExecutor::enqueueCommand] Retrieving Command");
		final ICommand cmd = getCommand(commandId);
		if(cmd != null)
		{
			cmd.setRequest(request);
			cmd.setResponseListener(listener);
			CommandQueueManager.getInstance().enqueue(cmd);
		}
	}

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
