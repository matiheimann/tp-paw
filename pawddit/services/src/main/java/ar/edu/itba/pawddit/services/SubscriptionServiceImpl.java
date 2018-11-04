package ar.edu.itba.pawddit.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ar.edu.itba.pawddit.model.Group;
import ar.edu.itba.pawddit.model.User;
import ar.edu.itba.pawddit.persistence.SubscriptionDao;

@Service
@Transactional
public class SubscriptionServiceImpl implements SubscriptionService {
	
	@Autowired
	SubscriptionDao sd;
	
	@Override
	public boolean suscribe(final User user, final Group group) {
		if (isUserSub(user, group))
			return false;
		
		sd.suscribe(user, group);
		return true;
	}

	@Override
	public boolean unsuscribe(final User user, final Group group) {
		if (!isUserSub(user, group))
			return false;
		
		sd.unsuscribe(user, group);
		return true;
	}

	@Override
	public boolean isUserSub(final User user, final Group group) {
		return sd.isUserSub(user, group);
	}


}
