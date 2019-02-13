package ar.edu.itba.pawddit.webapp.exceptions;

@SuppressWarnings("serial")
public class GroupNotFoundException extends NotFoundException {

	public GroupNotFoundException() {
		super("GroupNotFound");
	}
	
}
