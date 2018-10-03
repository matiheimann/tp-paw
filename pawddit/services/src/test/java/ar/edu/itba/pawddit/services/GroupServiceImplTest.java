package ar.edu.itba.pawddit.services;

import java.sql.Timestamp;
import java.util.Optional;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import ar.edu.itba.pawddit.model.Group;
import ar.edu.itba.pawddit.model.User;
import ar.edu.itba.pawddit.persistence.GroupDao;
import ar.edu.itba.pawddit.services.exceptions.GroupAlreadyExists;
import ar.edu.itba.pawddit.services.exceptions.NoPermissionsException;

@RunWith(MockitoJUnitRunner.class)
public class GroupServiceImplTest {
	
	private static final String TEST_USERNAME = "TestUser";
	private static final String TEST_PASSWORD = "TestPassword";
	private static final String TEST_EMAIL = "TestEmail";
	
	private static final String TEST_GROUP_NAME = "testGroup";
	private static final Timestamp TEST_GROUP_DATE = Timestamp.valueOf("2018-09-21 19:15:40.5");
	private static final String TEST_GROUP_DESCRIPTION = "testDescription";
	
	@InjectMocks
	private GroupServiceImpl groupService;
	
	@Mock
	private GroupDao groupDao;
	
	private User user;
	private Group group;
	
	@Before
	public void setup() {
		user = new User(TEST_USERNAME, TEST_PASSWORD, TEST_EMAIL, false, true, 1); 
		group = new Group(TEST_GROUP_NAME, TEST_GROUP_DATE, TEST_GROUP_DESCRIPTION, user, 1);
	}
		
	@Test(expected = GroupAlreadyExists.class)
	public void createExistingGroupTest() {
		Mockito.when(groupDao.findByName("testGroup")).thenReturn(Optional.of(group));
		groupService.create(TEST_GROUP_NAME, TEST_GROUP_DATE, TEST_GROUP_DESCRIPTION, user);
	}
	
	@Test(expected = NoPermissionsException.class)
	public void userNotOwnerOfGroupDeletesGroup() {
		final User userNotOwner = new User("username", "password", "email", false, true, 2);
		groupService.deleteGroup(userNotOwner, group);
	}
	
}
