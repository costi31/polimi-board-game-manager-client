package com.herokuapp.polimiboardgamemanager.client.view.command;

import com.beust.jcommander.Parameters;

@Parameters(commandNames=Command.QUIT, commandDescription="Quit application.")
public class CommandQuit implements Command {
	
	@Override
	public String getName() {
		return QUIT;
	}		

	@Override
	public Object[] getParameters() {
		return null;
	}

}
