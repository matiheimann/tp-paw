package ar.edu.itba.pawddit.services;

import java.util.Optional;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.security.crypto.password.PasswordEncoder;

import ar.edu.itba.pawddit.model.User;
import ar.edu.itba.pawddit.persistence.UserDao;
import ar.edu.itba.pawddit.services.exceptions.UserRepeatedDataException;

@RunWith(MockitoJUnitRunner.class)
public class UserServiceImplTest {
	
	private static final String NEW_USERNAME = "newTestUser";
	private static final String NEW_PASSWORD = "newTestPassword";
	private static final String NEW_EMAIL = "newTestEmail";
	private static final Integer NEW_SCORE = 0;

	@InjectMocks
	private UserServiceImpl us = new UserServiceImpl();
	
	@Mock
	private UserDao userDao;
	
	@Mock
	private PasswordEncoder passwordEncoder;
	
	private void createNonExistingUserTestSetup() {
		Optional<User> empty = Optional.empty();
		User userToReturn = new User(NEW_USERNAME, NEW_PASSWORD, NEW_EMAIL, NEW_SCORE, true, NEW_SCORE);
		Mockito.when(userDao.findByEmail(NEW_EMAIL)).thenReturn(empty);
		Mockito.when(userDao.findByUsername(NEW_USERNAME)).thenReturn(empty);
		Mockito.when(userDao.create(NEW_USERNAME, NEW_PASSWORD, NEW_EMAIL, NEW_SCORE)).thenReturn(userToReturn);
		Mockito.when(passwordEncoder.encode(NEW_PASSWORD)).thenReturn(NEW_PASSWORD);
	}
	
	@Test
	public void createNonExistingUserTest() {
		createNonExistingUserTestSetup();
		User userToCreate = us.create(NEW_USERNAME, NEW_PASSWORD, NEW_EMAIL, 0);
		Assert.assertEquals(NEW_USERNAME, userToCreate.getUsername());
		Assert.assertEquals(NEW_PASSWORD, userToCreate.getPassword());
		Assert.assertEquals(NEW_EMAIL, userToCreate.getEmail());
	}
	
	private void createUserWithExistingUsernameTestSetup() {
		Optional<User> existingUser = Optional.of(new User(NEW_USERNAME, NEW_PASSWORD, NEW_EMAIL, NEW_SCORE, true, NEW_SCORE));
		Mockito.when(userDao.findByUsername(NEW_USERNAME)).thenReturn(existingUser);
	}
	
	@Test(expected = UserRepeatedDataException.class)
	public void createUserWithExistingUsernameTest() {
		createUserWithExistingUsernameTestSetup();
		us.create(NEW_USERNAME, NEW_PASSWORD, NEW_EMAIL, 0);
	}
	
	private void createUserWithExistingEmailTestSetup() {
		Optional<User> existingUser = Optional.of(new User(NEW_USERNAME, NEW_PASSWORD, NEW_EMAIL, NEW_SCORE, true, NEW_SCORE));
		Mockito.when(userDao.findByEmail(NEW_EMAIL)).thenReturn(existingUser);
	}
	
	@Test(expected = UserRepeatedDataException.class)
	public void createUserWithExistingEmailTest() {
		createUserWithExistingEmailTestSetup();
		us.create(NEW_USERNAME, NEW_PASSWORD, NEW_EMAIL, 0);
	}
	
	public void createUserWithExistingEmailAndUsernameTestSetup() {
		Optional<User> existingUser = Optional.of(new User(NEW_USERNAME, NEW_PASSWORD, NEW_EMAIL, NEW_SCORE, true, NEW_SCORE));
		Mockito.when(userDao.findByEmail(NEW_EMAIL)).thenReturn(existingUser);
		Mockito.when(userDao.findByUsername(NEW_USERNAME)).thenReturn(existingUser);
	}
	
	@Test(expected = UserRepeatedDataException.class)
	public void createUserWithExistingEmailAndUsernameTest() {
		createUserWithExistingEmailAndUsernameTestSetup();
		us.create(NEW_USERNAME, NEW_PASSWORD, NEW_EMAIL, 0);
	}
}
