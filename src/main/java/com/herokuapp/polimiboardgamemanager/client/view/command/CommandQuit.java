package com.herokuapp.polimiboardgamemanager.client.view.command;

import com.beust.jcommander.Parameters;
import com.herokuapp.polimiboardgamemanager.client.view.ClientView;

/**
 * The Class CommandQuit.
 */
@Parameters(commandNames=Command.QUIT, commandDescription="Quit application.")
public class CommandQuit implements Command {
	
	/* (non-Javadoc)
	 * @see com.herokuapp.polimiboardgamemanager.client.view.command.Command#getName()
	 */
	@Override
	public String getName() {
		return QUIT;
	}		

	/* (non-Javadoc)
	 * @see com.herokuapp.polimiboardgamemanager.client.view.command.Command#getParameters()
	 */
	@Override
	public Object[] getParameters() {
		return null;
	}

	/* (non-Javadoc)
	 * @see com.herokuapp.polimiboardgamemanager.client.view.command.Command#execute(com.herokuapp.polimiboardgamemanager.client.view.ClientView, java.lang.String, java.lang.String)
	 */
	@Override
	public String execute(ClientView cv, String outSymbol, String errorSymbol) throws Exception {
		return null;
	}
}
