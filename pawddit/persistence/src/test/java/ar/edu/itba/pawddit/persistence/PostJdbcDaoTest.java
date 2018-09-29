package ar.edu.itba.pawddit.persistence;

import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;

import javax.sql.DataSource;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.jdbc.JdbcTestUtils;

import ar.edu.itba.pawddit.model.Post;

import org.junit.After;
import org.junit.Assert;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = TestConfig.class)
@Sql("classpath:postJdbcDaoTestScript.sql")
public class PostJdbcDaoTest {
	private static final String TEST_POST_TITLE = "test_title";
	private static final String TEST_POST_CONTENT = "test_content";
	private static final Timestamp TEST_POST_CREATION_DATE = Timestamp.valueOf("2018-09-21 19:15:40.5");
	
	private static final String CREATED_TEST_USERNAME = "testUser";
	private static final String CREATED_TEST_GROUP = "testGroup";
	
	private static final String CREATED_TEST_POST_TITLE_1 = "testPost1";
	private static final long CREATED_TEST_POST_1_ID = 1;
	private static final String CREATED_TEST_POST_TITLE_2 = "testPost2";
	private static final String CREATED_TEST_POST_TITLE_3 = "testPost3";
	
	@Autowired
	private DataSource ds;
	
	@Autowired
	private PostJdbcDao postDao;
	
	@Autowired
	private UserJdbcDao userDao; //User dao already tested successfully
	
	@Autowired
	private GroupJdbcDao groupDao; //Group dao already tested successfully
	
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
		Assert.assertEquals(1, JdbcTestUtils.countRowsInTableWhere(jdbcTemplate, "posts", "title = '" + TEST_POST_TITLE + "'"));
	}
	
	@Test
	public void findAllTest() {
		final List<Post> posts = postDao.findAll(5, 0, null);
		Assert.assertEquals(3, posts.size());
		Assert.assertEquals(CREATED_TEST_POST_TITLE_1, posts.get(0).getTitle());
		Assert.assertEquals(CREATED_TEST_POST_TITLE_2, posts.get(1).getTitle());
		Assert.assertEquals(CREATED_TEST_POST_TITLE_3, posts.get(2).getTitle());
	}
	
	@Test
	public void findByGroupTest() {
		final List<Post> posts = postDao.findByGroup(groupDao.findByName(CREATED_TEST_GROUP).get(), 5, 0, null);
		Assert.assertEquals(3, posts.size());
		Assert.assertEquals(CREATED_TEST_POST_TITLE_1, posts.get(0).getTitle());
		Assert.assertEquals(CREATED_TEST_POST_TITLE_2, posts.get(1).getTitle());
		Assert.assertEquals(CREATED_TEST_POST_TITLE_3, posts.get(2).getTitle());
	}
	
	@Test
	public void findByUserTest() {
		final List<Post> posts = postDao.findByUser(userDao.findByUsername(CREATED_TEST_USERNAME).get(), 5, 0, null);
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
		final List<Post> posts = postDao.findBySubscriptions(userDao.findByUsername(CREATED_TEST_USERNAME).get(), 5, 0, null);
		Assert.assertEquals(3, posts.size());
		Assert.assertEquals(CREATED_TEST_POST_TITLE_1, posts.get(0).getTitle());
		Assert.assertEquals(CREATED_TEST_POST_TITLE_2, posts.get(1).getTitle());
		Assert.assertEquals(CREATED_TEST_POST_TITLE_3, posts.get(2).getTitle());
	}
	
	@After
	public void tearDown() {
		JdbcTestUtils.deleteFromTables(jdbcTemplate, "users");
	}
}
