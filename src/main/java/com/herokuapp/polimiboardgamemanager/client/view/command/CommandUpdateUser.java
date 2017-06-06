package com.herokuapp.polimiboardgamemanager.client.view.command;

import com.beust.jcommander.Parameter;
import com.beust.jcommander.Parameters;

@Parameters(commandNames=Command.UPDATE_USER,
			commandDescription="Update a user with the optional provided information (you must login before) "
					+ "or create a new one if the id cannot be found.")
public class CommandUpdateUser implements Command {
	
	@Parameter(names="-id", description="user id", order=0)
	private Long id;
	
	@Parameter(names={"-fn", "-fullName"}, description="full name (optional)", order=1)
	private String fullName;
	
	@Parameter(names={"-u", "-username"}, description="username (optional)", order=2)
	private String username;
	
	@Parameter(names={"-p", "-password"}, description="password (optional)", order=3)
	private String password;	

	@Override
	public String getName() {
		return CREATE_USER;
	}

	@Override
	public Object[] getParameters() {
		return new Object[]{id, fullName, username, password};
	}

	public Long getId() {
		return id;
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
