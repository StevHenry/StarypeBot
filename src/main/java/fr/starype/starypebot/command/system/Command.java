package fr.starype.starypebot.command.system;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(value=ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Command {

	String name();
	String[] aliases() default {};
	String description() default "No description set.";
	boolean overpassBotState() default false;
	Usage usage() default Usage.EVERYONE;

	enum Usage {
		EVERYONE,
		MODERATORS;
	}
}