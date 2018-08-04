package fr.starype.starypebot.command.system;

import java.lang.reflect.Method;

public final class StarypeCommand {

	private final String name, description;
	private final String[] aliases;
	private final boolean canOverpassBotState;
	private final CommandHolder commandHolder;
	private final Method executorMethod;
	private final Command.Usage usage;

	public StarypeCommand(CommandHolder cmdHolder, Method method, String name, String description, boolean canOverpassBotState, Command.Usage usage, String... aliases) {
		this.name = name;
		this.description = description;
		this.commandHolder = cmdHolder;
		this.executorMethod = method;
		this.aliases = aliases;
		this.usage = usage;
		this.canOverpassBotState = canOverpassBotState;
	}

	public String getName() {
		return name;
	}

	public String getDescription() {
		return description;
	}

	public String[] getAliases() { return aliases; }

	public Object getCommandHolder() {
		return commandHolder;
	}

	public Method getExecutorMethod() {
		return executorMethod;
	}

	public Command.Usage getUsage() { return usage; }

    public boolean canOverpassBotState() { return canOverpassBotState; }
}