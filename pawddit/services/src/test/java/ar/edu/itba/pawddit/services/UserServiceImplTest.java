package ar.edu.itba.pawddit.services;

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

@RunWith(MockitoJUnitRunner.class)
public class UserServiceImplTest {
	
	private static final String NEW_USERNAME = "newTestUser";
	private static final String NEW_PASSWORD = "newTestPassword";
	private static final String NEW_EMAIL = "newTestEmail";

	@InjectMocks
	private UserServiceImpl us;
	
	@Mock
	private UserDao userDao;
	
	@Mock
	private PasswordEncoder passwordEncoder;
	
	private void createNonExistingUserTestSetup() {
		User userToReturn = new User(NEW_USERNAME, NEW_PASSWORD, NEW_EMAIL, false, true);
		Mockito.when(userDao.create(NEW_USERNAME, NEW_PASSWORD, NEW_EMAIL, false, true)).thenReturn(userToReturn);
		Mockito.when(passwordEncoder.encode(NEW_PASSWORD)).thenReturn(NEW_PASSWORD);
	}
	
	@Test
	public void createNonExistingUserTest() {
		createNonExistingUserTestSetup();
		User userToCreate = us.create(NEW_USERNAME, NEW_PASSWORD, NEW_EMAIL, true);
		Assert.assertEquals(NEW_USERNAME, userToCreate.getUsername());
		Assert.assertEquals(NEW_PASSWORD, userToCreate.getPassword());
		Assert.assertEquals(NEW_EMAIL, userToCreate.getEmail());
	}
}