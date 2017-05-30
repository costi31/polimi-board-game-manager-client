package com.herokuapp.polimiboardgamemanager.client.view.cli;

import java.util.ArrayList;
import java.util.List;

public enum Command {
	
    // ======================================
    // =             Commands               =
    // ======================================	
	
	QUIT(0, "Quit application");
	
    // ======================================
    // =             Constants              =
    // ======================================
	
	private static final String COMMAND_NOT_VALID_MSG = "Command not valid!";
	
    // ======================================
    // =             Attributes             =
    // ======================================	
	
	private int code;
	private String description;
	/** Number of parameters needed. */
	private int parametersNeeded;
	/** List of command parameters. */
	private List<String> parameters;
	
	private Command(int code, String description) {
		this(code, description, 0);
	}
	
	private Command(int code, String description, int parametersNeeded) {
		this.code = code;
		this.description = description;
		this.parametersNeeded = parametersNeeded;
		if (parametersNeeded > 0)
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
				if (i == 0)
					continue;
				com.parameters.add(param);
				i++;
			}
			
			return com;
		} catch (Exception e) {
			throw new IllegalArgumentException(COMMAND_NOT_VALID_MSG);
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

	public void setCode(int code) {
		this.code = code;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public int getParametersNeeded() {
		return parametersNeeded;
	}

	public void setParametersNeeded(int parametersNeeded) {
		this.parametersNeeded = parametersNeeded;
	}
	
	/**
	 * Checks if the command needs other parameters
	 * @return <b>true</b> if it needs other parameters, <b>false</b> otherwise
	 */
	public boolean needsParameters() {
		return parametersNeeded > 0;
	}
	
}
