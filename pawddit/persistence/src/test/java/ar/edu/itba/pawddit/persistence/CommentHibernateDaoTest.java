package ar.edu.itba.pawddit.persistence;

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
import ar.edu.itba.pawddit.model.Group;
import ar.edu.itba.pawddit.model.Post;
import ar.edu.itba.pawddit.model.User;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = TestConfig.class)
@Sql("classpath:commentHibernateDaoTestScript.sql")
@Transactional
public class CommentHibernateDaoTest {
	private static final String TEST_COMMENT_CONTENT = "test_content";
	private static final String TEST_COMMENT_CONTENT_1 = "test_content_1";
	private static final String TEST_COMMENT_CONTENT_2 = "test_content_2";

	private static final LocalDateTime TEST_DATE = LocalDateTime.parse("2018-09-21 19:15", DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
	
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
	private CommentHibernateDao commentDao;
		
	private JdbcTemplate jdbcTemplate;	
	
	private User userDummy;
	
	private Group groupDummy;
	
	private Post postDummy;
	
	private Comment commentDummy;
	
	@Before
	public void setUp() {
		jdbcTemplate = new JdbcTemplate(ds);
		
		userDummy = userDao.create("username", "password", "email", false, true);
		groupDummy = groupDao.create("name", TEST_DATE, "description", userDummy);
		postDummy = postDao.create("title", "content", TEST_DATE, groupDummy, userDummy, "");
		commentDummy = commentDao.create(TEST_COMMENT_CONTENT, postDummy, null, userDummy, TEST_DATE);
	}
	
	@Test
	public void findCommentsByUserTest() {
		Comment comment = commentDao.create(TEST_COMMENT_CONTENT_1, postDummy, null, userDummy, TEST_DATE); 
		Comment otherComment = commentDao.create(TEST_COMMENT_CONTENT_2, postDummy, null, userDummy, TEST_DATE); 
		
		final List<Comment> comments = commentDao.findByUser(userDummy, 5, 0);
		
		Assert.assertTrue(comments.contains(comment));
		Assert.assertTrue(comments.contains(otherComment));
	}
	
	@Test
	public void findCommentsByPostTest() {
		Comment comment = commentDao.create(TEST_COMMENT_CONTENT_1, postDummy, null, userDummy, TEST_DATE); 
		Comment otherComment = commentDao.create(TEST_COMMENT_CONTENT_2, postDummy, null, userDummy, TEST_DATE); 

		final List<Comment> comments = commentDao.findByPost(postDummy, 5, 0);

		Assert.assertTrue(comments.contains(comment));
		Assert.assertTrue(comments.contains(otherComment));
	}
	
	@Test
	public void findCommentByIdTest() {
		Optional<Comment> commentFound = commentDao.findById(postDummy, commentDummy.getCommentid());
		
		Assert.assertTrue(commentFound.isPresent());
		Assert.assertEquals(commentDummy.getCommentid(), commentFound.get().getCommentid());
	}
	
	@After
	public void tearDown() {
		JdbcTestUtils.deleteFromTables(jdbcTemplate, "images");
		JdbcTestUtils.deleteFromTables(jdbcTemplate, "comments");
		JdbcTestUtils.deleteFromTables(jdbcTemplate, "posts");
		JdbcTestUtils.deleteFromTables(jdbcTemplate, "groups");
		JdbcTestUtils.deleteFromTables(jdbcTemplate, "users");
	}
}
