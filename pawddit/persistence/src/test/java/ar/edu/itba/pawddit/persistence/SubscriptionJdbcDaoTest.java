package ar.edu.itba.pawddit.persistence;

import java.sql.Timestamp;
import java.util.Optional;

import javax.sql.DataSource;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.jdbc.JdbcTestUtils;

import ar.edu.itba.pawddit.model.Group;
import ar.edu.itba.pawddit.model.User;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = TestConfig.class)
@Sql("classpath:subscriptionJdbcDaoTestScript.sql")
public class SubscriptionJdbcDaoTest {
	private static final String SUBSCRIBED_USER_USERNAME = "subscribedUser";
	private static final String UNSUBSCRIBED_USER_USERNAME = "unsubscribedUser";
	private static final String TEST_GROUP_NAME = "testGroup";

	@Autowired
	private DataSource ds;
	
	@Autowired
	private SubscriptionJdbcDao subscriptionDao;
	
	@Autowired
	private UserJdbcDao userDao; //User dao already tested successfully
	
	@Autowired
	private GroupJdbcDao groupDao; //Group dao already tested successfully
	
	private JdbcTemplate jdbcTemplate;
	private Optional<Group> testGroup;
	private Optional<User> subscribedUser;
	private Optional<User> unsubscribedUser;
	
	@Before
	public void setUp() {
		jdbcTemplate = new JdbcTemplate(ds);
		testGroup = groupDao.findByName(TEST_GROUP_NAME);
	}
	
	@Test
	public void isUserSubTest() {
		subscribedUser = userDao.findByUsername(SUBSCRIBED_USER_USERNAME);
		Assert.assertTrue(subscriptionDao.isUserSub(subscribedUser.get(), testGroup.get()));
	}
	
	@Test
	public void userNotSubscribedTest() {
		unsubscribedUser = userDao.findByUsername(UNSUBSCRIBED_USER_USERNAME);
		Assert.assertFalse(subscriptionDao.isUserSub(unsubscribedUser.get(), testGroup.get()));
	}
	
	@Test
	public void unsubscribeTest() {
		subscribedUser = userDao.findByUsername(SUBSCRIBED_USER_USERNAME);
		subscriptionDao.unsuscribe(subscribedUser.get(), testGroup.get());
		Assert.assertFalse(subscriptionDao.isUserSub(subscribedUser.get(), testGroup.get()));
	}
	
	@Test
	public void subscribeTest() {
		unsubscribedUser = userDao.findByUsername(UNSUBSCRIBED_USER_USERNAME);
		subscriptionDao.suscribe(unsubscribedUser.get(), testGroup.get());
		Assert.assertTrue(subscriptionDao.isUserSub(unsubscribedUser.get(), testGroup.get()));
	}
	
	@After
	public void tearDown() {
		JdbcTestUtils.deleteFromTables(jdbcTemplate, "users");
	}
}
