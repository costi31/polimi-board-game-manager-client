package com.herokuapp.polimiboardgamemanager.client.view.command;

import java.util.List;

import com.beust.jcommander.Parameter;
import com.beust.jcommander.Parameters;
import com.herokuapp.polimiboardgamemanager.client.view.ClientView;

/**
 * The Class CommandHelp.
 */
@Parameters(commandNames=Command.HELP, commandDescription="Show the entire usage menu or the usage of a single command.")
public class CommandHelp implements Command {
	
	/** The commands. */
	@Parameter(names = {"--c", "--command"},
			   description = "The desired command whose the usage has to be shown. It can be specified multiple times.")
	private List<String> commands;	
	
	/* (non-Javadoc)
	 * @see com.herokuapp.polimiboardgamemanager.client.view.command.Command#getName()
	 */
	@Override
	public String getName() {
		return HELP;
	}		

	/* (non-Javadoc)
	 * @see com.herokuapp.polimiboardgamemanager.client.view.command.Command#getParameters()
	 */
	@Override
	public Object[] getParameters() {
		return new Object[]{commands};
	}
	
	/* (non-Javadoc)
	 * @see com.herokuapp.polimiboardgamemanager.client.view.command.Command#execute(com.herokuapp.polimiboardgamemanager.client.view.ClientView, java.lang.String, java.lang.String)
	 */
	@Override
	public String execute(ClientView cv, String outSymbol, String errorSymbol) throws Exception {
		if (commands != null)
			CommandManager.getInstance().printUsage(commands.toArray(new String[commands.size()]));
		else
			CommandManager.getInstance().printUsage();
		return null;
	}

	/**
	 * Gets the commands.
	 *
	 * @return the commands
	 */
	public List<String> getCommands() {
		return commands;
	}

}
