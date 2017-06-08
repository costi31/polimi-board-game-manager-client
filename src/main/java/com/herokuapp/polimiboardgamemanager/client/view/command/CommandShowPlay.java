package com.herokuapp.polimiboardgamemanager.client.view.command;

import com.beust.jcommander.Parameter;
import com.beust.jcommander.Parameters;
import com.herokuapp.polimiboardgamemanager.client.view.ClientView;
import com.herokuapp.polimiboardgamemanager.model.Play;

/**
 * The Class CommandShowPlay.
 */
@Parameters(commandNames=Command.SHOW_PLAY, commandDescription="Show the information of the play with the desired id.")
public class CommandShowPlay implements Command {
	
	/** The user id. */
	@Parameter(names={"-uid", "-userCreatorId"}, description="Id of the user which the play is associated to.")
	private Long userId;
	
	/** The id. */
	@Parameter(names="-id", description="Id of play.")
	private Long id;

	/* (non-Javadoc)
	 * @see com.herokuapp.polimiboardgamemanager.client.view.command.Command#getName()
	 */
	@Override
	public String getName() {
		return SHOW_PLAY;
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
		if (userId == null)
			userId = cv.getAuthUserId();	
		Play p = cv.getPlay(userId, id);
		if (p != null)
			return outSymbol + "Play " + id + " info: "+p.toString();
		else
			return errorSymbol + "Error! Play " + id + " doesn't exist!";		
	}

	/**
	 * Gets the user id.
	 *
	 * @return the user id
	 */
	public Long getUserId() {
		return userId;
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
