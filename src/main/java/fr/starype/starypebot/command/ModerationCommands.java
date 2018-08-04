package fr.starype.starypebot.command;

import fr.starype.starypebot.StarypeBot;
import fr.starype.starypebot.command.system.Command;
import fr.starype.starypebot.command.system.CommandHolder;
import fr.starype.starypebot.util.BotManager;
import fr.starype.starypebot.util.BotState;
import net.dv8tion.jda.core.EmbedBuilder;
import net.dv8tion.jda.core.JDA;
import net.dv8tion.jda.core.OnlineStatus;
import net.dv8tion.jda.core.entities.Game;
import net.dv8tion.jda.core.entities.MessageChannel;
import net.dv8tion.jda.core.entities.User;

import java.awt.*;
import java.io.IOException;
import java.net.URISyntaxException;

public class ModerationCommands implements CommandHolder {

	private Color moderationCommandsColor = new Color(224, 26, 26);

	@Command(name="stop", description = "Stops the bot.", usage = Command.Usage.MODERATORS, overpassBotState = true)
	private void stop(MessageChannel channel, User user){
		EmbedBuilder builder = new EmbedBuilder();
		builder.setColor(moderationCommandsColor);
		builder.setTitle("Shutdown...");
		builder.addField("__Moderator__", user.getName(), true);
		channel.sendMessage(builder.build()).queue();
		BotManager.shutdown();
	}

	@Command(name="restart", description = "Restarts the bot.", usage = Command.Usage.MODERATORS, overpassBotState = true)
	private void restart(MessageChannel channel, User user){
		EmbedBuilder builder = new EmbedBuilder();
		builder.setColor(moderationCommandsColor);
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

	@Command(name="enable", description = "Enable the bot.", usage = Command.Usage.MODERATORS, overpassBotState = true)
	private void enable(MessageChannel channel, JDA jda){
		EmbedBuilder builder = new EmbedBuilder();
		builder.setColor(moderationCommandsColor);
		switch(BotState.getState()){
			case ENABLED:
				builder.setTitle("Bot is already enabled !");
				break;
			case STARTING:
			case DISABLED:
				builder.setTitle("Bot is now enabled !");
				builder.setThumbnail("https://upload.wikimedia.org/wikipedia/commons/thumb/0/0e/Ski_trail_rating_symbol-green_circle.svg/600px-Ski_trail_rating_symbol-green_circle.svg.png");
				BotState.setState(BotState.ENABLED);
				jda.getPresence().setGame(Game.of(Game.GameType.DEFAULT, BotManager.DEFAULT_ACTIVITY));
				jda.getPresence().setStatus(OnlineStatus.ONLINE);
				break;
		}
		channel.sendMessage(builder.build()).queue();
	}

	@Command(name="disable", description = "Disable the bot.", usage = Command.Usage.MODERATORS, overpassBotState = true)
	private void disable(MessageChannel channel, JDA jda){
		EmbedBuilder builder = new EmbedBuilder();
		builder.setColor(moderationCommandsColor);
		switch(BotState.getState()){
			case ENABLED:
				builder.setTitle("Bot is now disabled !");
				builder.setThumbnail("https://upload.wikimedia.org/wikipedia/commons/9/9e/WX_circle_red.png");
				BotState.setState(BotState.DISABLED);
				jda.getPresence().setGame(Game.of(Game.GameType.DEFAULT, "beeing disabled"));
				jda.getPresence().setStatus(OnlineStatus.IDLE);
				break;
			case STARTING:
			case DISABLED:
				builder.setTitle("Bot is already enabled !");
				break;
		}
		channel.sendMessage(builder.build()).queue();
	}
}