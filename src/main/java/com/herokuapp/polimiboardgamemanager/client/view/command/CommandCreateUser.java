package com.herokuapp.polimiboardgamemanager.client.view.command;

import com.beust.jcommander.Parameter;
import com.beust.jcommander.Parameters;

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
