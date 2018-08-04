package fr.starype.starypebot.event;

import fr.starype.starypebot.StarypeBot;
import fr.starype.starypebot.command.system.CommandsManager;
import net.dv8tion.jda.core.events.Event;
import net.dv8tion.jda.core.events.ReadyEvent;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;
import net.dv8tion.jda.core.hooks.EventListener;

public class EventsManager implements EventListener {

    private StarypeBot bot = StarypeBot.getInstance();
    private CommandsManager manager;

    @Override
    public void onEvent(Event event) {
        if (event instanceof ReadyEvent)
            onReady();
        if (event instanceof MessageReceivedEvent)
            onMessage((MessageReceivedEvent) event);

    }

    private void onReady() {
        new Thread(() -> {
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            manager = bot.getCommandsManager();
        }).start();
    }

    public void onMessage(MessageReceivedEvent e) {

        if (e.getAuthor() == StarypeBot.getInstance().getJDA().getSelfUser() || manager == null){
            return;
        }

        String msg = e.getMessage().getContentRaw();
        if (msg.startsWith(manager.getCommandTag())) {
            msg = msg.replaceFirst(manager.getCommandTag(), "");
            manager.executeCommand(msg, e.getMessage());
        }
    }
}