package fr.starype.starypebot.util;

import fr.starype.starypebot.StarypeBot;
import net.dv8tion.jda.core.JDA;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;

public class BotManager {

    public static final String BOT_NAME = "StarypeBot";
    public static final String VERSION = "1.0";
    public static String[] startArguments;


    public static void shutdown() {
        JDA jda = StarypeBot.getInstance().getJDA();
        if (jda != null)
            jda.shutdown();
        else
            StarypeBot.LOGGER.warn("Tried to shutdown but bot not connected !");
        System.exit(0);
    }

    public static void restartBot() throws IOException, URISyntaxException
    {
        final String javaBin = System.getProperty("java.home") + File.separator + "bin" + File.separator + "java";
        final File currentJar = new File(StarypeBot.class.getProtectionDomain().getCodeSource().getLocation().toURI());

        if(!currentJar.getName().endsWith(".jar"))
            return;

        /* Build command: java -jar application.jar */
        final ArrayList<String> command = new ArrayList<String>();
        command.add(javaBin);
        command.add("-jar");
        command.add(currentJar.getPath());
        command.add("-DFile.encoding=UTF-8");
        command.add("Arguments");
        for(String startArg : startArguments)
            command.add(startArg);

        final ProcessBuilder builder = new ProcessBuilder(command);
        builder.start();
        shutdown();
    }
}
