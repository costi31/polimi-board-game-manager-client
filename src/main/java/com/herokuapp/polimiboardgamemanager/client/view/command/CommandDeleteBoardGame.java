package com.herokuapp.polimiboardgamemanager.client.view.command;

import javax.ws.rs.core.Response;

import com.beust.jcommander.Parameter;
import com.beust.jcommander.Parameters;
import com.herokuapp.polimiboardgamemanager.client.view.ClientView;

/**
 * The Class CommandDeleteBoardGame.
 */
@Parameters(commandNames=Command.DELETE_BOARDGAME,
			commandDescription="Delete a board game (you must login as a super user before).")
public class CommandDeleteBoardGame implements Command {
	
	/** The id. */
	@Parameter(names="-id", description="board game id", order=0)
	private Long id;

	/* (non-Javadoc)
	 * @see com.herokuapp.polimiboardgamemanager.client.view.command.Command#getName()
	 */
	@Override
	public String getName() {
		return DELETE_BOARDGAME;
	}

	/* (non-Javadoc)
	 * @see com.herokuapp.polimiboardgamemanager.client.view.command.Command#getParameters()
	 */
	@Override
	public Object[] getParameters() {
		return new Object[]{id};
	}
	
	/* (non-Javadoc)
	 * @see com.herokuapp.polimiboardgamemanager.client.view.command.Command#execute(com.herokuapp.polimiboardgamemanager.client.view.ClientView, java.lang.String, java.lang.String)
	 */
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

	/**
	 * Gets the id.
	 *
	 * @return the id
	 */
	public Long getId() {
		return id;
	}

}
