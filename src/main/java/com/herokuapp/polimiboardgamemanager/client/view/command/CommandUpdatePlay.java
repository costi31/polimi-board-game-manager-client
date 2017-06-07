package com.herokuapp.polimiboardgamemanager.client.view.command;

import java.net.URI;
import java.sql.Time;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import javax.ws.rs.core.Response;

import com.beust.jcommander.Parameter;
import com.beust.jcommander.Parameters;
import com.herokuapp.polimiboardgamemanager.client.view.ClientView;

@Parameters(commandNames=Command.UPDATE_PLAY,
			commandDescription="Update a play of yours with the optional provided information (you must login before) "
					+ "or create a new one if the id cannot be found.")
public class CommandUpdatePlay implements Command {
	
	@Parameter(names="-id", description="play id", order=0)
	private Long id;
	
	@Parameter(names={"-bgid", "-boardGameId"}, description="Id of the board game which the play is associated to.", order=1)
	private Long boardGameId;
	
	@Parameter(names={"-d", "-date"}, description="date in format: dd/MM/yyyy - hh:mm am/pm", order=2)
	private String dateString;
	
	@Parameter(names={"-p", "-playersInvolved"}, description="number of players involved", order=3)
	private Integer playersInvolved;
	
	@Parameter(names={"-c", "-completed"}, description="sets if the play is completed", order=4)
	private boolean completed;
	
	@Parameter(names={"-t", "-timeToComplete"}, description="time to complete the play, in format: hh:mm:ss", order=5)
	private String timeToCompleteString;
	
	@Parameter(names={"-uwid", "-userWinnerId"}, description="Id of the user that won the play.", order=6)
	private Long userWinnerId;	

	@Override
	public String getName() {
		return UPDATE_PLAY;
	}

	@Override
	public Object[] getParameters() {
		return new Object[]{id, boardGameId, dateString, playersInvolved, completed, timeToCompleteString, userWinnerId};
	}
	
	@Override
	public String execute(ClientView cv, String outSymbol, String errorSymbol) throws Exception {	
		if (id == null)
			return errorSymbol + "Error! You must specify an id!";
		
    	SimpleDateFormat dateFormatter = new SimpleDateFormat("dd/MM/yyyy - hh:mm a");
    	Calendar date = null;
    	if (dateString != null) {
	    	date = Calendar.getInstance();
	    	date.setTime(dateFormatter.parse(dateString));
    	}
		
		Response res = cv.updatePlay(id, boardGameId, date, playersInvolved, completed,
									 (timeToCompleteString != null ? Time.valueOf(timeToCompleteString) : null), userWinnerId);
        
		if (res.getStatus() == Response.Status.NO_CONTENT.getStatusCode()) {
	        return outSymbol + "Play with id " + id + " has been updated succesfully.";
		} else if (res.getStatus() == Response.Status.CREATED.getStatusCode()) {
	        URI newUserLocation = res.getLocation();
	        String path = newUserLocation.getPath();
	        return outSymbol + "New play successfully created with id = " +
	        	   path.substring(path.lastIndexOf('/')+1);
		} else {
			return errorSymbol + "Error! "+res.readEntity(String.class);
		}		
	}
	
	public Long getId() {
		return id;
	}

	public Long getBoardGameId() {
		return boardGameId;
	}

	public String getDateString() {
		return dateString;
	}

	public Integer getPlayersInvolved() {
		return playersInvolved;
	}

	public boolean isCompleted() {
		return completed;
	}

	public String getTimeToCompleteString() {
		return timeToCompleteString;
	}

	public Long getUserWinnerId() {
		return userWinnerId;
	}

}
