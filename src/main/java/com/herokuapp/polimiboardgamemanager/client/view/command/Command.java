package com.herokuapp.polimiboardgamemanager.client.view.command;

import com.herokuapp.polimiboardgamemanager.client.view.ClientView;

/**
 * The Interface Command.
 */
public interface Command {
	
	/** The Constant QUIT. */
	public static final String QUIT = "quit";
	
	/** The Constant HELP. */
	public static final String HELP = "help";
	
	/** The Constant LOGIN. */
	public static final String LOGIN = "login";
	
	/** The Constant SHOW_USERS. */
	public static final String SHOW_USERS = "show_users";
	
	/** The Constant SHOW_USER. */
	public static final String SHOW_USER = "show_u";
	
	/** The Constant COUNT_USERS. */
	public static final String COUNT_USERS = "count_users";
	
	/** The Constant CREATE_USER. */
	public static final String CREATE_USER = "create_u";
	
	/** The Constant UPDATE_USER. */
	public static final String UPDATE_USER = "update_u";
	
	/** The Constant DELETE_USER. */
	public static final String DELETE_USER = "delete_u";
	
	/** The Constant SHOW_BOARDGAMES. */
	public static final String SHOW_BOARDGAMES = "show_boardgames";
	
	/** The Constant SHOW_BOARDGAME. */
	public static final String SHOW_BOARDGAME = "show_b";
	
	/** The Constant COUNT_BOARDGAMES. */
	public static final String COUNT_BOARDGAMES = "count_boardgames";
	
	/** The Constant CREATE_BOARDGAME. */
	public static final String CREATE_BOARDGAME = "create_b";
	
	/** The Constant UPDATE_BOARDGAME. */
	public static final String UPDATE_BOARDGAME = "update_b";
	
	/** The Constant DELETE_BOARDGAME. */
	public static final String DELETE_BOARDGAME = "delete_b";
	
	/** The Constant SHOW_PLAYS. */
	public static final String SHOW_PLAYS = "show_plays";
	
	/** The Constant SHOW_PLAY. */
	public static final String SHOW_PLAY = "show_p";
	
	/** The Constant CREATE_PLAY. */
	public static final String CREATE_PLAY = "create_p";
	
	/** The Constant UPDATE_PLAY. */
	public static final String UPDATE_PLAY = "update_p";
	
	/** The Constant DELETE_PLAY. */
	public static final String DELETE_PLAY = "delete_p";	
	
	/**
	 * Gets the name.
	 *
	 * @return the name
	 */
	public String getName();
	
	/**
	 * Gets the parameters.
	 *
	 * @return the parameters
	 */
	public Object[] getParameters();
	
	/**
	 * Execute.
	 *
	 * @param cv the cv
	 * @param outSymbol the out symbol
	 * @param errorSymbol the error symbol
	 * @return the string
	 * @throws Exception the exception
	 */
	public String execute(ClientView cv, String outSymbol, String errorSymbol) throws Exception;
}
