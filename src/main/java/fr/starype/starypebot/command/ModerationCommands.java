package fr.starype.starypebot.command;

import fr.starype.starypebot.StarypeBot;
import fr.starype.starypebot.command.system.Command;
import fr.starype.starypebot.command.system.CommandHolder;
import fr.starype.starypebot.util.BotManager;
import net.dv8tion.jda.core.EmbedBuilder;
import net.dv8tion.jda.core.entities.MessageChannel;
import net.dv8tion.jda.core.entities.User;

import java.awt.*;
import java.io.IOException;
import java.net.URISyntaxException;

public class ModerationCommands implements CommandHolder {

	@Command(name="stop", description = "Stop the bot.")
	private void stop(MessageChannel channel, User user){
		EmbedBuilder builder = new EmbedBuilder();
		builder.setAuthor(BotManager.BOT_NAME + " - " + BotManager.VERSION);
		builder.setColor(new Color(224, 26, 26));
		builder.setTitle("Shutdown...");
		builder.addField("__Moderator__", user.getName(), true);
		channel.sendMessage(builder.build()).queue();
		BotManager.shutdown();
	}

	@Command(name="restart", description = "Restart the bot")
	private void restart(MessageChannel channel, User user){
		EmbedBuilder builder = new EmbedBuilder();
		builder.setAuthor(BotManager.BOT_NAME + " - " + BotManager.VERSION);
		builder.setColor(new Color(22, 172, 224));
		builder.setTitle("Restarting...");
		builder.addField("__Moderator__", user.getName(), true);
		channel.sendMessage(builder.build()).queue();
		try {
			BotManager.restartBot();
		}catch (IOException | URISyntaxException e){
			StarypeBot.LOGGER.error("Error when restarting bot !");
			StarypeBot.LOGGER.error(e.getMessage());
		}
	}
}