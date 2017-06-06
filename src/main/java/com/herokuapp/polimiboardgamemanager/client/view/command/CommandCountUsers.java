package com.herokuapp.polimiboardgamemanager.client.view.command;

import com.beust.jcommander.Parameters;
import com.herokuapp.polimiboardgamemanager.client.view.ClientView;

@Parameters(commandNames=Command.COUNT_USERS, commandDescription="Show the count of users.")
public class CommandCountUsers implements Command {
	
	@Override
	public String getName() {
		return COUNT_USERS;
	}		

	@Override
	public Object[] getParameters() {
		return null;
	}

	@Override
	public String execute(ClientView cv, String outSymbol, String errorSymbol) throws Exception {
		return String.valueOf(cv.getUsersCount());
	}
}
