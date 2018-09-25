package ar.edu.itba.pawddit.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import ar.edu.itba.pawddit.persistence.UserDao;
import ar.edu.itba.pawddit.services.UserService;
import ar.edu.itba.pawddit.services.exceptions.EmailAlreadyRegistered;
import ar.edu.itba.pawddit.services.exceptions.UsernameAlreadyRegistered;
import ar.edu.itba.pawddit.model.User;

@Service
public class UserServiceImpl implements UserService {
	
	@Autowired
	private UserDao userDao;
	
	@Autowired
	private PasswordEncoder passwordEncoder;

	@Override
	public Optional<User> findById(final long id) {
		return userDao.findById(id);
	}

	@Override
	public User create(final String username, final String password, final String email, final int score) {
		if (findByUsername(username).isPresent())
			throw new UsernameAlreadyRegistered();
		if (findByEmail(email).isPresent())
			throw new EmailAlreadyRegistered();
		
		return userDao.create(username, passwordEncoder.encode(password), email, score);
	}

	@Override
	public Optional<User> findByUsername(final String username) {
		return userDao.findByUsername(username);
	}

	@Override
	public Optional<User> findByEmail(final String email) {
		return userDao.findByEmail(email);
	}
	
}
