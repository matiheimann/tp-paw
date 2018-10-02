package ar.edu.itba.pawddit.services;

import java.sql.Timestamp;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import ar.edu.itba.pawddit.model.Group;
import ar.edu.itba.pawddit.model.User;
import ar.edu.itba.pawddit.persistence.SubscriptionDao;

@RunWith(MockitoJUnitRunner.class)
public class SubscriptionServiceImplTest {
	
	private static final String NEW_USERNAME = "newTestUser";
	private static final String NEW_PASSWORD = "newTestPassword";
	private static final String NEW_EMAIL = "newTestEmail";
	private static final Integer NEW_SCORE = 0;
	
	private static final String GROUP_NAME = "testGroup";
	private static final Timestamp GROUP_DATE = Timestamp.valueOf("2018-09-21 19:15:40.5");
	private static final String GROUP_DESCRIPTION = "testDescription";
	
	private User testUser;
	private Group testGroup;

	@InjectMocks
	private SubscriptionServiceImpl subscriptionService;
	
	@Mock
	private SubscriptionDao subscriptionDao;
	
	@Before
	public void setup() {
		testUser = new User(NEW_USERNAME, NEW_PASSWORD, NEW_EMAIL, NEW_SCORE, true, NEW_SCORE);
		testGroup = new Group(GROUP_NAME, GROUP_DATE, GROUP_DESCRIPTION, testUser, 1);
	}
	
	@Test
	public void subscribeToUnsuscribedGroup() {
		Mockito.when(subscriptionDao.isUserSub(testUser, testGroup)).thenReturn(false);
		Assert.assertTrue(subscriptionService.suscribe(testUser, testGroup));
	}

	@Test
	public void subscribeToSubscribedGroupTest() {
		Mockito.when(subscriptionDao.isUserSub(testUser, testGroup)).thenReturn(true);
		Assert.assertFalse(subscriptionService.suscribe(testUser, testGroup));
	}
	
	@Test
	public void unsuscribeFromSubscribedGroupTest() {
		Mockito.when(subscriptionDao.isUserSub(testUser, testGroup)).thenReturn(true);
		Assert.assertTrue(subscriptionService.unsuscribe(testUser, testGroup));
	}
	
	@Test
	public void unsuscribeFromUnsubscribedGroupTest() {
		Mockito.when(subscriptionDao.isUserSub(testUser, testGroup)).thenReturn(false);
		Assert.assertFalse(subscriptionService.unsuscribe(testUser, testGroup));
	}
}
