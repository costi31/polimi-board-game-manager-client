package com.herokuapp.polimiboardgamemanager.client;

import com.beust.jcommander.Parameter;
import com.beust.jcommander.Parameters;

@Parameters(commandDescription = "Record changes to the repository")
public class AbbaCommand {
	@Parameter(names="-a")
	public String a;
}
