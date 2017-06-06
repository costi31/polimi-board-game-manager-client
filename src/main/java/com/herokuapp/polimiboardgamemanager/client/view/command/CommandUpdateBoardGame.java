package com.herokuapp.polimiboardgamemanager.client.view.command;

import java.net.URI;

import javax.ws.rs.core.Response;

import com.beust.jcommander.Parameter;
import com.beust.jcommander.Parameters;
import com.herokuapp.polimiboardgamemanager.client.view.ClientView;

@Parameters(commandNames=Command.UPDATE_BOARDGAME,
			commandDescription="Update a user with the optional provided information (you must login before) "
					+ "or create a new one if the id cannot be found.")
public class CommandUpdateBoardGame implements Command {
	
	@Parameter(names="-id", description="user id", order=0)
	private Long id;
	
	@Parameter(names={"-n", "-name"}, description="board game name", order=0)
	private String name;
	
	@Parameter(names={"-d", "-designers"}, description="game designers", order=1)
	private String designers;
	
	@Parameter(names={"-c", "-cover"}, description="cover art", order=2)
	private String cover;

	@Override
	public String getName() {
		return UPDATE_BOARDGAME;
	}

	@Override
	public Object[] getParameters() {
		return new Object[]{id, name, designers, cover};
	}
	
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
			System.out.println(res);
			return errorSymbol + "Error! "+res.readEntity(String.class);
		}		
	}

	public Long getId() {
		return id;
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
