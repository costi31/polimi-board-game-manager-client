package com.herokuapp.polimiboardgamemanager.client.view;

import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.core.Link;

public enum Command {
	
    // ======================================
    // =             Commands               =
    // ======================================	
	
	QUIT(0, "Quit application"),
	LOGIN(1, "Login with username and password. Format: login <username> <password>", 2),
	SHOW_USERS(2, "Show the users with optional filter and ordering criteria." +
				  "Format: show_users [<filter_name>@<filter_value>;...] [<order_by>@<order_mode>]", -1),
	SHOW_U(3, "Show a specific user. Format: show_u <id>", 1);
	
    // ======================================
    // =             Constants              =
    // ======================================
	
	private static final String COMMAND_NOT_VALID_MSG = "Command not valid!";
	
    // ======================================
    // =             Attributes             =
    // ======================================	
	
	private final int code;
	private final String description;
	/** Number of parameters needed. Value -1 means indefinite number. */
	private final int parametersNeeded;
	/** List of command parameters. */
	private List<String> parameters;
	
	private Command(int code, String description) {
		this(code, description, 0);
	}
	
	private Command(int code, String description, int parametersNeeded) {
		this.code = code;
		this.description = description;
		this.parametersNeeded = parametersNeeded;
		if (parametersNeeded != 0)
			parameters = new ArrayList<>();
	}
	
    // ======================================
    // =          Static methods            =
    // ====================================== 	
	
	public static Command readCommand(String line) {
		String[] splitLine = line.split(" ");
			
		try {
			Command com = getCommand(splitLine[0]);
			
			int i = 0;
			for (String param : splitLine) {
				
				// Ignore the first param that is the command itself
				if (com.parametersNeeded > 0 && i >= com.parametersNeeded + 1) // take only needed parameters at most
					break;
				if (i > 0)
					com.parameters.add(param);
				
				i++;
			}
			
			return com;
		} catch (Exception e) {
			throw new IllegalArgumentException(COMMAND_NOT_VALID_MSG, e);
		}
	}
	
	public static Command getCommand(String comString) {
        try {       	
        	Command com = getCommandFromCode(Integer.parseInt(comString));
        	if (com != null)
        		return com;
        	else
        		throw new IllegalArgumentException(COMMAND_NOT_VALID_MSG);
        } catch (Exception e1) {
	        try {
	        	return valueOf(comString.toUpperCase());
	        } catch (Exception e2) {
	        	throw new IllegalArgumentException(COMMAND_NOT_VALID_MSG, e2);
	        }
        }
	}
	
	public static Command getCommandFromCode(int c) {
		for (Command com : values()) {
			if (com.code == c)
				return com;
		}
		
		return null;
	}
	
    // ======================================
    // =          Getters & Setters         =
    // ====================================== 	
	
    public int getCode() {
		return code;
	}

	public String getDescription() {
		return description;
	}

	public int getParametersNeeded() {
		return parametersNeeded;
	}

	
	/**
	 * Checks if the command needs other parameters
	 * @return <b>true</b> if it needs other parameters, <b>false</b> otherwise
	 */
	public boolean needsParameters() {
		return parametersNeeded != 0;
	}

	public String[] getParameters() {
		return parameters.toArray(new String[parameters.size()]);
	}
	
}
