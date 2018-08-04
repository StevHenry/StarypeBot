package fr.starype.starypebot.command.system;

import java.lang.reflect.Method;

public final class StarypeCommand {

	private final String name, description;
	private final String[] aliases;
	private final CommandHolder commandHolder;
	private final Method executorMethod;
	
	public StarypeCommand(CommandHolder cmdHolder, Method method, String name, String description, String... aliases) {
		this.name = name;
		this.description = description;
		this.commandHolder = cmdHolder;
		this.executorMethod = method;
		this.aliases = aliases;
	}

	public String getName() {
		return name;
	}

	public String getDescription() {
		return description;
	}
	
	public String[] getAliases() {
		return aliases;
	}

	public Object getCommandHolder() {
		return commandHolder;
	}

	public Method getExecutorMethod() {
		return executorMethod;
	}
}