package com.herokuapp.polimiboardgamemanager.client.view.command;

import com.beust.jcommander.Parameter;
import com.beust.jcommander.Parameters;

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

	public String getUsername() {
		return username;
	}

	public String getPassword() {
		return password;
	}
}
