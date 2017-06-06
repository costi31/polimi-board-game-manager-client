package com.herokuapp.polimiboardgamemanager.client.view.command;

import com.herokuapp.polimiboardgamemanager.client.view.ClientView;

public interface Command {
	public static final String QUIT = "quit";
	public static final String HELP = "help";
	public static final String LOGIN = "login";
	
	public static final String SHOW_USERS = "show_users";
	public static final String SHOW_USER = "show_u";
	public static final String CREATE_USER = "create_u";
	public static final String UPDATE_USER = "update_u";
	public static final String DELETE_USER = "delete_u";
	
	public static final String SHOW_BOARDGAMES = "show_boardgames";
	public static final String SHOW_BOARDGAME = "show_b";
	public static final String CREATE_BOARDGAME = "create_b";
	
	public String getName();
	public Object[] getParameters();
	public String execute(ClientView cv, String outSymbol, String errorSymbol) throws Exception;
}
