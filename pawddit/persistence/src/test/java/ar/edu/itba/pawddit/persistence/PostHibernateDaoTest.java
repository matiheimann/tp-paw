package ar.edu.itba.pawddit.persistence;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.sql.DataSource;

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
import ar.edu.itba.pawddit.model.Post;
import ar.edu.itba.pawddit.model.User;

import org.junit.After;
import org.junit.Assert;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = TestConfig.class)
@Sql("classpath:postHibernateDaoTestScript.sql")
@Transactional
public class PostHibernateDaoTest {
	private static final String TEST_POST_TITLE = "test_title";
	private static final String TEST_POST_CONTENT = "test_content";	
	private static final LocalDateTime TEST_POST_CREATION_DATE = LocalDateTime.parse("2018-09-21 19:15", DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
	
	private static final String CREATED_TEST_USERNAME = "testUser";
	private static final String CREATED_TEST_GROUP = "testGroup";
	
	private static final String CREATED_TEST_POST_TITLE_1 = "testPost1";
	private static final long CREATED_TEST_POST_1_ID = 1;
	private static final String CREATED_TEST_POST_TITLE_2 = "testPost2";
	private static final String CREATED_TEST_POST_TITLE_3 = "testPost3";
	
	@PersistenceContext
	private EntityManager em;
	
	@Autowired
	private DataSource ds;
	
	@Autowired
	private PostHibernateDao postDao;
	
	@Autowired
	private UserHibernateDao userDao; //User dao already tested successfully
	
	@Autowired
	private GroupHibernateDao groupDao; //Group dao already tested successfully
	
	@Autowired
	private SubscriptionHibernateDao subscriptionDao; //Subscription dao already tested successfully
	
	private JdbcTemplate jdbcTemplate;	
	
	@Before
	public void setUp() {
		jdbcTemplate = new JdbcTemplate(ds);
	}
	
	@Test
	public void createPostTest() {
		final Post post = postDao.create(TEST_POST_TITLE, TEST_POST_CONTENT, TEST_POST_CREATION_DATE, 
				groupDao.findByName(CREATED_TEST_GROUP).get(), 
				userDao.findByUsername(CREATED_TEST_USERNAME).get(), null);
		Assert.assertEquals(TEST_POST_TITLE, post.getTitle());
		Assert.assertEquals(TEST_POST_CONTENT, post.getContent());
		Assert.assertEquals(TEST_POST_CREATION_DATE, post.getDate());
	}
	
	@Test
	public void findAllTest() {
		final List<Post> posts = postDao.findAll(5, 0, null, "all");
		Assert.assertEquals(3, posts.size());
		Assert.assertEquals(CREATED_TEST_POST_TITLE_1, posts.get(0).getTitle());
		Assert.assertEquals(CREATED_TEST_POST_TITLE_2, posts.get(1).getTitle());
		Assert.assertEquals(CREATED_TEST_POST_TITLE_3, posts.get(2).getTitle());
	}
	
	@Test
	public void findByGroupTest() {
		final List<Post> posts = postDao.findByGroup(groupDao.findByName(CREATED_TEST_GROUP).get(), 5, 0, null, "all");
		Assert.assertEquals(3, posts.size());
		Assert.assertEquals(CREATED_TEST_POST_TITLE_1, posts.get(0).getTitle());
		Assert.assertEquals(CREATED_TEST_POST_TITLE_2, posts.get(1).getTitle());
		Assert.assertEquals(CREATED_TEST_POST_TITLE_3, posts.get(2).getTitle());
	}
	
	@Test
	public void findByUserTest() {
		final List<Post> posts = postDao.findByUser(userDao.findByUsername(CREATED_TEST_USERNAME).get(), 5, 0, null, "all");
		Assert.assertEquals(3, posts.size());
		Assert.assertEquals(CREATED_TEST_POST_TITLE_1, posts.get(0).getTitle());
		Assert.assertEquals(CREATED_TEST_POST_TITLE_2, posts.get(1).getTitle());
		Assert.assertEquals(CREATED_TEST_POST_TITLE_3, posts.get(2).getTitle());
	}
	
	@Test
	public void findByIdTest() {
		final Optional<Post> post = postDao.findById(groupDao.findByName(CREATED_TEST_GROUP).get(), CREATED_TEST_POST_1_ID);
		Assert.assertTrue(post.isPresent());
		Assert.assertEquals(CREATED_TEST_POST_1_ID, post.get().getPostid());
	}
	
	@Test
	public void findBySubscription() {
		final User subscribedUser = userDao.findByUsername(CREATED_TEST_USERNAME).get();
		final Group existingGroup = groupDao.findByName(CREATED_TEST_GROUP).get();
		subscriptionDao.suscribe(subscribedUser, existingGroup);
		
		final List<Post> posts = postDao.findBySubscriptions(subscribedUser, 5, 0, null, "all");
		Assert.assertEquals(3, posts.size());
		Assert.assertEquals(CREATED_TEST_POST_TITLE_1, posts.get(0).getTitle());
		Assert.assertEquals(CREATED_TEST_POST_TITLE_2, posts.get(1).getTitle());
		Assert.assertEquals(CREATED_TEST_POST_TITLE_3, posts.get(2).getTitle());
	}
	
	@After
	public void tearDown() {
		JdbcTestUtils.deleteFromTables(jdbcTemplate, "posts");
		JdbcTestUtils.deleteFromTables(jdbcTemplate, "subscriptions");
		JdbcTestUtils.deleteFromTables(jdbcTemplate, "groups");
		JdbcTestUtils.deleteFromTables(jdbcTemplate, "users");
	}
}
