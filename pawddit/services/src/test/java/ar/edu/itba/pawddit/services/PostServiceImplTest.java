package ar.edu.itba.pawddit.services;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

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
	private static final String GROUP_DESCRIPTION = "testDescription";
	
	private static final LocalDateTime DEFAULT_DATE_TO_USE = LocalDateTime.parse("2018-09-21 19:15", DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
	
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
		user = new User(TEST_USERNAME, TEST_PASSWORD, TEST_EMAIL, false, true);
		user.setUserid(1);
		group = new Group(GROUP_NAME, DEFAULT_DATE_TO_USE, GROUP_DESCRIPTION, user);
	}
		
	@Test(expected = NoPermissionsException.class)
	public void userNotSubscribedToGroupCreatesPostTest() {
		Mockito.when(subscriptionDao.isUserSub(user, group)).thenReturn(false);
		postService.create("Title", "content", DEFAULT_DATE_TO_USE, group, user, "");
	}
	
	@Test(expected = NoPermissionsException.class)
	public void deletePostThatUserDoesNotOwnTest() {
		final Post post = new Post("title", "content", DEFAULT_DATE_TO_USE, group, user, "");
		final User userNotOwner = new User("other", "pswd", "mail", false, true);
		userNotOwner.setUserid(2);
		postService.delete(userNotOwner, group, post);
	}
	
	@Test(expected = NoPermissionsException.class)
	public void deletePostInGroupThatUserIsNotModeratorTest() {
		final Post post = new Post("title", "content", DEFAULT_DATE_TO_USE, group, user, "");
		final User userNotModerator = new User("other", "pswd", "mail", false, true);
		userNotModerator.setUserid(2);
		postService.delete(userNotModerator, group, post);
	}
}
