package ar.edu.itba.pawddit.persistence;

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

import ar.edu.itba.pawddit.model.Post;
import ar.edu.itba.pawddit.model.User;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = TestConfig.class)
@Sql("classpath:postVoteJdbcDaoTestScript.sql")
public class PostVoteJdbcDaoTest {
	
	private static final String EXISTING_UPVOTING_USER_USERNAME = "upvotingUser";
	private static final String EXISTING_DOWNVOTING_USER_USERNAME = "downvotingUser";
	private static final String EXISTING_NON_VOTING_USER_USERNAME = "nonVotingUser";
	private static final String EXISTING_POSTING_USER_USERNAME = "postingUser";
	
	@Autowired
	private DataSource ds;
	
	@Autowired
	private PostVoteJdbcDao postVoteDao;
	
	@Autowired
	private UserJdbcDao userDao; //User dao already tested successfully
	
	@Autowired
	private PostJdbcDao postDao; //Post dao already tested successfully
	
	private JdbcTemplate jdbcTemplate;
	private User user;
	private Post post;
	
	@Before
	public void setUp() {
		jdbcTemplate = new JdbcTemplate(ds);
		user = userDao.findByUsername(EXISTING_POSTING_USER_USERNAME).get();
		post = postDao.findByUser(user, 5, 0, null).get(0);
	}
	
	@Test
	public void checkExistingUpvote() {
		final User upvotingUser = userDao.findByUsername(EXISTING_UPVOTING_USER_USERNAME).get();
		Assert.assertEquals(Integer.valueOf(1), postVoteDao.checkVote(upvotingUser, post));
	}
	
	@Test
	public void checkExistingDownVote() {
		final User downvotingUser = userDao.findByUsername(EXISTING_DOWNVOTING_USER_USERNAME).get();
		Assert.assertEquals(Integer.valueOf(-1), postVoteDao.checkVote(downvotingUser, post));
	}
	
	@Test
	public void checkNonExistingVote() {
		final User nonVotingUser = userDao.findByUsername(EXISTING_NON_VOTING_USER_USERNAME).get();
		Assert.assertEquals(Integer.valueOf(0), postVoteDao.checkVote(nonVotingUser, post));
	}
	
	@Test
	public void upvotingOnAlreadyUpvotedPost() {
		final User upvotingUser = userDao.findByUsername(EXISTING_UPVOTING_USER_USERNAME).get();
		Assert.assertFalse(postVoteDao.votePost(upvotingUser, post, 1));
	}
	
	@Test
	public void downvotingOnAlreadyDownVotedPost() {
		final User downvotingUser = userDao.findByUsername(EXISTING_DOWNVOTING_USER_USERNAME).get();
		Assert.assertFalse(postVoteDao.votePost(downvotingUser, post, -1));
	}
	
	@Test
	public void votingOnNonVotedPost() {
		final User votingUser = userDao.findByUsername(EXISTING_NON_VOTING_USER_USERNAME).get();
		Assert.assertTrue(postVoteDao.votePost(votingUser, post, 1));
		Assert.assertEquals(Integer.valueOf(1), postVoteDao.checkVote(votingUser, post));
	}
	
	@Test
	public void cancelVoteTest() {
		final User upvotingUser = userDao.findByUsername(EXISTING_UPVOTING_USER_USERNAME).get();
		postVoteDao.cancelVote(upvotingUser, post);
		Assert.assertEquals(Integer.valueOf(0), postVoteDao.checkVote(upvotingUser, post));
	}
	
	@Test
	public void changeUpvoteTest() {
		final User upvotingUser = userDao.findByUsername(EXISTING_UPVOTING_USER_USERNAME).get();
		postVoteDao.changeVote(upvotingUser, post);
		Assert.assertEquals(Integer.valueOf(-1), postVoteDao.checkVote(upvotingUser, post));
	}
	
	@Test
	public void changeDownvoteTest() {
		final User downvotingUser = userDao.findByUsername(EXISTING_DOWNVOTING_USER_USERNAME).get();
		postVoteDao.changeVote(downvotingUser, post);
		Assert.assertEquals(Integer.valueOf(1), postVoteDao.checkVote(downvotingUser, post));
	}
	
	@After
	public void tearDown() {
		JdbcTestUtils.deleteFromTables(jdbcTemplate, "users");
	}
}
