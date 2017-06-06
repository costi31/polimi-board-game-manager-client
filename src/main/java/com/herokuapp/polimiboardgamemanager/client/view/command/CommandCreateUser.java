package com.herokuapp.polimiboardgamemanager.client.view.command;

import java.net.URI;

import javax.ws.rs.core.Response;

import com.beust.jcommander.Parameter;
import com.beust.jcommander.Parameters;
import com.herokuapp.polimiboardgamemanager.client.view.ClientView;

@Parameters(commandNames=Command.CREATE_USER, commandDescription="Create a new user with the desired information.")
public class CommandCreateUser implements Command {
	
	@Parameter(names={"-fn", "-fullName"}, description="full name", order=0)
	private String fullName;
	
	@Parameter(names={"-u", "-username"}, description="username", order=1)
	private String username;
	
	@Parameter(names={"-p", "-password"}, description="password", order=2)
	private String password;	

	@Override
	public String getName() {
		return CREATE_USER;
	}

	@Override
	public Object[] getParameters() {
		return new Object[]{fullName, username, password};
	}
	
	@Override
	public String execute(ClientView cv, String outSymbol, String errorSymbol) throws Exception {	
		Response res = cv.createUser(fullName, username, password);
        
		if (res.getStatus() == Response.Status.CREATED.getStatusCode()) {
	        URI newUserLocation = res.getLocation();
	        String path = newUserLocation.getPath();
	        return outSymbol + "New user successfully created with id = " +
        		   path.substring(path.lastIndexOf('/')+1);
		} else {
			return errorSymbol + "Error! " + res.readEntity(String.class);
		}		
	}	

	public String getFullName() {
		return fullName;
	}

	public String getUsername() {
		return username;
	}

	public String getPassword() {
		return password;
	}

}
