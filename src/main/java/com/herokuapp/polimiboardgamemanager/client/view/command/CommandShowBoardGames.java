package com.herokuapp.polimiboardgamemanager.client.view.command;

import java.util.List;

import com.beust.jcommander.Parameter;
import com.beust.jcommander.Parameters;
import com.herokuapp.polimiboardgamemanager.client.view.ClientView;
import com.herokuapp.polimiboardgamemanager.model.BoardGame;

@Parameters(commandNames=Command.SHOW_BOARDGAMES, commandDescription="Show the board games with optional filter and ordering criteria.")
public class CommandShowBoardGames implements Command {
	
	@Parameter(names = {"--f", "--filter"},
			description = "The filter criteria expressed as <filter_name>@<filter_value> . It can be specified multiple times.",
			order=0)
	private List<String> filters;
	
	@Parameter(names = {"--o", "--order"},
			description = "The order criteria expressed as <order_by_field>@<order_mode> where order_mode can be asc or desc. It can be specified multiple times.",
			order=1)
	private List<String> orders;

	@Override
	public String getName() {
		return SHOW_BOARDGAMES;
	}

	@Override
	public Object[] getParameters() {
		return new Object[]{filters, orders};
	}
	
	@Override
	public String execute(ClientView cv, String outSymbol, String errorSymbol) throws Exception {
		List<BoardGame> games = cv.getAllBoardGames(filters != null ? filters.toArray() : null,
							   	  					orders != null ? orders.toArray() : null);
		
		StringBuilder out = new StringBuilder();
		
		for (BoardGame b : games)
			out.append(b + "\n");
		
		return out.toString();
	}

	public List<String> getFilters() {
		return filters;
	}

	public List<String> getOrders() {
		return orders;
	}

}