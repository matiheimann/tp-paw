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
	private static final LocalDateTime TEST_DATE = LocalDateTime.parse("2018-09-21 19:15", DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
	
	private static final String CREATED_TEST_GROUP = "testGroup";
	
	private static final String CREATED_TEST_POST_TITLE_1 = "testPost1";
	private static final String CREATED_TEST_POST_TITLE_2 = "testPost2";
	private static final String CREATED_TEST_POST_TITLE_3 = "testPost3";
	
	@PersistenceContext
	private EntityManager em;
	
	@Autowired
	private DataSource ds;
	
	@Autowired
	private PostHibernateDao postDao;
	
	@Autowired
	private UserHibernateDao userDao;
	
	@Autowired
	private GroupHibernateDao groupDao;
	
	@Autowired
	private SubscriptionHibernateDao subscriptionDao;
	
	private JdbcTemplate jdbcTemplate;	
	
	private User userDummy;
	
	private Group groupDummy;
		
	@Before
	public void setUp() {
		jdbcTemplate = new JdbcTemplate(ds);
		
		userDummy = userDao.create("testUser", "testPassword", "testEmail", false, true);
		groupDummy = groupDao.create(CREATED_TEST_GROUP, TEST_DATE, "description", userDummy);
	}
	
	@Test
	public void createPostTest() {		
		final Post post = postDao.create(TEST_POST_TITLE, TEST_POST_CONTENT, TEST_DATE, groupDummy,  userDummy, "");
		
		Assert.assertEquals(TEST_POST_TITLE, post.getTitle());
		Assert.assertEquals(TEST_POST_CONTENT, post.getContent());
		Assert.assertEquals(TEST_DATE, post.getDate());
	}
	
	@Test
	public void findAllTest() {		
		Post firstPost = postDao.create(CREATED_TEST_POST_TITLE_1, "content", TEST_DATE, groupDummy, userDummy, "");
		Post secondPost = postDao.create(CREATED_TEST_POST_TITLE_2, "content", TEST_DATE, groupDummy, userDummy, "");
		Post thirdPost = postDao.create(CREATED_TEST_POST_TITLE_3, "content", TEST_DATE, groupDummy, userDummy, "");
		
		final List<Post> posts = postDao.findAll(5, 0, null, "all");
		
		Assert.assertEquals(3, posts.size());
		Assert.assertEquals(firstPost.getTitle(), posts.get(0).getTitle());
		Assert.assertEquals(secondPost.getTitle(), posts.get(1).getTitle());
		Assert.assertEquals(thirdPost.getTitle(), posts.get(2).getTitle());
	}
	
	@Test
	public void findByGroupTest() {		
		Post firstPost = postDao.create(CREATED_TEST_POST_TITLE_1, "content", TEST_DATE, groupDummy, userDummy, "");
		Post secondPost = postDao.create(CREATED_TEST_POST_TITLE_2, "content", TEST_DATE, groupDummy, userDummy, "");
		Post thirdPost = postDao.create(CREATED_TEST_POST_TITLE_3, "content", TEST_DATE, groupDummy, userDummy, "");
		
		final List<Post> posts = postDao.findByGroup(groupDummy, 5, 0, null, "all");
		
		Assert.assertEquals(3, posts.size());
		Assert.assertTrue(posts.contains(firstPost));
		Assert.assertTrue(posts.contains(secondPost));
		Assert.assertTrue(posts.contains(thirdPost));
	}
	
	@Test
	public void findByUserTest() {		
		Post firstPost = postDao.create(CREATED_TEST_POST_TITLE_1, "content", TEST_DATE, groupDummy, userDummy, "");
		Post secondPost = postDao.create(CREATED_TEST_POST_TITLE_2, "content", TEST_DATE, groupDummy, userDummy, "");
		Post thirdPost = postDao.create(CREATED_TEST_POST_TITLE_3, "content", TEST_DATE, groupDummy, userDummy, "");
		
		final List<Post> posts = postDao.findByUser(userDummy, 5, 0, null, "all");
		
		Assert.assertEquals(3, posts.size());
		Assert.assertTrue(posts.contains(firstPost));
		Assert.assertTrue(posts.contains(secondPost));
		Assert.assertTrue(posts.contains(thirdPost));
	}
	
	@Test
	public void findByIdTest() {		
		Post postToFind = postDao.create(TEST_POST_TITLE, TEST_POST_CONTENT, TEST_DATE, groupDummy,  userDummy, "");
		
		final Optional<Post> postFound = postDao.findById(groupDummy, postToFind.getPostid());
		
		Assert.assertTrue(postFound.isPresent());
		Assert.assertEquals(postToFind.getPostid(), postFound.get().getPostid());
	}
	
	@Test
	public void findBySubscription() {
		subscriptionDao.suscribe(userDummy, groupDummy);
		
		Post firstPost = postDao.create(CREATED_TEST_POST_TITLE_1, "content", TEST_DATE, groupDummy, userDummy, "");
		Post secondPost = postDao.create(CREATED_TEST_POST_TITLE_2, "content", TEST_DATE, groupDummy, userDummy, "");
		Post thirdPost = postDao.create(CREATED_TEST_POST_TITLE_3, "content", TEST_DATE, groupDummy, userDummy, "");
		
		final List<Post> posts = postDao.findBySubscriptions(userDummy, 5, 0, null, "all");
		
		Assert.assertEquals(3, posts.size());
		Assert.assertTrue(posts.contains(firstPost));
		Assert.assertTrue(posts.contains(secondPost));
		Assert.assertTrue(posts.contains(thirdPost));
	}
	
	@After
	public void tearDown() {
		JdbcTestUtils.deleteFromTables(jdbcTemplate, "posts");
		JdbcTestUtils.deleteFromTables(jdbcTemplate, "subscriptions");
		JdbcTestUtils.deleteFromTables(jdbcTemplate, "groups");
		JdbcTestUtils.deleteFromTables(jdbcTemplate, "users");
	}
}
