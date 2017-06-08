package com.herokuapp.polimiboardgamemanager.client.view.command;

import com.beust.jcommander.Parameter;
import com.beust.jcommander.Parameters;
import com.herokuapp.polimiboardgamemanager.client.view.ClientView;
import com.herokuapp.polimiboardgamemanager.model.User;

/**
 * The Class CommandShowUser.
 */
@Parameters(commandNames=Command.SHOW_USER,
			commandDescription="Show the information of your profile (you must login before)"
					+ " or the information of the user with the desired id.")
public class CommandShowUser implements Command {
	
	/** The id. */
	@Parameter(names="-id", description="Id of the user profile you want to see.")
	private Long id;

	/* (non-Javadoc)
	 * @see com.herokuapp.polimiboardgamemanager.client.view.command.Command#getName()
	 */
	@Override
	public String getName() {
		return SHOW_USER;
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
		if (id == null)
			id = cv.getAuthUserId();
		User u = cv.getUser(id);
		if (u != null)
			return outSymbol + "User " + id + " info: "+u.toString();
		else
			return errorSymbol + "Error! User " + id + " doesn't exist!";		
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
