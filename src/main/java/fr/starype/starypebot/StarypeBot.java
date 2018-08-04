package fr.starype.starypebot;

import fr.starype.starypebot.command.system.CommandsManager;
import fr.starype.starypebot.event.EventsManager;
import fr.starype.starypebot.util.BotManager;
import net.dv8tion.jda.core.AccountType;
import net.dv8tion.jda.core.JDA;
import net.dv8tion.jda.core.JDABuilder;
import net.dv8tion.jda.core.entities.Game;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.security.auth.login.LoginException;
import java.util.Scanner;

public class StarypeBot {

    public final static Logger LOGGER = LoggerFactory.getLogger(StarypeBot.class);

    private static StarypeBot instance;

    public static void main(String... args) {
        BotManager.startArguments = args;
        if (args.length > 0) {
            LOGGER.info("Bot's starting !");
            new StarypeBot(args[0]);
        } else
            LOGGER.error("No token provided !");
    }

    public static StarypeBot getInstance(){
        return instance;
    }

    private JDA jda;
    private final CommandsManager cmdManager;

    private StarypeBot(String token){
        instance = this;

        try {
            jda = new JDABuilder(AccountType.BOT).setToken(token).setCompressionEnabled(true)
                    .setGame(Game.watching("é_è")).setAutoReconnect(true).setBulkDeleteSplittingEnabled(false)
                    .addEventListener(new EventsManager()).buildBlocking();
            LOGGER.info("Bot connected !");
        } catch (LoginException | IllegalArgumentException | InterruptedException e) {
            LOGGER.error("Connection impossible !");
            LOGGER.debug(e.getMessage());
            System.exit(0);
        }

        cmdManager = new CommandsManager();

        new Thread(() -> {
            if(LOGGER.isDebugEnabled()){
                while(true)
                    if (new Scanner(System.in).nextLine().equals("stop")) {
                        LOGGER.info("Stopping Bot.");
                        BotManager.shutdown();
                        break;
                    }
            }
        }).start();
    }

    public JDA getJDA(){
        return jda;
    }

    public CommandsManager getCommandsManager() {
        return cmdManager;
    }
}