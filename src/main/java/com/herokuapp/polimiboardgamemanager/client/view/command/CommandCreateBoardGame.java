package com.herokuapp.polimiboardgamemanager.client.view.command;

import java.net.URI;

import javax.ws.rs.core.Response;

import com.beust.jcommander.Parameter;
import com.beust.jcommander.Parameters;
import com.herokuapp.polimiboardgamemanager.client.view.ClientView;
import com.herokuapp.polimiboardgamemanager.model.BoardGame;

/**
 * The Class CommandCreateBoardGame.
 */
@Parameters(commandNames=Command.CREATE_BOARDGAME,
			commandDescription="Create a new board game with the desired information."
					+ "(You must be a super user authenitcated)")
public class CommandCreateBoardGame implements Command {
	
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
		return CREATE_BOARDGAME;
	}

	/* (non-Javadoc)
	 * @see com.herokuapp.polimiboardgamemanager.client.view.command.Command#getParameters()
	 */
	@Override
	public Object[] getParameters() {
		return new Object[]{name, designers, cover};
	}
	
	/* (non-Javadoc)
	 * @see com.herokuapp.polimiboardgamemanager.client.view.command.Command#execute(com.herokuapp.polimiboardgamemanager.client.view.ClientView, java.lang.String, java.lang.String)
	 */
	@Override
	public String execute(ClientView cv, String outSymbol, String errorSymbol) throws Exception {	
		Response res = cv.createBoardGame(new BoardGame (name, designers, cover));
        
		if (res.getStatus() == Response.Status.CREATED.getStatusCode()) {
	        URI newUserLocation = res.getLocation();
	        String path = newUserLocation.getPath();
	        return outSymbol + "New board game successfully created with id = " +
        		   path.substring(path.lastIndexOf('/')+1);
		} else {
			return errorSymbol + "Error! " + res.readEntity(String.class);
		}		
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
