package com.herokuapp.polimiboardgamemanager.client.view.command;

import java.net.URI;
import java.sql.Time;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import javax.ws.rs.core.Response;

import com.beust.jcommander.Parameter;
import com.beust.jcommander.Parameters;
import com.herokuapp.polimiboardgamemanager.client.view.ClientView;
import com.herokuapp.polimiboardgamemanager.model.Play;

@Parameters(commandNames=Command.CREATE_PLAY,
			commandDescription="Create a new play with the desired information."
					+ "(You must login before)")
public class CommandCreatePlay implements Command {
	
//	Play(Long id, User userCreator, BoardGame boardGame, Calendar date, int playersInvolved, boolean completed, Time timeToComplete, User userWinner) {
	
	@Parameter(names={"-uid", "-userCreatorId"}, description="Id of the user which the play is associated to.", order=0)
	private Long userId;
	
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
		return CREATE_PLAY;
	}

	@Override
	public Object[] getParameters() {
		return new Object[]{userId, boardGameId, dateString, playersInvolved, completed, timeToCompleteString, userWinnerId};
	}
	
	@Override
	public String execute(ClientView cv, String outSymbol, String errorSymbol) throws Exception {	
    	SimpleDateFormat dateFormatter = new SimpleDateFormat("dd/MM/yyyy - hh:mm a");
    	
    	Calendar date = Calendar.getInstance();
    	date.setTime(dateFormatter.parse(dateString));
		
		Response res = cv.createPlay(new Play (cv.getUser(userId), cv.getBoardGame(boardGameId), date, playersInvolved, 
											   completed, Time.valueOf(timeToCompleteString), cv.getUser(userWinnerId)));
        
		if (res.getStatus() == Response.Status.CREATED.getStatusCode()) {
	        URI newUserLocation = res.getLocation();
	        String path = newUserLocation.getPath();
	        return outSymbol + "New board game successfully created with id = " +
        		   path.substring(path.lastIndexOf('/')+1);
		} else {
			return errorSymbol + "Error! " + res.readEntity(String.class);
		}		
	}

	public Long getUserId() {
		return userId;
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
