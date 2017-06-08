package com.herokuapp.polimiboardgamemanager.client.view.command;

import java.net.URI;

import javax.ws.rs.core.Response;

import com.beust.jcommander.Parameter;
import com.beust.jcommander.Parameters;
import com.herokuapp.polimiboardgamemanager.client.view.ClientView;

/**
 * The Class CommandUpdateUser.
 */
@Parameters(commandNames=Command.UPDATE_USER,
			commandDescription="Update a user with the optional provided information (you must login before) "
					+ "or create a new one if the id cannot be found.")
public class CommandUpdateUser implements Command {
	
	/** The id. */
	@Parameter(names="-id", description="user id", order=0)
	private Long id;
	
	/** The full name. */
	@Parameter(names={"-fn", "-fullName"}, description="full name (optional)", order=1)
	private String fullName;
	
	/** The username. */
	@Parameter(names={"-u", "-username"}, description="username (optional)", order=2)
	private String username;
	
	/** The password. */
	@Parameter(names={"-p", "-password"}, description="password (optional)", order=3)
	private String password;	

	/* (non-Javadoc)
	 * @see com.herokuapp.polimiboardgamemanager.client.view.command.Command#getName()
	 */
	@Override
	public String getName() {
		return CREATE_USER;
	}

	/* (non-Javadoc)
	 * @see com.herokuapp.polimiboardgamemanager.client.view.command.Command#getParameters()
	 */
	@Override
	public Object[] getParameters() {
		return new Object[]{id, fullName, username, password};
	}
	
	/* (non-Javadoc)
	 * @see com.herokuapp.polimiboardgamemanager.client.view.command.Command#execute(com.herokuapp.polimiboardgamemanager.client.view.ClientView, java.lang.String, java.lang.String)
	 */
	@Override
	public String execute(ClientView cv, String outSymbol, String errorSymbol) throws Exception {	
		if (id == null)
			return errorSymbol + "Error! You must specify an id!";
		
		Response res = cv.updateUser(fullName, username, password);
        
		if (res.getStatus() == Response.Status.NO_CONTENT.getStatusCode()) {
	        return outSymbol + "User with id " + id + " has been updated succesfully.";
		} else if (res.getStatus() == Response.Status.CREATED.getStatusCode()) {
	        URI newUserLocation = res.getLocation();
	        String path = newUserLocation.getPath();
	        return outSymbol + "New user successfully created with id = " +
	        	   path.substring(path.lastIndexOf('/')+1);
		} else {
			return errorSymbol + "Error! "+res.readEntity(String.class);
		}		
	}

	/**
	 * Gets the id.
	 *
	 * @return the id
	 */
	public Long getId() {
		return id;
	}

	/**
	 * Gets the full name.
	 *
	 * @return the full name
	 */
	public String getFullName() {
		return fullName;
	}

	/**
	 * Gets the username.
	 *
	 * @return the username
	 */
	public String getUsername() {
		return username;
	}

	/**
	 * Gets the password.
	 *
	 * @return the password
	 */
	public String getPassword() {
		return password;
	}

}
