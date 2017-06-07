package com.herokuapp.polimiboardgamemanager.client.view.command;

import com.beust.jcommander.Parameter;
import com.beust.jcommander.Parameters;
import com.herokuapp.polimiboardgamemanager.client.view.ClientView;
import com.herokuapp.polimiboardgamemanager.model.Play;

@Parameters(commandNames=Command.SHOW_PLAY, commandDescription="Show the information of the play with the desired id.")
public class CommandShowPlay implements Command {
	
	@Parameter(names={"-uid", "-userCreatorId"}, description="Id of the user which the play is associated to.")
	private Long userId;
	
	@Parameter(names="-id", description="Id of play.")
	private Long id;

	@Override
	public String getName() {
		return SHOW_PLAY;
	}

	@Override
	public Object[] getParameters() {
		return new Object[]{id};
	}
	
	@Override
	public String execute(ClientView cv, String outSymbol, String errorSymbol) throws Exception {
		if (userId == null)
			userId = cv.getAuthUserId();	
		Play p = cv.getPlay(userId, id);
		if (p != null)
			return outSymbol + "Play " + id + " info: "+p.toString();
		else
			return errorSymbol + "Error! Play " + id + " doesn't exist!";		
	}

	public Long getUserId() {
		return userId;
	}

	public Long getId() {
		return id;
	}

}
