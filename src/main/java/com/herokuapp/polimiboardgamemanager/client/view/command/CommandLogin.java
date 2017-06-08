package com.herokuapp.polimiboardgamemanager.client.view.command;

import javax.ws.rs.core.Response;

import com.beust.jcommander.Parameter;
import com.beust.jcommander.Parameters;
import com.herokuapp.polimiboardgamemanager.client.view.ClientView;

/**
 * The Class CommandLogin.
 */
@Parameters(commandNames=Command.LOGIN, commandDescription="Login with username and password.")
public class CommandLogin implements Command {
	
	/** The username. */
	@Parameter(names="-u", description="username", order=0)
	private String username;
	
	/** The password. */
	@Parameter(names="-p", description="password", order=1)
	private String password;
	
	/* (non-Javadoc)
	 * @see com.herokuapp.polimiboardgamemanager.client.view.command.Command#getName()
	 */
	@Override
	public String getName() {
		return LOGIN;
	}	

	/* (non-Javadoc)
	 * @see com.herokuapp.polimiboardgamemanager.client.view.command.Command#getParameters()
	 */
	@Override
	public Object[] getParameters() {
		return new Object[]{username, password};
	}
	
	/* (non-Javadoc)
	 * @see com.herokuapp.polimiboardgamemanager.client.view.command.Command#execute(com.herokuapp.polimiboardgamemanager.client.view.ClientView, java.lang.String, java.lang.String)
	 */
	@Override
	public String execute(ClientView cv, String outSymbol, String errorSymbol) throws Exception {
		Response res = cv.loginUser(username, password);
		if (res.getStatus() == Response.Status.OK.getStatusCode())
			return outSymbol + "Login successful! Your user id is " + cv.getAuthUserId() +"\n"
					+ "Now you can manager user account, your plays or even board games if you "
					+ "are a power user.";
		else
			return errorSymbol + "Login failed! Wrong username or password!";
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
