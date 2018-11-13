package ar.edu.itba.pawddit.persistence;

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

import ar.edu.itba.pawddit.model.Post;
import ar.edu.itba.pawddit.model.User;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = TestConfig.class)
@Sql("classpath:postVoteHibernateDaoTestScript.sql")
@Transactional
public class PostVoteHibernateDaoTest {
	
	private static final String EXISTING_UPVOTING_USER_USERNAME = "upvotingUser";
	private static final String EXISTING_DOWNVOTING_USER_USERNAME = "downvotingUser";
	private static final String EXISTING_NON_VOTING_USER_USERNAME = "nonVotingUser";
	private static final String EXISTING_POSTING_USER_USERNAME = "postingUser";
	
	@PersistenceContext
	private EntityManager em;
	
	@Autowired
	private DataSource ds;
	
	@Autowired
	private PostVoteHibernateDao postVoteDao;
	
	@Autowired
	private UserHibernateDao userDao; //User dao already tested successfully
	
	@Autowired
	private PostHibernateDao postDao; //Post dao already tested successfully
	
	private JdbcTemplate jdbcTemplate;
	private User user;
	private Post post;
	
	@Before
	public void setUp() {
		jdbcTemplate = new JdbcTemplate(ds);
		user = userDao.findByUsername(EXISTING_POSTING_USER_USERNAME).get();
		post = postDao.findByUser(user, 5, 0, null, "all").get(0);
	}
	
	@Test
	public void checkExistingUpvote() {
		final User upvotingUser = userDao.findByUsername(EXISTING_UPVOTING_USER_USERNAME).get();
		Assert.assertEquals(1, postVoteDao.checkVote(upvotingUser, post));
	}
	
	@Test
	public void checkExistingDownVote() {
		final User downvotingUser = userDao.findByUsername(EXISTING_DOWNVOTING_USER_USERNAME).get();
		Assert.assertEquals(-1, postVoteDao.checkVote(downvotingUser, post));
	}
	
	@Test
	public void checkNonExistingVote() {
		final User nonVotingUser = userDao.findByUsername(EXISTING_NON_VOTING_USER_USERNAME).get();
		Assert.assertEquals(0, postVoteDao.checkVote(nonVotingUser, post));
	}
	
	@Test
	public void votingOnNonVotedPost() {
		final User votingUser = userDao.findByUsername(EXISTING_NON_VOTING_USER_USERNAME).get();
		Assert.assertEquals(0, postVoteDao.checkVote(votingUser, post));
		postVoteDao.votePost(votingUser, post, 1);
		Assert.assertEquals(1, postVoteDao.checkVote(votingUser, post));
	}
	
	@Test
	public void cancelVoteTest() {
		final User upvotingUser = userDao.findByUsername(EXISTING_UPVOTING_USER_USERNAME).get();
		postVoteDao.cancelVote(upvotingUser, post);
		Assert.assertEquals(0, postVoteDao.checkVote(upvotingUser, post));
	}
	
	@Test
	public void changeUpvoteTest() {
		final User upvotingUser = userDao.findByUsername(EXISTING_UPVOTING_USER_USERNAME).get();
		postVoteDao.changeVote(upvotingUser, post);
		Assert.assertEquals(-1, postVoteDao.checkVote(upvotingUser, post));
	}
	
	@Test
	public void changeDownvoteTest() {
		final User downvotingUser = userDao.findByUsername(EXISTING_DOWNVOTING_USER_USERNAME).get();
		postVoteDao.changeVote(downvotingUser, post);
		Assert.assertEquals(1, postVoteDao.checkVote(downvotingUser, post));
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
