package ar.edu.itba.pawddit.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ar.edu.itba.pawddit.persistence.UserDao;
import ar.edu.itba.pawddit.services.UserService;
import ar.edu.itba.pawddit.model.User;

@Service
public class UserServiceImpl implements UserService {
	
	@Autowired
	private UserDao userDao;

	@Override
	public User findById(int id) {
		// TODO Auto-generated method stub
		return null;
	}
	
}
