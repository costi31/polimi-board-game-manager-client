package com.herokuapp.polimiboardgamemanager.client.view.command;

import javax.ws.rs.core.Response;

import com.beust.jcommander.Parameter;
import com.beust.jcommander.Parameters;
import com.herokuapp.polimiboardgamemanager.client.view.ClientView;

@Parameters(commandNames=Command.DELETE_BOARDGAME,
			commandDescription="Delete a board game (you must login as a super user before).")
public class CommandDeleteBoardGame implements Command {
	
	@Parameter(names="-id", description="board game id", order=0)
	private Long id;

	@Override
	public String getName() {
		return DELETE_BOARDGAME;
	}

	@Override
	public Object[] getParameters() {
		return new Object[]{id};
	}
	
	@Override
	public String execute(ClientView cv, String outSymbol, String errorSymbol) throws Exception {		
		if (id == null)
			return errorSymbol + "Error! You must specify an id!";
		
		Response res = cv.deleteBoardGame(id);
        
		if (res.getStatus() == Response.Status.NO_CONTENT.getStatusCode()) {
	        return outSymbol + "Board game with id " + id + " has been deleted succesfully.";
		} else {
			return errorSymbol + "Error! "+res.readEntity(String.class);
		}	
	}		

	public Long getId() {
		return id;
	}

}
