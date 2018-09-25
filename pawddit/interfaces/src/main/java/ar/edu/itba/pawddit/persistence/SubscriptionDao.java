package ar.edu.itba.pawddit.persistence;

import ar.edu.itba.pawddit.model.Group;
import ar.edu.itba.pawddit.model.User;

public interface SubscriptionDao {

	public void suscribe(User user, Group group);
	public void unsuscribe(User user, Group group);
	public Boolean isUserSub(User user, Group group);
	
}
