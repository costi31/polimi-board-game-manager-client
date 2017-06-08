package com.herokuapp.polimiboardgamemanager.client.view.command;

import javax.ws.rs.core.Response;

import com.beust.jcommander.Parameter;
import com.beust.jcommander.Parameters;
import com.herokuapp.polimiboardgamemanager.client.view.ClientView;

/**
 * The Class CommandDeletePlay.
 */
@Parameters(commandNames=Command.DELETE_PLAY,
			commandDescription="Delete a play created by you (you must login before).")
public class CommandDeletePlay implements Command {
	
	/** The id. */
	@Parameter(names="-id", description="play id")
	private Long id;

	/* (non-Javadoc)
	 * @see com.herokuapp.polimiboardgamemanager.client.view.command.Command#getName()
	 */
	@Override
	public String getName() {
		return DELETE_PLAY;
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
		
		Response res = cv.deletePlay(id);
        
		if (res.getStatus() == Response.Status.NO_CONTENT.getStatusCode()) {
	        return outSymbol + "Play with id " + id + " has been deleted succesfully.";
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
