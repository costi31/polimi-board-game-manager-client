package com.herokuapp.polimiboardgamemanager.client.view.command;

import java.net.URI;

import javax.ws.rs.core.Response;

import com.beust.jcommander.Parameter;
import com.beust.jcommander.Parameters;
import com.herokuapp.polimiboardgamemanager.client.view.ClientView;
import com.herokuapp.polimiboardgamemanager.model.BoardGame;

@Parameters(commandNames=Command.CREATE_BOARDGAME,
			commandDescription="Create a new board game with the desired information."
					+ "(You must be a super user authenitcated)")
public class CommandCreateBoardGame implements Command {
	
	@Parameter(names={"-n", "-name"}, description="board game name", order=0)
	private String name;
	
	@Parameter(names={"-d", "-designers"}, description="game designers", order=1)
	private String designers;
	
	@Parameter(names={"-c", "-cover"}, description="cover art", order=2)
	private String cover;	

	@Override
	public String getName() {
		return CREATE_BOARDGAME;
	}

	@Override
	public Object[] getParameters() {
		return new Object[]{name, designers, cover};
	}
	
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

	public String getGameName() {
		return name;
	}

	public String getDesigners() {
		return designers;
	}

	public String getCover() {
		return cover;
	}

}
