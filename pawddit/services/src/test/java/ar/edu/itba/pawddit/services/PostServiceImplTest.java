package ar.edu.itba.pawddit.services;

import java.sql.Timestamp;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import ar.edu.itba.pawddit.model.Group;
import ar.edu.itba.pawddit.model.Post;
import ar.edu.itba.pawddit.model.User;
import ar.edu.itba.pawddit.persistence.SubscriptionDao;
import ar.edu.itba.pawddit.services.exceptions.NoPermissionsException;

@RunWith(MockitoJUnitRunner.class)
public class PostServiceImplTest {
	
	private static final String GROUP_NAME = "createGroupTest";
	private static final Timestamp GROUP_CREATION_DATE = Timestamp.valueOf("2018-09-21 19:15:40.5");
	private static final String GROUP_DESCRIPTION = "testDescription";
	
	private static final String TEST_USERNAME = "Username";
	private static final String TEST_PASSWORD = "Password";
	private static final String TEST_EMAIL = "Email";

	@InjectMocks
	private PostServiceImpl postService;
	
	@Mock
	private SubscriptionDao subscriptionDao;
	
	private User user;
	private Group group;
	
	@Before
	public void setup() {
		user = new User(TEST_USERNAME, TEST_PASSWORD, TEST_EMAIL, false, true, 1);
		group = new Group(GROUP_NAME, GROUP_CREATION_DATE, GROUP_DESCRIPTION, user, 1);
	}
	
	@Test(expected = NoPermissionsException.class)
	public void userNotSubscribedToGroupCreatesPostTest() {
		Mockito.when(subscriptionDao.isUserSub(user, group)).thenReturn(false);
		postService.create("Title", "content", Timestamp.valueOf("2018-09-21 19:15:40.5"), group, user, "");
	}
	
	@Test(expected = NoPermissionsException.class)
	public void deletePostThatUserDoesNotOwnTest() {
		final Post post = new Post("title", "content", Timestamp.valueOf("2018-09-21 19:15:40.5"), 
				group, user, 1, 0, 0, "");
		final User userNotOwner = new User("other", "pswd", "mail", false, true, 2);
		
		postService.deletePost(userNotOwner, group, post);
	}
	
	@Test(expected = NoPermissionsException.class)
	public void deletePostInGroupThatUserIsNotModeratorTest() {
		final Post post = new Post("title", "content", Timestamp.valueOf("2018-09-21 19:15:40.5"), 
				group, user, 1, 0, 0, "");
		final User userNotModerator = new User("other", "pswd", "mail", false, true, 2);
		
		postService.deletePost(userNotModerator, group, post);
	}
}
