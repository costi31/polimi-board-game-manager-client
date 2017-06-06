package com.herokuapp.polimiboardgamemanager.client.view.command;

import javax.ws.rs.core.Response;

import com.beust.jcommander.Parameter;
import com.beust.jcommander.Parameters;
import com.herokuapp.polimiboardgamemanager.client.view.ClientView;

@Parameters(commandNames=Command.LOGIN, commandDescription="Login with username and password.")
public class CommandLogin implements Command {
	@Parameter(names="-u", description="username", order=0)
	private String username;
	
	@Parameter(names="-p", description="password", order=1)
	private String password;
	
	@Override
	public String getName() {
		return LOGIN;
	}	

	@Override
	public Object[] getParameters() {
		return new Object[]{username, password};
	}
	
	@Override
	public String execute(ClientView cv, String outSymbol, String errorSymbol) throws Exception {
		Response res = cv.loginUser(username, password);
		if (res.getStatus() == Response.Status.OK.getStatusCode())
			return outSymbol + "Login successful!";
		else
			return errorSymbol + "Login failed! Wrong username or password!";
	}

	public String getUsername() {
		return username;
	}

	public String getPassword() {
		return password;
	}
}
