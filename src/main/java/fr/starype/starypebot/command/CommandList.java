package fr.starype.starypebot.command;

import fr.starype.starypebot.command.system.Command;
import fr.starype.starypebot.command.system.CommandHolder;
import net.dv8tion.jda.core.entities.MessageChannel;

public class CommandList implements CommandHolder {

	@Command(name = "help", description = "Show available commands.")
	public void help(MessageChannel channel) {
		/*
		 * jda.getPrivateChannelById(channel.getIdLong()).
		 * sendMessage("BOT : help").queue();
		 */
		channel.sendMessage("Help ! é_è").queue();
	}
}