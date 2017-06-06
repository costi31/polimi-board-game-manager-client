package com.herokuapp.polimiboardgamemanager.client.view.command;

import com.beust.jcommander.Parameter;
import com.beust.jcommander.Parameters;

@Parameters(commandNames=Command.DELETE_USER,
			commandDescription="Delete a user (you must login before).")
public class CommandDeleteUser implements Command {
	
	@Parameter(names="-id", description="user id", order=0)
	private Long id;

	@Override
	public String getName() {
		return DELETE_USER;
	}

	@Override
	public Object[] getParameters() {
		return new Object[]{id};
	}

	public Long getId() {
		return id;
	}

}
