package com.herokuapp.polimiboardgamemanager.client.view.command;

import javax.ws.rs.core.Response;

import com.beust.jcommander.Parameters;
import com.herokuapp.polimiboardgamemanager.client.view.ClientView;

@Parameters(commandNames=Command.DELETE_USER,
			commandDescription="Delete your user account (you must login before).")
public class CommandDeleteUser implements Command {
	
	@Override
	public String getName() {
		return DELETE_USER;
	}

	@Override
	public Object[] getParameters() {
		return new Object[]{};
	}
	
	@Override
	public String execute(ClientView cv, String outSymbol, String errorSymbol) throws Exception {		
		Long id = cv.getAuthUserId();
		
		if (id == null)
			return errorSymbol + "Error! You must login before!";
		
		Response res = cv.deleteUser();
        
		if (res.getStatus() == Response.Status.NO_CONTENT.getStatusCode()) {
	        return outSymbol + "User with id " + id + " has been deleted succesfully.";
		} else {
			return errorSymbol + "Error! "+res.readEntity(String.class);
		}	
	}		

}
