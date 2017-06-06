package com.herokuapp.polimiboardgamemanager.client.view.command;

import com.beust.jcommander.Parameter;
import com.beust.jcommander.Parameters;
import com.herokuapp.polimiboardgamemanager.client.view.ClientView;
import com.herokuapp.polimiboardgamemanager.model.BoardGame;

@Parameters(commandNames=Command.SHOW_BOARDGAME, commandDescription="Show the information of the board game with the desired id.")
public class CommandShowBoardGame implements Command {
	
	@Parameter(names="-id", description="board game id")
	private Long id;

	@Override
	public String getName() {
		return SHOW_BOARDGAME;
	}

	@Override
	public Object[] getParameters() {
		return new Object[]{id};
	}
	
	@Override
	public String execute(ClientView cv, String outSymbol, String errorSymbol) throws Exception {
		BoardGame b = cv.getBoardGame(id);
		if (b != null)
			return outSymbol + "Board game " + id + " info: "+b.toString();
		else
			return errorSymbol + "Error! Board game " + id + " doesn't exist!";		
	}

	public Long getId() {
		return id;
	}

}
