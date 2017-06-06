package com.herokuapp.polimiboardgamemanager.client.view.command;

import com.beust.jcommander.Parameter;
import com.beust.jcommander.Parameters;
import com.herokuapp.polimiboardgamemanager.client.view.ClientView;
import com.herokuapp.polimiboardgamemanager.model.User;

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
	
	@Override
	public String execute(ClientView cv, String outSymbol, String errorSymbol) throws Exception {
		User u = cv.getUser(id);
		if (u != null)
			return outSymbol + "User " + id + " info: "+u.toString();
		else
			return errorSymbol + "Error! User " + id + " doesn't exist!";		
	}

	public Long getId() {
		return id;
	}

}
