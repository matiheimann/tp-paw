package ar.edu.itba.pawddit.services;

import java.util.Optional;

import org.junit.Assert;
import org.junit.Before;
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

	@InjectMocks
	private UserServiceImpl userService; 
	
	@Mock
	private UserDao userDao;
	
	@Mock
	private PasswordEncoder passwordEncoder;
	
	private void createNonExistingUserTestSetup() {
<<<<<<< HEAD
		final Optional<User> empty = Optional.empty();
		final User userToReturn = new User(NEW_USERNAME, NEW_PASSWORD, NEW_EMAIL, NEW_SCORE, true, NEW_SCORE);
=======
		Optional<User> empty = Optional.empty();
		User userToReturn = new User(NEW_USERNAME, NEW_PASSWORD, NEW_EMAIL, false, true, 0);
>>>>>>> 7f7bb65ba6871eaffabdebd7766da71b544d35d6
		Mockito.when(userDao.findByEmail(NEW_EMAIL)).thenReturn(empty);
		Mockito.when(userDao.findByUsername(NEW_USERNAME)).thenReturn(empty);
		Mockito.when(userDao.create(NEW_USERNAME, NEW_PASSWORD, NEW_EMAIL)).thenReturn(userToReturn);
		Mockito.when(passwordEncoder.encode(NEW_PASSWORD)).thenReturn(NEW_PASSWORD);
	}
	
	@Test
	public void createNonExistingUserTest() {
		createNonExistingUserTestSetup();
<<<<<<< HEAD
		final User userToCreate = userService.create(NEW_USERNAME, NEW_PASSWORD, NEW_EMAIL, 0);
=======
		User userToCreate = us.create(NEW_USERNAME, NEW_PASSWORD, NEW_EMAIL);
>>>>>>> 7f7bb65ba6871eaffabdebd7766da71b544d35d6
		Assert.assertEquals(NEW_USERNAME, userToCreate.getUsername());
		Assert.assertEquals(NEW_PASSWORD, userToCreate.getPassword());
		Assert.assertEquals(NEW_EMAIL, userToCreate.getEmail());
	}
	
	private void createUserWithExistingUsernameTestSetup() {
<<<<<<< HEAD
		final Optional<User> empty = Optional.empty();
		final Optional<User> existingUser = Optional.of(new User(NEW_USERNAME, NEW_PASSWORD, NEW_EMAIL, NEW_SCORE, true, NEW_SCORE));
=======
		Optional<User> existingUser = Optional.of(new User(NEW_USERNAME, NEW_PASSWORD, NEW_EMAIL, false, true, 0));
>>>>>>> 7f7bb65ba6871eaffabdebd7766da71b544d35d6
		Mockito.when(userDao.findByUsername(NEW_USERNAME)).thenReturn(existingUser);
		Mockito.when(userDao.findByEmail(NEW_EMAIL)).thenReturn(empty);
	}
	
	@Test(expected = UserRepeatedDataException.class)
	public void createUserWithExistingUsernameTest() {
		createUserWithExistingUsernameTestSetup();
<<<<<<< HEAD
		userService.create(NEW_USERNAME, NEW_PASSWORD, NEW_EMAIL, 0);
	}
	
	private void createUserWithExistingEmailTestSetup() {
		final Optional<User> empty = Optional.empty();
		final Optional<User> existingUser = Optional.of(new User(NEW_USERNAME, NEW_PASSWORD, NEW_EMAIL, NEW_SCORE, true, NEW_SCORE));
=======
		us.create(NEW_USERNAME, NEW_PASSWORD, NEW_EMAIL);
	}
	
	private void createUserWithExistingEmailTestSetup() {
		Optional<User> existingUser = Optional.of(new User(NEW_USERNAME, NEW_PASSWORD, NEW_EMAIL, false, true, 0));
>>>>>>> 7f7bb65ba6871eaffabdebd7766da71b544d35d6
		Mockito.when(userDao.findByEmail(NEW_EMAIL)).thenReturn(existingUser);
		Mockito.when(userDao.findByUsername(NEW_USERNAME)).thenReturn(empty);
	}
	
	@Test(expected = UserRepeatedDataException.class)
	public void createUserWithExistingEmailTest() {
		createUserWithExistingEmailTestSetup();
<<<<<<< HEAD
		userService.create(NEW_USERNAME, NEW_PASSWORD, NEW_EMAIL, 0);
	}
	
	public void createUserWithExistingEmailAndUsernameTestSetup() {
		final Optional<User> existingUser = Optional.of(new User(NEW_USERNAME, NEW_PASSWORD, NEW_EMAIL, NEW_SCORE, true, NEW_SCORE));
=======
		us.create(NEW_USERNAME, NEW_PASSWORD, NEW_EMAIL);
	}
	
	public void createUserWithExistingEmailAndUsernameTestSetup() {
		Optional<User> existingUser = Optional.of(new User(NEW_USERNAME, NEW_PASSWORD, NEW_EMAIL, false, true, 0));
>>>>>>> 7f7bb65ba6871eaffabdebd7766da71b544d35d6
		Mockito.when(userDao.findByEmail(NEW_EMAIL)).thenReturn(existingUser);
		Mockito.when(userDao.findByUsername(NEW_USERNAME)).thenReturn(existingUser);
	}
	
	@Test(expected = UserRepeatedDataException.class)
	public void createUserWithExistingEmailAndUsernameTest() {
		createUserWithExistingEmailAndUsernameTestSetup();
<<<<<<< HEAD
		userService.create(NEW_USERNAME, NEW_PASSWORD, NEW_EMAIL, 0);
=======
		us.create(NEW_USERNAME, NEW_PASSWORD, NEW_EMAIL);
>>>>>>> 7f7bb65ba6871eaffabdebd7766da71b544d35d6
	}
}
