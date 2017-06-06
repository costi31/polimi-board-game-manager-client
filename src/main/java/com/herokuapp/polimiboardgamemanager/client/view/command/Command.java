package com.herokuapp.polimiboardgamemanager.client.view.command;

public interface Command {
	public static final String QUIT = "quit";
	public static final String HELP = "help";
	public static final String LOGIN = "login";
	public static final String SHOW_USERS = "show_users";
	
	public String getName();
	public Object[] getParameters();
}
