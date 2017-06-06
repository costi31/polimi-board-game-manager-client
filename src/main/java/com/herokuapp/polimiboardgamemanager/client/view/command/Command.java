package com.herokuapp.polimiboardgamemanager.client.view.command;

public interface Command {
	public static final String QUIT = "quit";
	public static final String HELP = "help";
	public static final String LOGIN = "login";
	public static final String SHOW_USERS = "show_users";
	public static final String SHOW_USER = "show_u";
	public static final String CREATE_USER = "create_u";
	public static final String UPDATE_USER = "update_u";
	
	public String getName();
	public Object[] getParameters();
}
