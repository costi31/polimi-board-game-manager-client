package com.herokuapp.polimiboardgamemanager.client.view.command;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.beust.jcommander.JCommander;

public class CommandManager {
	private static CommandManager instance = null;
	
	private Map<String, Command> commandsMap;
	
	private JCommander jc;
	
	private CommandManager() {
		commandsMap = new HashMap<>();
		
		setupCommander();
	}
	
	public static CommandManager getInstance() {
		if (instance == null)
			instance = new CommandManager();
		
		return instance;
	}

	public JCommander getJc() {
		return jc;
	}
	
	public Command parseCommandLine(String commandLine) {
		// Split the line by spaces except when they are inside quotes
		List<String> matchList = new ArrayList<>();
		Pattern regex = Pattern.compile("[^\\s\"']+|\"([^\"]*)\"|'([^']*)'");
		Matcher regexMatcher = regex.matcher(commandLine.trim());
		while (regexMatcher.find()) {
		    if (regexMatcher.group(1) != null) {
		        // Add double-quoted string without the quotes
		        matchList.add(regexMatcher.group(1));
		    } else if (regexMatcher.group(2) != null) {
		        // Add single-quoted string without the quotes
		        matchList.add(regexMatcher.group(2));
		    } else {
		        // Add unquoted word
		        matchList.add(regexMatcher.group());
		    }
		}
		
		return parseCommand(matchList.toArray(new String[matchList.size()]));
	}
	
	public Command parseCommand(String[] commandArgs) {
		// I setup again the commander to reset all the commands
		setupCommander();
		jc.parse(commandArgs);
		return commandsMap.get(jc.getParsedCommand());
	}
	
	public void printUsage() {
		jc.usage();
	}
	
	public void printUsage(String[] commands) {
		for (String c: commands)
			jc.usage(c);
	}	
	
	private void setupCommander() {
		// Setup the map of commands
		commandsMap.put(Command.HELP, new CommandHelp());
		commandsMap.put(Command.LOGIN, new CommandLogin());
		commandsMap.put(Command.QUIT, new CommandQuit());
		
		commandsMap.put(Command.SHOW_USERS, new CommandShowUsers());
		commandsMap.put(Command.SHOW_USER, new CommandShowUser());
		commandsMap.put(Command.COUNT_USERS, new CommandCountUsers());
		commandsMap.put(Command.CREATE_USER, new CommandCreateUser());
		commandsMap.put(Command.UPDATE_USER, new CommandUpdateUser());
		commandsMap.put(Command.DELETE_USER, new CommandDeleteUser());
		
		commandsMap.put(Command.SHOW_BOARDGAMES, new CommandShowBoardGames());
		commandsMap.put(Command.SHOW_BOARDGAME, new CommandShowBoardGame());
		commandsMap.put(Command.CREATE_BOARDGAME, new CommandCreateBoardGame());
		commandsMap.put(Command.UPDATE_BOARDGAME, new CommandUpdateBoardGame());
		commandsMap.put(Command.DELETE_BOARDGAME, new CommandDeleteBoardGame());
		
		commandsMap.put(Command.SHOW_PLAYS, new CommandShowPlays());
		commandsMap.put(Command.SHOW_PLAY, new CommandShowPlay());
		commandsMap.put(Command.DELETE_PLAY, new CommandDeletePlay());
		
		// Setup JCommander
		jc = JCommander.newBuilder().build();
		jc.setProgramName("");
//		jc.setAllowAbbreviatedOptions(true);
		jc.setCaseSensitiveOptions(false);
		
		for (Command com : commandsMap.values()) {
		    jc.addCommand(com);
		}
	}
}
