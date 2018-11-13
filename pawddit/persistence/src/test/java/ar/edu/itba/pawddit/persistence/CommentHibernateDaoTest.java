package ar.edu.itba.pawddit.persistence;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
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

import ar.edu.itba.pawddit.model.Comment;
import ar.edu.itba.pawddit.model.Post;
import ar.edu.itba.pawddit.model.User;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = TestConfig.class)
@Sql("classpath:commentHibernateDaoTestScript.sql")
@Transactional
public class CommentHibernateDaoTest {
	private static final String CREATED_TEST_USERNAME = "testUser";
	private static final String TEST_COMMENT_CONTENT = "test_content";
	private static final LocalDateTime COMMENT_CREATION_DATE = LocalDateTime.parse("2018-09-21 19:15", DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
	
	private static final long CREATED_COMMENT_1_ID = 1;
	private static final long CREATED_COMMENT_2_ID = 2;
	private static final long CREATED_COMMENT_3_ID = 3;
	
	@PersistenceContext
	private EntityManager em;
	
	@Autowired
	private DataSource ds;
	
	@Autowired
	private PostHibernateDao postDao; //Post dao already tested successfully
	
	@Autowired
	private UserHibernateDao userDao; //User dao already tested successfully
	
	@Autowired
	private CommentHibernateDao commentDao;
		
	private JdbcTemplate jdbcTemplate;	
	
	@Before
	public void setUp() {
		jdbcTemplate = new JdbcTemplate(ds);
	}
	
	@Test
	public void createCommentTest() {
		final User user = userDao.findByUsername(CREATED_TEST_USERNAME).get();
		final Post post = postDao.findByUser(user, 5, 0, null, "all").get(0);
		Comment comment = commentDao.create(TEST_COMMENT_CONTENT, post, null, user, COMMENT_CREATION_DATE);
		Assert.assertEquals(TEST_COMMENT_CONTENT, comment.getContent());
		Assert.assertEquals(CREATED_TEST_USERNAME, comment.getOwner().getUsername());
		Assert.assertEquals(COMMENT_CREATION_DATE, comment.getDate());
	}
	
	@Test
	public void findCommentsByUserTest() {
		final User user = userDao.findByUsername(CREATED_TEST_USERNAME).get();
		final List<Comment> comment = commentDao.findByUser(user, 5, 0);
		Assert.assertEquals(CREATED_COMMENT_3_ID, comment.get(0).getCommentid());
		Assert.assertEquals(CREATED_COMMENT_2_ID, comment.get(1).getCommentid());
		Assert.assertEquals(CREATED_COMMENT_1_ID, comment.get(2).getCommentid());
	}
	
	@Test
	public void findCommentsByPostTest() {
		final User user = userDao.findByUsername(CREATED_TEST_USERNAME).get();
		final Post post = postDao.findByUser(user, 5, 0, null, "all").get(0);
		final List<Comment> comment = commentDao.findByPost(post, 5, 0);
		Assert.assertEquals(CREATED_COMMENT_3_ID, comment.get(0).getCommentid());
		Assert.assertEquals(CREATED_COMMENT_2_ID, comment.get(1).getCommentid());
		Assert.assertEquals(CREATED_COMMENT_1_ID, comment.get(2).getCommentid());
	}
	
	@Test
	public void findCommentByIdTest() {
		final User user = userDao.findByUsername(CREATED_TEST_USERNAME).get();
		final Post post = postDao.findByUser(user, 5, 0, null, "all").get(0);
		Optional<Comment> comment = commentDao.findById(post, CREATED_COMMENT_1_ID);
		Assert.assertTrue(comment.isPresent());
		Assert.assertEquals(CREATED_COMMENT_1_ID, comment.get().getCommentid());
	}
	
	@After
	public void tearDown() {
		JdbcTestUtils.deleteFromTables(jdbcTemplate, "comments");
		JdbcTestUtils.deleteFromTables(jdbcTemplate, "posts");
		JdbcTestUtils.deleteFromTables(jdbcTemplate, "groups");
		JdbcTestUtils.deleteFromTables(jdbcTemplate, "users");
	}
}
