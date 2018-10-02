package ar.edu.itba.pawddit.persistence;

import javax.sql.DataSource;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import ar.edu.itba.pawddit.model.Post;
import ar.edu.itba.pawddit.model.User;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = TestConfig.class)
@Sql("classpath:postVoteJdbcDaoTestScript.sql")
public class PostVoteJdbcDaoTest {
	
	private static final String EXISTING_VOTING_USER_USERNAME = "votingUser";
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
		final User votingUser = userDao.findByUsername(EXISTING_VOTING_USER_USERNAME).get();
		Assert.assertEquals(new Integer(1), postVoteDao.checkVote(votingUser, post));
	}
}
