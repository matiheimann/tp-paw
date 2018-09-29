package ar.edu.itba.pawddit.services;

import ar.edu.itba.pawddit.model.Group;
import ar.edu.itba.pawddit.model.User;

public interface SubscriptionService {
	
	public Boolean suscribe(User user, Group group);
	public Boolean unsuscribe(User user, Group group);
	public Boolean isUserSub(User user, Group group);
	public int userSuscribed(User user);
}
