package com.herokuapp.polimiboardgamemanager.client.view.command;

import com.beust.jcommander.Parameter;
import com.beust.jcommander.Parameters;
import com.herokuapp.polimiboardgamemanager.client.view.ClientView;
import com.herokuapp.polimiboardgamemanager.model.BoardGame;

/**
 * The Class CommandShowBoardGame.
 */
@Parameters(commandNames=Command.SHOW_BOARDGAME, commandDescription="Show the information of the board game with the desired id.")
public class CommandShowBoardGame implements Command {
	
	/** The id. */
	@Parameter(names="-id", description="board game id")
	private Long id;

	/* (non-Javadoc)
	 * @see com.herokuapp.polimiboardgamemanager.client.view.command.Command#getName()
	 */
	@Override
	public String getName() {
		return SHOW_BOARDGAME;
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
		BoardGame b = cv.getBoardGame(id);
		if (b != null)
			return outSymbol + "Board game " + id + " info: "+b.toString();
		else
			return errorSymbol + "Error! Board game " + id + " doesn't exist!";		
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
