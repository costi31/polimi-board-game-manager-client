package com.herokuapp.polimiboardgamemanager.client.view.command;

import java.util.List;

import com.beust.jcommander.Parameter;
import com.beust.jcommander.Parameters;
import com.herokuapp.polimiboardgamemanager.client.view.ClientView;
import com.herokuapp.polimiboardgamemanager.model.Play;

@Parameters(commandNames=Command.SHOW_PLAYS, commandDescription="Show the plays of the desired user with optional filter and ordering criteria.")
public class CommandShowPlays implements Command {
	
	@Parameter(names={"-uid", "-userId"}, description="Id of the user which the play is associated to. "
			+ "(If you don't set it and you are authenticated then your plays are showed)")
	private Long userId;
	
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
		return SHOW_PLAYS;
	}

	@Override
	public Object[] getParameters() {
		return new Object[]{filters, orders};
	}
	
	@Override
	public String execute(ClientView cv, String outSymbol, String errorSymbol) throws Exception {
		if (userId == null)
			userId = cv.getAuthUserId();
		if (userId == null)
			return errorSymbol + "Error! You must specify an id!";
		
		List<Play> plays = cv.getAllPlays(userId, filters != null ? filters.toArray() : null,
									   	  orders != null ? orders.toArray() : null);
		
		StringBuilder out = new StringBuilder();
		
		for (Play p : plays)
			out.append(p + "\n");
		
		return out.toString();
	}

	public Long getUserId() {
		return userId;
	}

	public List<String> getFilters() {
		return filters;
	}

	public List<String> getOrders() {
		return orders;
	}

}
