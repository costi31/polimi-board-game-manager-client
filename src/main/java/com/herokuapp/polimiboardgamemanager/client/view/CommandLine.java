package com.herokuapp.polimiboardgamemanager.client.view;

import java.io.PrintStream;
import java.net.URI;
import java.util.List;
import java.util.Scanner;

import javax.ws.rs.core.Response;

import com.herokuapp.polimiboardgamemanager.client.view.command.Command;
import com.herokuapp.polimiboardgamemanager.client.view.command.CommandCreateUser;
import com.herokuapp.polimiboardgamemanager.client.view.command.CommandHelp;
import com.herokuapp.polimiboardgamemanager.client.view.command.CommandLogin;
import com.herokuapp.polimiboardgamemanager.client.view.command.CommandManager;
import com.herokuapp.polimiboardgamemanager.client.view.command.CommandShowUser;
import com.herokuapp.polimiboardgamemanager.client.view.command.CommandShowUsers;
import com.herokuapp.polimiboardgamemanager.client.view.command.CommandUpdateUser;
import com.herokuapp.polimiboardgamemanager.model.User;


public class CommandLine extends ClientView {
	
	private static final String OUT_SYMBOL = "# ";
	private static final String ERROR_SYMBOL = "*** ";
	private static final String IN_SYMBOL = " > ";
	private static final String LINE = "-----------------------------------------------";
	
	private PrintStream out;
	private Scanner in;
	private CommandManager comManager;

    /**
     * Instantiates a new CommandLine.
     */
    CommandLine() {
    	out = System.out;
    	in = new Scanner(System.in);
    	comManager = CommandManager.getInstance();
    }
    
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
    			printInputLine();
    		}
    		
    		
    		
    	} while(com == null || com.getName() != Command.QUIT);
    	
    	out.println(OUT_SYMBOL + "Quit!");
    }
    
    @Override
    protected void executeCommand(Command com) throws Exception {
    	
    	String cn = com.getName();
    	
    	if(cn.equals(Command.HELP)) {
			List<String> helpCommands = ((CommandHelp) com).getCommands();
			if (helpCommands != null)
				comManager.printUsage(helpCommands.toArray(new String[helpCommands.size()]));
			else
				comManager.printUsage();
			
    	} else if (cn.equals(Command.LOGIN)) {
			Response res = loginUser( ((CommandLogin) com).getUsername(), ((CommandLogin) com).getPassword());
			if (res.getStatus() == Response.Status.OK.getStatusCode())
				out.println(OUT_SYMBOL + "Login successful!");
			else
				out.println(ERROR_SYMBOL + "Login failed! Wrong username or password!");
			
    	} else if (cn.equals(Command.SHOW_USERS)) {
			List<String> filters = ((CommandShowUsers) com).getFilters();
			List<String> orders = ((CommandShowUsers) com).getOrders();
			List<User> users = getAllUsers(filters != null ? filters.toArray() : null,
										   orders != null ? orders.toArray() : null);
			for (User u : users)
				out.println(u);
			
    	} else if (cn.equals(Command.SHOW_USER)) {
			long id = ((CommandShowUser) com).getId();
			User u = getUser(id);
			if (u != null)
				out.println(OUT_SYMBOL + "User " + id + " info: "+u.toString());
			else
				out.println(ERROR_SYMBOL + "Error! User " + id + " doesn't exist!");
			
    	} else if (cn.equals(Command.CREATE_USER)) {
			String fullName = ((CommandCreateUser) com).getFullName();
			String username = ((CommandCreateUser) com).getUsername();
			String password = ((CommandCreateUser) com).getPassword();
			
			Response response = createUser(fullName, username, password);
	        
			if (response.getStatus() == Response.Status.CREATED.getStatusCode()) {
    	        URI newUserLocation = response.getLocation();
    	        String path = newUserLocation.getPath();
    	        out.println(OUT_SYMBOL + "New user successfully created with id = " +
    	        			path.substring(path.lastIndexOf('/')+1) );
			} else {
				out.println(ERROR_SYMBOL + "Error! "+response.readEntity(String.class));
			}
			
    	} else if (cn.equals(Command.UPDATE_USER)) {
    		Long id = ((CommandUpdateUser) com).getId();
    		
    		if (id == null) {
    			out.println(ERROR_SYMBOL + "Error! You must specify an id!");
    			return;
    		}
    		
			String fullName = ((CommandUpdateUser) com).getFullName();
			String username = ((CommandUpdateUser) com).getUsername();
			String password = ((CommandUpdateUser) com).getPassword();
			
			Response response = updateUser(id, fullName, username, password);
	        
			if (response.getStatus() == Response.Status.NO_CONTENT.getStatusCode()) {
    	        out.println(OUT_SYMBOL + "User with id " + id + " has been updated succesfully." );
			} else if (response.getStatus() == Response.Status.CREATED.getStatusCode()) {
    	        URI newUserLocation = response.getLocation();
    	        String path = newUserLocation.getPath();
    	        out.println(OUT_SYMBOL + "New user successfully created with id = " +
    	        			path.substring(path.lastIndexOf('/')+1) );
			} else {
				out.println(ERROR_SYMBOL + "Error! "+response.readEntity(String.class));
			}
    	}
    }
    
    private void printMenu() {
    	out.println("");
    	out.println(LINE);
    	out.println(OUT_SYMBOL + "AVAILABLE COMMANDS");
    	out.println("");
    	
    	comManager.printUsage();
    	
    	printInputLine();
    }
    
    private void printInputLine() {
    	out.println(LINE);
    	out.println(OUT_SYMBOL + "Enter the desired command or \"help\" to show the menu:");
    	out.print(IN_SYMBOL);
    }
}
