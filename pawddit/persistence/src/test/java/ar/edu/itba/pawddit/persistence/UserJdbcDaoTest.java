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
import org.springframework.test.jdbc.JdbcTestUtils;

import ar.edu.itba.pawddit.model.User;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = TestConfig.class)
@Sql("classpath:schema.sql")
public class UserJdbcDaoTest {
	private static final String USERNAME = "Username";
	private static final String PASSWORD = "Password";
	private static final String EMAIL = "Email";
	private static final int SCORE = 1;
	
	@Autowired
	private DataSource ds;
	
	@Autowired
	private UserJdbcDao userDao;
	private JdbcTemplate jdbcTemplate;
	
	@Before
	public void setUp() {
		jdbcTemplate = new JdbcTemplate(ds);
		JdbcTestUtils.deleteFromTables(jdbcTemplate, "users");
	}
	
	@Test
	public void testCreate() {
		final User user = userDao.create(USERNAME, PASSWORD, EMAIL, SCORE);
		Assert.assertNotNull(user);
		Assert.assertEquals(USERNAME, user.getUsername());
		Assert.assertEquals(PASSWORD, user.getPassword());
		Assert.assertEquals(EMAIL, user.getEmail());
		Assert.assertEquals(SCORE, user.getScore());
		Assert.assertEquals(1, JdbcTestUtils.countRowsInTable(jdbcTemplate, "users"));
	}
}
