package ar.edu.itba.pawddit.services;

import ar.edu.itba.pawddit.model.Group;
import ar.edu.itba.pawddit.model.User;

public interface SubscriptionService {
	
	public Number suscribe(User user, Group group);
	public int unsuscribe(User user, Group group);
	public int checkIfItsSuscribed(User user, Group group);
	
}
