package com.herokuapp.polimiboardgamemanager.client.view.cli;

import java.io.PrintStream;
import java.util.Scanner;


public class CommandLine {
	
	private static final String OUT_SYMBOL = "# ";
	private static final String IN_SYMBOL = " > ";
	
	private static CommandLine instance = null;
	
	private PrintStream out;
	private Scanner in;
	

    public static CommandLine getInstance() {
        if (instance == null)
            instance = new CommandLine();

        return instance;
    }

    /**
     * Instantiates a new CommandLine.
     */
    private CommandLine() {
    	out = System.out;
    	in = new Scanner(System.in);
    }
    
    public void run() {
    	Command com = null;
    	do {
    		
    		printMenu();
	
    		try {
    			com = Command.readCommand(in.nextLine());
    		} catch(Exception e) {
    			out.println(OUT_SYMBOL + e.getMessage() + " Retry!");
    		}
    		
    	} while(com != Command.QUIT);
    	
    	out.println(OUT_SYMBOL + "Quitting...");
    }
    
    public void printMenu() {
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
    	
    	out.println("Enter the desired command (you can write code or name):");
    	out.print(IN_SYMBOL);
    }
}
