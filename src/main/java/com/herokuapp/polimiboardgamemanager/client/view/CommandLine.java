package com.herokuapp.polimiboardgamemanager.client.view;

import java.io.PrintStream;
import java.util.Scanner;

import com.herokuapp.polimiboardgamemanager.client.view.command.Command;
import com.herokuapp.polimiboardgamemanager.client.view.command.CommandManager;


/**
 * The Class CommandLine.
 */
public class CommandLine extends ClientView {
	
	/** The Constant OUT_SYMBOL. */
	private static final String OUT_SYMBOL = "# ";
	
	/** The Constant ERROR_SYMBOL. */
	private static final String ERROR_SYMBOL = "*** ";
	
	/** The Constant IN_SYMBOL. */
	private static final String IN_SYMBOL = " > ";
	
	/** The Constant LINE. */
	private static final String LINE = "-----------------------------------------------";
	
	/** The out. */
	private PrintStream out;
	
	/** The in. */
	private Scanner in;
	
	/** The com manager. */
	private CommandManager comManager;

    /**
     * Instantiates a new CommandLine.
     */
    CommandLine() {
    	out =  System.out;
    	in = new Scanner(System.in);
    	comManager = CommandManager.getInstance();
    }
    
    /* (non-Javadoc)
     * @see com.herokuapp.polimiboardgamemanager.client.view.ClientView#run()
     */
    @Override
    public void run() {
    	out.println("Polimi Board Game Manager - Client");
    	
    	Command com = null;
    	do {
    		   		
    		if (com == null)
    			printMenu();
	
    		try {
        		String line = in.nextLine();
        		if (line.isEmpty()) {
        			com = null;
        			continue;
        		}
    			com = comManager.parseCommandLine(line);
    			if (! com.getName().equals(Command.QUIT))
    				executeCommand(com);
    			
    			printInputLine();					
    		} catch(Exception e) {
    			out.println(ERROR_SYMBOL + "Command error! Retry!");
    			e.printStackTrace();
    			printInputLine();
    		}
    		
    		
    		
    	} while(com == null || com.getName() != Command.QUIT);
    	
    	out.println(OUT_SYMBOL + "Quit!");
    }
    
    /* (non-Javadoc)
     * @see com.herokuapp.polimiboardgamemanager.client.view.ClientView#executeCommand(com.herokuapp.polimiboardgamemanager.client.view.command.Command)
     */
    @Override
	protected void executeCommand(Command com) throws Exception {
    	
    	String cn = com.getName();
    	
    	if(cn.equals(Command.HELP)) {
    		com.execute(this, OUT_SYMBOL, ERROR_SYMBOL);	
    	} else if (!cn.equals(Command.QUIT)) {
    		out.println(com.execute(this, OUT_SYMBOL, ERROR_SYMBOL));
    	}
    }
    
    /**
     * Prints the menu.
     */
    private void printMenu() {
    	out.println("");
    	out.println(LINE);
    	out.println(OUT_SYMBOL + "AVAILABLE COMMANDS");
    	out.println("");
    	
    	comManager.printUsage();
    	
    	printInputLine();
    }
    
    /**
     * Prints the input line.
     */
    private void printInputLine() {
    	out.println(LINE);
    	out.println(OUT_SYMBOL + "Enter the desired command or \"help\" to show the menu:");
    	out.print(IN_SYMBOL);
    }
}
