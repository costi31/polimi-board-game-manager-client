package com.herokuapp.polimiboardgamemanager.client.view;

import java.net.URI;
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
import com.herokuapp.polimiboardgamemanager.model.User;

public abstract class ClientView {
	
	private static final String USERS_PATH = "/users";
	
	private static ClientView instance = null;
	
	protected ClientConfig config;
	protected Client client;
	protected WebTarget target;
	protected String authorizationBearer;
	
	public static ClientView getCliInstance() {
		if (instance == null) {
			instance = new CommandLine();
			instance.setup();
		}
		
		return instance;
	}
	
    private static URI getBaseURI() {
        return UriBuilder.fromUri("https://polimi-board-game-manager.herokuapp.com/api/").build();
    }   	
	
	public abstract void run();
	
	protected abstract void executeCommand(Command com) throws Exception;
	
	public Response loginUser(String username, String password) {
		authorizationBearer = null;
        Form form = new Form();
        form.param("username", username);
        form.param("password", password);
        Response res = target.path(USERS_PATH+"/login").request().post(Entity.form(form));
        if (res.getStatus() == Response.Status.OK.getStatusCode())
        	authorizationBearer = res.getHeaderString(HttpHeaders.AUTHORIZATION);
        return res;
	}
	
	public List<User> getAllUsers(Object[] filters, Object[] orders) {
		WebTarget tempTarget = target.path(USERS_PATH);
		if (filters != null)
			tempTarget = tempTarget.queryParam("filter", filters);
		if (orders != null)
			tempTarget = tempTarget.queryParam("order", orders);
		
		return tempTarget.request(MediaType.APPLICATION_JSON_TYPE).get(new GenericType<List<User>>() {});
	}
	
	public User getUser(long id) {
		return target.path(USERS_PATH+"/"+id).request(MediaType.APPLICATION_JSON_TYPE).get(User.class);
	}
	
	public Response createUser(String fullName, String username, String password) {
        Form form = new Form();
        form.param("fullName", fullName);
        form.param("username", username);
        form.param("password", password);
        return target.path(USERS_PATH).request().post(Entity.form(form));
        
	}
	
	public Response updateUser(long id, String fullName, String username, String password) {
        Form form = new Form();
        if (fullName != null)
        	form.param("fullName", fullName);
        if (username != null)
        	form.param("username", username);    
        if (password != null)
        	form.param("password", password);
        
        return target.path(USERS_PATH+"/"+id).request()
        		.header(HttpHeaders.AUTHORIZATION, authorizationBearer)
        		.put(Entity.form(form));
        
	}
	
	public Response deleteUser(long id) {
		return target.path(USERS_PATH+"/"+id).request()
				.header(HttpHeaders.AUTHORIZATION,  authorizationBearer)
				.delete();
	}
	
	private void setup() {
        config = new ClientConfig();
        client = ClientBuilder.newClient(config);		
		target = client.target(getBaseURI());
	}
}
