package ar.edu.itba.pawddit.persistence;

import ar.edu.itba.pawddit.model.Group;
import ar.edu.itba.pawddit.model.User;

public interface SubscriptionDao {

	public int suscribe(User user, Group group);
	public int unsuscribe(User user, Group group);
	public int checkIfItsSuscribed(User user, Group group);
	
}
