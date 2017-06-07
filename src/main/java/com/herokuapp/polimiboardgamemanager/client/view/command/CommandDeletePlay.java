package com.herokuapp.polimiboardgamemanager.client.view.command;

import javax.ws.rs.core.Response;

import com.beust.jcommander.Parameter;
import com.beust.jcommander.Parameters;
import com.herokuapp.polimiboardgamemanager.client.view.ClientView;

@Parameters(commandNames=Command.DELETE_PLAY,
			commandDescription="Delete a play created by you (you must login before).")
public class CommandDeletePlay implements Command {
	
	@Parameter(names="-id", description="play id")
	private Long id;

	@Override
	public String getName() {
		return DELETE_PLAY;
	}

	@Override
	public Object[] getParameters() {
		return new Object[]{id};
	}
	
	@Override
	public String execute(ClientView cv, String outSymbol, String errorSymbol) throws Exception {		
		if (id == null)
			return errorSymbol + "Error! You must specify an id!";
		
		Response res = cv.deletePlay(id);
        
		if (res.getStatus() == Response.Status.NO_CONTENT.getStatusCode()) {
	        return outSymbol + "Play with id " + id + " has been deleted succesfully.";
		} else {
			return errorSymbol + "Error! "+res.readEntity(String.class);
		}	
	}		

	public Long getId() {
		return id;
	}

}
