package ar.edu.itba.pawddit.services;

import ar.edu.itba.pawddit.model.Group;
import ar.edu.itba.pawddit.model.User;

public interface SubscriptionService {
	
	public boolean suscribe(User user, Group group);
	public boolean unsuscribe(User user, Group group);
	public boolean isUserSub(User user, Group group);
}
