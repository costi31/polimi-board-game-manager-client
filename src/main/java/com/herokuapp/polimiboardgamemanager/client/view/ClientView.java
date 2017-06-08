package com.herokuapp.polimiboardgamemanager.client.view;

import java.net.URI;
import java.sql.Time;
import java.util.Calendar;
import java.util.List;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.Form;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriBuilder;

import org.glassfish.jersey.client.ClientConfig;

import com.herokuapp.polimiboardgamemanager.client.view.command.Command;
import com.herokuapp.polimiboardgamemanager.model.BoardGame;
import com.herokuapp.polimiboardgamemanager.model.Play;
import com.herokuapp.polimiboardgamemanager.model.User;

/**
 * The Class ClientView.
 */
public abstract class ClientView {
	
	/** The Constant USERS_PATH. */
	private static final String USERS_PATH = "/users";
	
	/** The Constant BOARDGAMES_PATH. */
	private static final String BOARDGAMES_PATH = "/boardgames";
	
	/** The instance. */
	private static ClientView instance = null;
	
	/** The client config. */
	protected ClientConfig config;
	
	/** The client. */
	protected Client client;
	
	/** The target. */
	protected WebTarget target;
	
	/** The authorization bearer. */
	protected String authorizationBearer;
	
	/**  Id of the authenticated user. */
	protected Long authUserId = null;
	
	/**
	 * Gets the cli instance.
	 *
	 * @return the cli instance
	 */
	public static ClientView getCliInstance() {
		if (instance == null) {
			instance = new CommandLine();
			instance.setup();
		}
		
		return instance;
	}
	
    /**
     * Gets the base URI.
     *
     * @return the base URI
     */
    protected static URI getBaseURI() {
        return UriBuilder.fromUri("https://polimi-board-game-manager.herokuapp.com/api/").build();
    }   
    
    /**
     * Gets the id from URI.
     *
     * @param uri the uri
     * @return the id from URI
     */
    protected static Long getIdFromURI(URI uri) {
    	try {
	        String path = uri.getPath();
	        return Long.parseLong( path.substring(path.lastIndexOf('/')+1) );
    	} catch(Exception e) {
	    	return null;
	    }
    }
	
	/**
	 * Run.
	 */
	public abstract void run();
	
	/**
	 * Execute command.
	 *
	 * @param com the command
	 * @throws Exception the exception
	 */
	protected abstract void executeCommand(Command com) throws Exception;
	
	/**
	 * Gets the authenticated user id.
	 *
	 * @return the auth user id
	 */
	public Long getAuthUserId() {
		return authUserId;
	}	
	
    // ======================================
    // =          User management           =
    // ======================================

	/**
     * Login user.
     *
     * @param username the username
     * @param password the password
     * @return the response
     */
    public Response loginUser(String username, String password) {
		authorizationBearer = null;
		authUserId = null;
        Form form = new Form();
        form.param("username", username);
        form.param("password", password);
        Response res = target.path(USERS_PATH+"/login").request().post(Entity.form(form));
        if (res.getStatus() == Response.Status.OK.getStatusCode()) {
        	authorizationBearer = res.getHeaderString(HttpHeaders.AUTHORIZATION);
        	authUserId = getIdFromURI(res.getLink("self").getUri());
        }
        return res;
	}
	
	/**
	 * Gets the all users.
	 *
	 * @param filters the filters
	 * @param orders the orders
	 * @return the all users
	 */
	public List<User> getAllUsers(Object[] filters, Object[] orders) {
		WebTarget tempTarget = target.path(USERS_PATH);
		if (filters != null)
			tempTarget = tempTarget.queryParam("filter", filters);
		if (orders != null)
			tempTarget = tempTarget.queryParam("order", orders);
		
		return tempTarget.request(MediaType.APPLICATION_JSON_TYPE).get(new GenericType<List<User>>() {});
	}
	
	/**
	 * Gets the user.
	 *
	 * @param id the id
	 * @return the user
	 */
	public User getUser(long id) {
		try {
			return target.path(USERS_PATH+"/"+id).request(MediaType.APPLICATION_JSON_TYPE).get(User.class);
		} catch(Exception e) {
			return null;
		}
	}
	
	/**
	 * Gets the users count.
	 *
	 * @return the users count
	 */
	public long getUsersCount() {
		return target.path(USERS_PATH+"/count").request(MediaType.TEXT_PLAIN_TYPE).get(Long.class);
	}
	
	/**
	 * Creates the user.
	 *
	 * @param fullName the full name
	 * @param username the username
	 * @param password the password
	 * @return the response
	 */
	public Response createUser(String fullName, String username, String password) {
        Form form = new Form();
        form.param("fullName", fullName);
        form.param("username", username);
        form.param("password", password);
        return target.path(USERS_PATH).request().post(Entity.form(form));
        
	}
	
	/**
	 * Update user.
	 *
	 * @param fullName the full name
	 * @param username the username
	 * @param password the password
	 * @return the response
	 */
	public Response updateUser(String fullName, String username, String password) {
        Form form = new Form();
        if (fullName != null)
        	form.param("fullName", fullName);
        if (username != null)
        	form.param("username", username);    
        if (password != null)
        	form.param("password", password);
        
        return target.path(USERS_PATH+"/"+authUserId).request()
        		.header(HttpHeaders.AUTHORIZATION, authorizationBearer)
        		.put(Entity.form(form));
        
	}
	
	/**
	 * Delete user.
	 *
	 * @return the response
	 */
	public Response deleteUser() {
		return target.path(USERS_PATH+"/"+authUserId).request()
				.header(HttpHeaders.AUTHORIZATION,  authorizationBearer)
				.delete();
	}
	
    // ======================================
    // =       Board games management       =
    // ======================================	
	
	/**
     * Gets the all board games.
     *
     * @param filters the filters
     * @param orders the orders
     * @return the all board games
     */
    public List<BoardGame> getAllBoardGames(Object[] filters, Object[] orders) {
		WebTarget tempTarget = target.path(BOARDGAMES_PATH);
		if (filters != null)
			tempTarget = tempTarget.queryParam("filter", filters);
		if (orders != null)
			tempTarget = tempTarget.queryParam("order", orders);
		
		return tempTarget.request(MediaType.APPLICATION_JSON_TYPE).get(new GenericType<List<BoardGame>>() {});
	}	
	
	/**
	 * Gets the board game.
	 *
	 * @param id the id
	 * @return the board game
	 */
	public BoardGame getBoardGame(Long id) {
		try {
			return target.path(BOARDGAMES_PATH+"/"+id).request(MediaType.APPLICATION_JSON_TYPE).get(BoardGame.class);
		} catch(Exception e) {
			return null;
		}
	}	
	
	/**
	 * Gets the board games count.
	 *
	 * @return the board games count
	 */
	public long getBoardGamesCount() {
		return target.path(BOARDGAMES_PATH).request(MediaType.TEXT_PLAIN_TYPE).get(Long.class);
	}	
	
	/**
	 * Creates the board game.
	 *
	 * @param boardGame the board game
	 * @return the response
	 */
	public Response createBoardGame(BoardGame boardGame) {
        return target.path(BOARDGAMES_PATH).request().
        		header(HttpHeaders.AUTHORIZATION, authorizationBearer).
        		post(Entity.entity(boardGame, MediaType.APPLICATION_JSON_TYPE));
	}
	
	/**
	 * Update board game.
	 *
	 * @param id the id
	 * @param name the name
	 * @param designers the designers
	 * @param cover the cover
	 * @return the response
	 */
	public Response updateBoardGame(Long id, String name, String designers, String cover) {        
        BoardGame b = getBoardGame(id);
        if (b == null)
        	b = new BoardGame(name, designers, cover);
        
        if (name != null)
        	b.setName(name);
        if (designers != null)
        	b.setDesigners(designers);
        if (cover != null)
        	b.setCover(cover);
        
        return target.path(BOARDGAMES_PATH+"/"+id).request()
        		.header(HttpHeaders.AUTHORIZATION, authorizationBearer)
        		.put(Entity.entity(b, MediaType.APPLICATION_JSON_TYPE));        
	}	
	
	/**
	 * Delete board game.
	 *
	 * @param id the id
	 * @return the response
	 */
	public Response deleteBoardGame(Long id) {
		return target.path(BOARDGAMES_PATH+"/"+id).request()
				.header(HttpHeaders.AUTHORIZATION,  authorizationBearer)
				.delete();
	}	
	
    // ======================================
    // =       Plays management       =
    // ======================================	
	
	/**
     * Gets the all plays.
     *
     * @param userId the user id
     * @param filters the filters
     * @param orders the orders
     * @return the all plays
     */
    public List<Play> getAllPlays(Long userId, Object[] filters, Object[] orders) {
		WebTarget tempTarget = target.path(USERS_PATH+"/"+userId+"/plays");
		if (filters != null)
			tempTarget = tempTarget.queryParam("filter", filters);
		if (orders != null)
			tempTarget = tempTarget.queryParam("order", orders);
		
		return tempTarget.request(MediaType.APPLICATION_JSON_TYPE).get(new GenericType<List<Play>>() {});
	}	
	
	/**
	 * Gets the play.
	 *
	 * @param userId the user id
	 * @param id the id
	 * @return the play
	 */
	public Play getPlay(Long userId, Long id) {
		try {
			return target.path(USERS_PATH+"/"+userId+"/plays/"+id).request(MediaType.APPLICATION_JSON_TYPE).get(Play.class);
		} catch(Exception e) {
			return null;
		}
	}	
	
	/**
	 * Creates the play.
	 *
	 * @param play the play
	 * @return the response
	 */
	public Response createPlay(Play play) {
        return target.path(USERS_PATH+"/"+authUserId+"/plays").request().
        		header(HttpHeaders.AUTHORIZATION, authorizationBearer).
        		post(Entity.entity(play, MediaType.APPLICATION_JSON_TYPE));
	}
	
	/**
	 * Update play.
	 *
	 * @param id the id
	 * @param boardGameId the board game id
	 * @param date the date
	 * @param playersInvolved the players involved
	 * @param completed the completed
	 * @param timeToComplete the time to complete
	 * @param userWinnerId the user winner id
	 * @return the response
	 */
	public Response updatePlay(Long id, Long boardGameId, Calendar date, Integer playersInvolved, 
							   boolean completed, Time timeToComplete, Long userWinnerId) { 
		
        Play p = getPlay(authUserId, id);
        if (p == null)
        	p = new Play(getUser(authUserId), getBoardGame(boardGameId), date,
        				 playersInvolved != null ? playersInvolved : 0, 
        				 completed, timeToComplete,
        				 userWinnerId != null ? getUser(userWinnerId) : null);
        
        if (boardGameId != null)
        	p.setBoardGame(getBoardGame(boardGameId));
        if (playersInvolved != null)
        	p.setPlayersInvolved(playersInvolved);
        if (timeToComplete != null)
        	p.setTimeToComplete(timeToComplete);
        if (userWinnerId != null)
        	p.setUserWinner(getUser(userWinnerId));
        
        return target.path(USERS_PATH+"/"+authUserId+"/plays/"+id).request()
        		.header(HttpHeaders.AUTHORIZATION, authorizationBearer)
        		.put(Entity.entity(p, MediaType.APPLICATION_JSON_TYPE));        
	}	
	
	/**
	 * Delete play.
	 *
	 * @param id the id
	 * @return the response
	 */
	public Response deletePlay(Long id) {
		return target.path(USERS_PATH+"/"+authUserId+"/plays/"+id).request()
				.header(HttpHeaders.AUTHORIZATION,  authorizationBearer)
				.delete();
	}		
	
    // ======================================
    // =               Setup                =
    // ======================================		
	
	/**
     * Setup.
     */
    private void setup() {
        config = new ClientConfig();
        client = ClientBuilder.newClient(config);		
		target = client.target(getBaseURI());
	}
}
