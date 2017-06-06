package com.herokuapp.polimiboardgamemanager.client.view.command;

import java.util.List;

import com.beust.jcommander.Parameter;
import com.beust.jcommander.Parameters;
import com.herokuapp.polimiboardgamemanager.client.view.ClientView;

@Parameters(commandNames=Command.HELP, commandDescription="Show the entire usage menu or the usage of a single command.")
public class CommandHelp implements Command {
	
	@Parameter(names = {"--c", "--command"},
			   description = "The desired command whose the usage has to be shown. It can be specified multiple times.")
	private List<String> commands;	
	
	@Override
	public String getName() {
		return HELP;
	}		

	@Override
	public Object[] getParameters() {
		return new Object[]{commands};
	}
	
	@Override
	public String execute(ClientView cv, String outSymbol, String errorSymbol) throws Exception {
		if (commands != null)
			CommandManager.getInstance().printUsage(commands.toArray(new String[commands.size()]));
		else
			CommandManager.getInstance().printUsage();
		return null;
	}

	public List<String> getCommands() {
		return commands;
	}

}
