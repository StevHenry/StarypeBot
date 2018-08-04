package fr.starype.starypebot.command;

import fr.starype.starypebot.StarypeBot;
import fr.starype.starypebot.command.system.Command;
import fr.starype.starypebot.command.system.CommandHolder;
import fr.starype.starypebot.command.system.StarypeCommand;
import fr.starype.starypebot.util.BotManager;
import net.dv8tion.jda.core.EmbedBuilder;
import net.dv8tion.jda.core.entities.MessageChannel;
import net.dv8tion.jda.core.entities.MessageEmbed;
import net.dv8tion.jda.core.entities.User;

import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class GeneralCommands implements CommandHolder {

    private Color generalCommandsColor = new Color(0, 224, 22);

    @Command(name = "help", description = "Shows available commands.")
    public void help(MessageChannel channel, User user) {
        List<MessageEmbed> embeds = new ArrayList<>();
        EmbedBuilder builder = new EmbedBuilder();
        builder.setTitle("``Available commands - Visibility mode: " + (Arrays.asList(BotManager.getAdministrators()).contains(user) ? Command.Usage.MODERATORS.name() : Command.Usage.EVERYONE.name()).toLowerCase() + "``");
        builder.setColor(generalCommandsColor);

        for (StarypeCommand cmd : StarypeBot.getInstance().getCommandsManager().getCommands()) {
            if (builder.getFields().size()/3 == BotManager.COMMANDS_PER_EMBED){
                setPage(builder, embeds);
                embeds.add(builder.build());
                builder = new EmbedBuilder();
                builder.setColor(generalCommandsColor);
            }
            if (Arrays.asList(BotManager.getAdministrators()).contains(user) || cmd.getUsage() == Command.Usage.EVERYONE) {
                builder.addField("**/" + cmd.getName() + "**", cmd.getDescription(), true);
                builder.addField("__Usage__", cmd.getUsage().name(), true);
            }
            if(cmd.getAliases().length > 0)
                builder.addField("__Aliases__", String.join(", ", cmd.getAliases()), true);
            else
                builder.addBlankField(true);
        }
        setPage(builder, embeds);
        embeds.add(builder.build());

        for (MessageEmbed embed : embeds)
            channel.sendMessage(embed).queue();
    }

    @Command(name = "info", description = "Shows starype informations", aliases = {"starype", "st"})
    public void info(MessageChannel channel) {
        List<MessageEmbed> embeds = new ArrayList<>();
        User[] admins = BotManager.getAdministrators();
        String[] names = new String[admins.length];
        for (int i = 0; i < admins.length; i++)
            names[i] = admins[i].getAsMention();


        EmbedBuilder builder = new EmbedBuilder();
        builder.setThumbnail("https://cdn.discordapp.com/attachments/421718304479117332/475392214755835915/logo_orange.png");
        //builder.setAuthor(BotManager.BOT_NAME + " - Version " + BotManager.VERSION);
        builder.setColor(generalCommandsColor);
        builder.setTitle("``Starype informations``");
        builder.addField("__Founders__", String.join(", ", names), true);
        builder.addField("__Crew creation date__", "M'en rappelle plus ._.", true);

        embeds.add(builder.build());

        builder = new EmbedBuilder();
        builder.setColor(generalCommandsColor);
        builder.setTitle("``Contact``");
        builder.addField("__Email__", BotManager.EMAIL, false);
        builder.addField("__Website__", BotManager.WEBSITE_URL, false);
        builder.addField("__Make a donation__", BotManager.DONATION_URL, false);
        builder.setFooter("Starype 2017-2018", "https://cdn.discordapp.com/attachments/421718304479117332/475399099722760192/copyright.png");

        embeds.add(builder.build());

        for (MessageEmbed embed : embeds)
            channel.sendMessage(embed).queue();
    }

    private void setPage(EmbedBuilder builder, List<MessageEmbed> existingEmbeds){
        builder.setFooter("Page n°" + (existingEmbeds.size()+1) + "", "http://www.emoji.co.uk/files/twitter-emojis/objects-twitter/11039-page-facing-up.png");
    }
}