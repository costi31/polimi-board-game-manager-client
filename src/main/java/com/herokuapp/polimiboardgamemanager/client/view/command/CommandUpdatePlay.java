package com.herokuapp.polimiboardgamemanager.client.view.command;

import java.net.URI;
import java.sql.Time;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import javax.ws.rs.core.Response;

import com.beust.jcommander.Parameter;
import com.beust.jcommander.Parameters;
import com.herokuapp.polimiboardgamemanager.client.view.ClientView;

/**
 * The Class CommandUpdatePlay.
 */
@Parameters(commandNames=Command.UPDATE_PLAY,
			commandDescription="Update a play of yours with the optional provided information (you must login before) "
					+ "or create a new one if the id cannot be found.")
public class CommandUpdatePlay implements Command {
	
	/** The id. */
	@Parameter(names="-id", description="play id", order=0)
	private Long id;
	
	/** The board game id. */
	@Parameter(names={"-bgid", "-boardGameId"}, description="Id of the board game which the play is associated to.", order=1)
	private Long boardGameId;
	
	/** The date string. */
	@Parameter(names={"-d", "-date"}, description="date in format: dd/MM/yyyy - hh:mm am/pm", order=2)
	private String dateString;
	
	/** The players involved. */
	@Parameter(names={"-p", "-playersInvolved"}, description="number of players involved", order=3)
	private Integer playersInvolved;
	
	/** The completed. */
	@Parameter(names={"-c", "-completed"}, description="sets if the play is completed", order=4)
	private boolean completed;
	
	/** The time to complete string. */
	@Parameter(names={"-t", "-timeToComplete"}, description="time to complete the play, in format: hh:mm:ss", order=5)
	private String timeToCompleteString;
	
	/** The user winner id. */
	@Parameter(names={"-uwid", "-userWinnerId"}, description="Id of the user that won the play.", order=6)
	private Long userWinnerId;	

	/* (non-Javadoc)
	 * @see com.herokuapp.polimiboardgamemanager.client.view.command.Command#getName()
	 */
	@Override
	public String getName() {
		return UPDATE_PLAY;
	}

	/* (non-Javadoc)
	 * @see com.herokuapp.polimiboardgamemanager.client.view.command.Command#getParameters()
	 */
	@Override
	public Object[] getParameters() {
		return new Object[]{id, boardGameId, dateString, playersInvolved, completed, timeToCompleteString, userWinnerId};
	}
	
	/* (non-Javadoc)
	 * @see com.herokuapp.polimiboardgamemanager.client.view.command.Command#execute(com.herokuapp.polimiboardgamemanager.client.view.ClientView, java.lang.String, java.lang.String)
	 */
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
	
	/**
	 * Gets the id.
	 *
	 * @return the id
	 */
	public Long getId() {
		return id;
	}

	/**
	 * Gets the board game id.
	 *
	 * @return the board game id
	 */
	public Long getBoardGameId() {
		return boardGameId;
	}

	/**
	 * Gets the date string.
	 *
	 * @return the date string
	 */
	public String getDateString() {
		return dateString;
	}

	/**
	 * Gets the players involved.
	 *
	 * @return the players involved
	 */
	public Integer getPlayersInvolved() {
		return playersInvolved;
	}

	/**
	 * Checks if is completed.
	 *
	 * @return true, if is completed
	 */
	public boolean isCompleted() {
		return completed;
	}

	/**
	 * Gets the time to complete string.
	 *
	 * @return the time to complete string
	 */
	public String getTimeToCompleteString() {
		return timeToCompleteString;
	}

	/**
	 * Gets the user winner id.
	 *
	 * @return the user winner id
	 */
	public Long getUserWinnerId() {
		return userWinnerId;
	}

}
