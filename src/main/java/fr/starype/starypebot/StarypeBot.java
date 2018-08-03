package fr.starype.starypebot;

import net.dv8tion.jda.core.AccountType;
import net.dv8tion.jda.core.JDA;
import net.dv8tion.jda.core.JDABuilder;
import net.dv8tion.jda.core.entities.Game;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.security.auth.login.LoginException;

public class StarypeBot {

    public final static Logger LOGGER = LoggerFactory.getLogger(StarypeBot.class);

    private static StarypeBot instance;

    public static void main(String[] args) {
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

    private StarypeBot(String token){
        instance = this;

        try {
            jda = new JDABuilder(AccountType.CLIENT).setToken(token).setCompressionEnabled(true)
                    .setGame(Game.listening("tester é_è")).setAutoReconnect(true).setBulkDeleteSplittingEnabled(false)
                    /*.addEventListener(new EventsManager())*/.buildBlocking();
            LOGGER.info("Bot connecté !");
        } catch (LoginException | IllegalArgumentException | InterruptedException e) {
            LOGGER.error("Connexion au bot impossible !");
            LOGGER.debug(e.getMessage());
            System.exit(0);
        }

    }

}