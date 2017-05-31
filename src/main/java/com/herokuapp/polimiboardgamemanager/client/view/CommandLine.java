package com.herokuapp.polimiboardgamemanager.client.view;

import java.io.PrintStream;
import java.util.List;
import java.util.Scanner;

import javax.ws.rs.client.Entity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;


public class CommandLine extends ClientView {
	
	private static final String OUT_SYMBOL = "# ";
	private static final String IN_SYMBOL = " > ";
	
	private PrintStream out;
	private Scanner in;

    /**
     * Instantiates a new CommandLine.
     */
    CommandLine() {
    	out = System.out;
    	in = new Scanner(System.in);
    }
    
    @Override
    public void run() {
    	out.println("Polimi Board Game Manager - Client");
    	
    	Command com = null;
    	do {
    		
    		printMenu();
	
    		try {
    			com = Command.readCommand(in.nextLine());
    			if (com != Command.QUIT)
    				executeCommand(com);
    		} catch(Exception e) {
    			out.println(OUT_SYMBOL + e.getMessage() + " Retry!");
    		}
    		
    	} while(com != Command.QUIT);
    	
    	out.println(OUT_SYMBOL + "Quitting...");
    }
    
    @Override
    protected void executeCommand(Command com) throws Exception {
    	String[] params = null;
    	if (com.needsParameters())
    		params = com.getParameters();
    	
    	switch(com) {
    		case LOGIN:
    			Response res = loginUser(params[0], params[1]);
    			if (res.getStatus() == Response.Status.OK.getStatusCode())
    				out.println(OUT_SYMBOL + "Login successful!");
    			else
    				out.println(OUT_SYMBOL + "Login error! Wrong username or password!");
    			break;
    	}
    }
    
    private void printMenu() {
    	out.println("");
    	out.println(OUT_SYMBOL + "AVAILABLE COMMANDS (<code> - <name> : <description>)");
    	out.println("");
    	for (Command availCom : Command.values()) {
    		out.println("\t" + availCom.getCode() + " - " + 
    					availCom.toString().toLowerCase() + " : " + 
    					availCom.getDescription());
    	}
    	
    	out.println("");
    	out.println("-----------------------------------------------");
    	out.println("");
    	
    	out.println(OUT_SYMBOL + "Enter the desired command (you can write code or name):");
    	out.print(IN_SYMBOL);
    }
}
