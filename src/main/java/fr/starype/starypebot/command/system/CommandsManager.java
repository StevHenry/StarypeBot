package fr.starype.starypebot.command.system;

import fr.starype.starypebot.StarypeBot;
import fr.starype.starypebot.command.GeneralCommands;
import fr.starype.starypebot.command.ModerationCommands;
import fr.starype.starypebot.util.BotState;
import net.dv8tion.jda.core.JDA;
import net.dv8tion.jda.core.entities.*;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.Set;

//@author NeutronStars
//Edited by lolilolulolilol

public class CommandsManager {

    private final HashMap<String, StarypeCommand> commands = new HashMap<>();
    private final String tag = "/";

    public CommandsManager() {
        registerCommandHolders(new GeneralCommands(), new ModerationCommands());
    }

    public String getCommandTag() {
        return tag;
    }

    public Set<StarypeCommand> getCommands() {
        return new LinkedHashSet<>(commands.values());
    }

    public void registerCommandHolders(CommandHolder... commandHolders) {
        for (CommandHolder cmdHolder : commandHolders)
            registerCommandHolder(cmdHolder);
    }

    public void registerCommandHolder(CommandHolder cmdClass) {
        for (Method executor : cmdClass.getClass().getDeclaredMethods()) {
            if (executor.isAnnotationPresent(Command.class)) {
                Command cmd = executor.getAnnotation(Command.class);
                executor.setAccessible(true);
                StarypeCommand starypeCommand = new StarypeCommand(cmdClass, executor, cmd.name(), cmd.description(), cmd.overpassBotState(), cmd.usage(), cmd.aliases());
                commands.put(cmd.name(), starypeCommand);
                for (String alias : cmd.aliases())
                    commands.put(alias, starypeCommand);
            }
        }
    }

    public void executeCommand(String command, Message message) {
        Object[] cmdContents = getCommand(command);
        if (cmdContents.length > 0 & cmdContents[0] != null)
            execute(((StarypeCommand) cmdContents[0]), (String[]) cmdContents[1], message);
    }

    //Returned Object[] contains command and args;
    private Object[] getCommand(String command) {
        String[] commandSplit = command.split(" ");
        String[] args = new String[commandSplit.length - 1];
        for (int i = 1; i < commandSplit.length; i++)
            args[i - 1] = commandSplit[i];
        StarypeCommand cmd = commands.get(commandSplit[0].toLowerCase());
        return new Object[]{cmd, args};
    }

    private void execute(StarypeCommand starypeCommand, String[] args, Message message) {
        if(!BotState.isState(BotState.ENABLED) && !starypeCommand.canOverpassBotState())
            return;
        Parameter[] parameters = starypeCommand.getExecutorMethod().getParameters();
        Object[] objects = new Object[parameters.length];
        for (int i = 0; i < parameters.length; i++) {
            if (parameters[i].getType() == String[].class) objects[i] = args;
            else if (parameters[i].getType() == User.class) objects[i] = message == null ? null : message.getAuthor();
            else if (parameters[i].getType() == TextChannel.class)
                objects[i] = message == null ? null : message.getTextChannel();
            else if (parameters[i].getType() == PrivateChannel.class)
                objects[i] = message == null ? null : message.getPrivateChannel();
            else if (parameters[i].getType() == Guild.class) objects[i] = message == null ? null : message.getGuild();
            else if (parameters[i].getType() == String.class) objects[i] = starypeCommand.getName();
            else if (parameters[i].getType() == Message.class) objects[i] = message;
            else if (parameters[i].getType() == JDA.class) objects[i] = StarypeBot.getInstance().getJDA();
            else if (parameters[i].getType() == MessageChannel.class) objects[i] = message.getChannel();
        }
        try {
            starypeCommand.getExecutorMethod().invoke(starypeCommand.getCommandHolder(), objects);
        } catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
            e.printStackTrace();
        }
    }
}