package ar.edu.itba.pawddit.persistence;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Optional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
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
import org.springframework.transaction.annotation.Transactional;

import ar.edu.itba.pawddit.model.Group;
import ar.edu.itba.pawddit.model.User;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = TestConfig.class)
@Sql("classpath:subscriptionHibernateDaoTestScript.sql")
@Transactional
public class SubscriptionHibernateDaoTest {
	
	private static final LocalDateTime GROUP_CREATION_DATE = LocalDateTime.parse("2018-09-21 19:15", DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));

	@PersistenceContext
	private EntityManager em;
	
	@Autowired
	private DataSource ds;
	
	@Autowired
	private SubscriptionHibernateDao subscriptionDao;
	
	@Autowired
	private UserHibernateDao userDao; 
	
	@Autowired
	private GroupHibernateDao groupDao; 
	
	private JdbcTemplate jdbcTemplate;
	
	private Group groupDummy;
	
	private User userDummy;
		
	@Before
	public void setUp() {
		jdbcTemplate = new JdbcTemplate(ds);
		
		userDummy = userDao.create("dummyUser", "dummyPassword", "dummyEmail", false, true);
		groupDummy = groupDao.create("dummyName", GROUP_CREATION_DATE, "dummyDescription", userDummy);
	}
	
	@Test
	public void isUserSubscribedTest() {
		subscriptionDao.suscribe(userDummy, groupDummy);
		
		Assert.assertTrue(subscriptionDao.isUserSub(userDummy, groupDummy));
	}
	
	@Test
	public void userNotSubscribedTest() {
		Assert.assertFalse(subscriptionDao.isUserSub(userDummy, groupDummy));
	}
	
	@Test
	public void unsubscribeTest() {
		subscriptionDao.suscribe(userDummy, groupDummy);
		
		subscriptionDao.unsuscribe(userDummy, groupDummy);
		
		Assert.assertFalse(subscriptionDao.isUserSub(userDummy, groupDummy));
	}
	
	@After
	public void tearDown() {
		JdbcTestUtils.deleteFromTables(jdbcTemplate, "subscriptions");
		JdbcTestUtils.deleteFromTables(jdbcTemplate, "groups");
		JdbcTestUtils.deleteFromTables(jdbcTemplate, "users");
	}
}
