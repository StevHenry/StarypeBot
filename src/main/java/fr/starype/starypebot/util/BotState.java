package fr.starype.starypebot.util;

import fr.starype.starypebot.StarypeBot;

public enum BotState {

    STARTING,
    ENABLED,
    DISABLED;

    private static BotState currentState;

    static { currentState = STARTING; }

    public static boolean isState(BotState state){
        return state == currentState;
    }

    public static void setState(BotState state){
        StarypeBot.LOGGER.debug("State passed from " + currentState.name() + " to " + state.name() + ".");
        currentState = state;
    }

    public static BotState getState(){
        return currentState;
    }
}
