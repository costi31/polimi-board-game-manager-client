package com.herokuapp.polimiboardgamemanager.client.view.command;

import java.util.List;

import com.beust.jcommander.Parameter;
import com.beust.jcommander.Parameters;

@Parameters(commandDescription="Show the users with optional filter and ordering criteria.")
public class CommandShowUsers implements Command {
	
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
		return SHOW_USERS;
	}

	@Override
	public Object[] getParameters() {
		return new Object[]{filters, orders};
	}

	public List<String> getFilters() {
		return filters;
	}

	public List<String> getOrders() {
		return orders;
	}

}
