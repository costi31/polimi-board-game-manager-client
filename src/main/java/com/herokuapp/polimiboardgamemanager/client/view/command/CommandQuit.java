package com.herokuapp.polimiboardgamemanager.client.view.command;

import com.beust.jcommander.Parameters;
import com.herokuapp.polimiboardgamemanager.client.view.ClientView;

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

	@Override
	public String execute(ClientView cv, String outSymbol, String errorSymbol) throws Exception {
		return null;
	}
}
