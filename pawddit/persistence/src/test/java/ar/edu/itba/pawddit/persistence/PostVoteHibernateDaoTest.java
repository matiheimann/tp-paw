package ar.edu.itba.pawddit.persistence;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

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
import ar.edu.itba.pawddit.model.Post;
import ar.edu.itba.pawddit.model.User;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = TestConfig.class)
@Sql("classpath:postVoteHibernateDaoTestScript.sql")
@Transactional
public class PostVoteHibernateDaoTest {
	
	private static final LocalDateTime GROUP_CREATION_DATE = LocalDateTime.parse("2018-09-21 19:15", DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
		
	@PersistenceContext
	private EntityManager em;
	
	@Autowired
	private DataSource ds;
	
	@Autowired
	private PostVoteHibernateDao postVoteDao;
	
	@Autowired
	private UserHibernateDao userDao;
	
	@Autowired
	private PostHibernateDao postDao;
	
	@Autowired
	private GroupHibernateDao groupDao;
	
	private JdbcTemplate jdbcTemplate;
	
	private User userDummy;
	
	private Group groupDummy;
	
	private Post postDummy;
	
	@Before
	public void setUp() {
		jdbcTemplate = new JdbcTemplate(ds);
		
		userDummy = userDao.create("dummyUser", "dummyPassword", "dummyEmail", false, true);
		groupDummy = groupDao.create("dummyName", GROUP_CREATION_DATE, "dummyDescription", userDummy);
		postDummy = postDao.create("dummyTitle", "dummyContent", GROUP_CREATION_DATE, groupDummy, userDummy, "");
	}
	
	@Test
	public void checkNonExistingVote() {
		Assert.assertEquals(0, postVoteDao.checkVote(userDummy, postDummy));
	}
	
	@Test
	public void checkVotingOnNonVotedPost() {
		postVoteDao.votePost(userDummy, postDummy, 1);
		
		Assert.assertEquals(1, postVoteDao.checkVote(userDummy, postDummy));
	}
	
	@Test
	public void checkCancelVoteTest() {
		postVoteDao.votePost(userDummy, postDummy, 1);
		
		postVoteDao.cancelVote(userDummy, postDummy);
		
		Assert.assertEquals(0, postVoteDao.checkVote(userDummy, postDummy));
	}
	
	@Test
	public void checkChangeUpvoteTest() {
		postVoteDao.votePost(userDummy, postDummy, 1);
		
		postVoteDao.changeVote(userDummy, postDummy);
		
		Assert.assertEquals(-1, postVoteDao.checkVote(userDummy, postDummy));
	}
	
	@Test
	public void checkChangeDownvoteTest() {
		postVoteDao.votePost(userDummy, postDummy, -1);

		postVoteDao.changeVote(userDummy, postDummy);
		
		Assert.assertEquals(1, postVoteDao.checkVote(userDummy, postDummy));
	}
	
	@After
	public void tearDown() {
		JdbcTestUtils.deleteFromTables(jdbcTemplate, "voteposts");
		JdbcTestUtils.deleteFromTables(jdbcTemplate, "posts");
		JdbcTestUtils.deleteFromTables(jdbcTemplate, "subscriptions");
		JdbcTestUtils.deleteFromTables(jdbcTemplate, "groups");
		JdbcTestUtils.deleteFromTables(jdbcTemplate, "users");
	}
}
