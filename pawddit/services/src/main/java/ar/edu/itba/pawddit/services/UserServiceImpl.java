package ar.edu.itba.pawddit.services;

import java.util.Optional;

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
	public Optional<User> findById(final long id) {
		return userDao.findById(id);
	}

	@Override
	public User create(final String username, final String password, final String email, final int score) {
		return userDao.create(username, password, email, score);
	}
	
}
