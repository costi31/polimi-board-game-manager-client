package com.herokuapp.polimiboardgamemanager.client.view;

import java.io.PrintStream;
import java.net.URI;
import java.util.List;
import java.util.Scanner;

import javax.ws.rs.core.Response;

import com.herokuapp.polimiboardgamemanager.model.User;


public class CommandLine extends ClientView {
	
	private static final String OUT_SYMBOL = "# ";
	private static final String ERROR_SYMBOL = "*** ";
	private static final String IN_SYMBOL = " > ";
	private static final String LINE = "-----------------------------------------------";
	
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
    			out.println(ERROR_SYMBOL + "Command error! Retry!");
    		}
    		
    	} while(com != Command.QUIT);
    	
    	out.println(OUT_SYMBOL + "Quit!");
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
    				out.println(ERROR_SYMBOL + "Login failed! Wrong username or password!");
    			break;
    		case SHOW_USERS:
    			String[] filters = null;
    			String[] orders = null;
    			if (params.length > 0)
    				filters = params[0].split(";");
    			if (params.length == 2)
    				orders = params[1].split(";");
    			List<User> users = getAllUsers(filters, orders);
    			for (User u : users)
    				out.println(u);
    			break;
    		case SHOW_U:
    			long id = Long.parseLong(params[0]);
    			User u = getUser(id);
    			if (u != null)
    				out.println(OUT_SYMBOL + "User " + id + " info: "+u.toString());
    			else
    				out.println(ERROR_SYMBOL + "Error! User " + id + " doesn't exist!");
    			break;
    		case CREATE_U:
    			String fullName = params[0];
    			String username = params[1];
    			String password = params[2];
    			
    			Response response = createUser(fullName, username, password);
    	        
    			if (response.getStatus() == Response.Status.CREATED.getStatusCode()) {
	    	        URI newUserLocation = response.getLocation();
	    	        System.out.print(newUserLocation);
	    	        String path = newUserLocation.getPath();
	    	        out.println(OUT_SYMBOL + "New user successfully created with id = " +
	    	        			path.substring(path.lastIndexOf('/')+1) );
    			} else {
    				out.println(ERROR_SYMBOL + "Error! "+response.readEntity(String.class));
    			}
    		default:
    	}
    }
    
    private void printMenu() {
    	out.println("");
    	out.println(LINE);
    	out.println(OUT_SYMBOL + "AVAILABLE COMMANDS (<code> - <name> : <description>)");
    	out.println("");
    	for (Command availCom : Command.values()) {
    		out.println("\t" + availCom.getCode() + " - " + 
    					availCom.toString().toLowerCase() + " : " + 
    					availCom.getDescription());
    	}
    	
    	out.println("");
    	out.println(LINE);
    	out.println("");
    	
    	out.println(OUT_SYMBOL + "Enter the desired command (you can write code or name):");
    	out.print(IN_SYMBOL);
    }
}
