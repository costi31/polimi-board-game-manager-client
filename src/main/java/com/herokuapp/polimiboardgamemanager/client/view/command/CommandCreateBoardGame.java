package com.herokuapp.polimiboardgamemanager.client.view.command;

import java.io.PrintStream;
import java.util.Scanner;

import com.beust.jcommander.Parameter;
import com.beust.jcommander.Parameters;
import com.herokuapp.polimiboardgamemanager.client.view.ClientView;

@Parameters(commandNames=Command.CREATE_BOARDGAME, commandDescription="Create a new board game with the desired information.")
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
		return "";
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
