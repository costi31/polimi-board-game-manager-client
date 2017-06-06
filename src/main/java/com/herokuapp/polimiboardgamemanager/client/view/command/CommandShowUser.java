package com.herokuapp.polimiboardgamemanager.client.view.command;

import com.beust.jcommander.Parameter;
import com.beust.jcommander.Parameters;

@Parameters(commandNames=Command.SHOW_USER, commandDescription="Show the information of the user with the desired id.")
public class CommandShowUser implements Command {
	
	@Parameter(names="-id", description="user id")
	private Long id;

	@Override
	public String getName() {
		return SHOW_USER;
	}

	@Override
	public Object[] getParameters() {
		return new Object[]{id};
	}

	public Long getId() {
		return id;
	}

}
