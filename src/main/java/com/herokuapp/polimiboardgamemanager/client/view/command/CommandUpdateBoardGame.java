package com.herokuapp.polimiboardgamemanager.client.view.command;

import java.net.URI;

import javax.ws.rs.core.Response;

import com.beust.jcommander.Parameter;
import com.beust.jcommander.Parameters;
import com.herokuapp.polimiboardgamemanager.client.view.ClientView;

/**
 * The Class CommandUpdateBoardGame.
 */
@Parameters(commandNames=Command.UPDATE_BOARDGAME,
			commandDescription="Update a user with the optional provided information (you must login before) "
					+ "or create a new one if the id cannot be found.")
public class CommandUpdateBoardGame implements Command {
	
	/** The id. */
	@Parameter(names="-id", description="user id", order=0)
	private Long id;
	
	/** The name. */
	@Parameter(names={"-n", "-name"}, description="board game name", order=0)
	private String name;
	
	/** The designers. */
	@Parameter(names={"-d", "-designers"}, description="game designers", order=1)
	private String designers;
	
	/** The cover. */
	@Parameter(names={"-c", "-cover"}, description="cover art", order=2)
	private String cover;

	/* (non-Javadoc)
	 * @see com.herokuapp.polimiboardgamemanager.client.view.command.Command#getName()
	 */
	@Override
	public String getName() {
		return UPDATE_BOARDGAME;
	}

	/* (non-Javadoc)
	 * @see com.herokuapp.polimiboardgamemanager.client.view.command.Command#getParameters()
	 */
	@Override
	public Object[] getParameters() {
		return new Object[]{id, name, designers, cover};
	}
	
	/* (non-Javadoc)
	 * @see com.herokuapp.polimiboardgamemanager.client.view.command.Command#execute(com.herokuapp.polimiboardgamemanager.client.view.ClientView, java.lang.String, java.lang.String)
	 */
	@Override
	public String execute(ClientView cv, String outSymbol, String errorSymbol) throws Exception {	
		if (id == null)
			return errorSymbol + "Error! You must specify an id!";
		
		Response res = cv.updateBoardGame(id, name, designers, cover);
        
		if (res.getStatus() == Response.Status.NO_CONTENT.getStatusCode()) {
	        return outSymbol + "Board game with id " + id + " has been updated succesfully.";
		} else if (res.getStatus() == Response.Status.CREATED.getStatusCode()) {
	        URI newUserLocation = res.getLocation();
	        String path = newUserLocation.getPath();
	        return outSymbol + "New board game successfully created with id = " +
	        	   path.substring(path.lastIndexOf('/')+1);
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

	/**
	 * Gets the game name.
	 *
	 * @return the game name
	 */
	public String getGameName() {
		return name;
	}

	/**
	 * Gets the designers.
	 *
	 * @return the designers
	 */
	public String getDesigners() {
		return designers;
	}

	/**
	 * Gets the cover.
	 *
	 * @return the cover
	 */
	public String getCover() {
		return cover;
	}

}
