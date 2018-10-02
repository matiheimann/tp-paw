package ar.edu.itba.pawddit.persistence;

import java.util.Optional;

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

import ar.edu.itba.pawddit.model.User;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = TestConfig.class)
@Sql("classpath:userJdbcDaoTestScript.sql")
public class UserJdbcDaoTest {
	private static final String NEW_USERNAME = "Username";
	private static final String NEW_PASSWORD = "Password";
	private static final String NEW_EMAIL = "Email";
	
	private static final int USER_ID_TO_FIND = 1;
	private static final String USER_USERNAME_TO_FIND = "testUsername";
	
	@Autowired
	private DataSource ds;
	
	@Autowired
	private UserJdbcDao userDao;
	
	private JdbcTemplate jdbcTemplate;
	
	@Before
	public void setUp() {
		jdbcTemplate = new JdbcTemplate(ds);
	}
	
	@Test
	public void testCreate() {
		final User user = userDao.create(NEW_USERNAME, NEW_PASSWORD, NEW_EMAIL);
		Assert.assertNotNull(user);
		Assert.assertEquals(NEW_USERNAME, user.getUsername());
		Assert.assertEquals(NEW_PASSWORD, user.getPassword());
		Assert.assertEquals(NEW_EMAIL, user.getEmail());
		Assert.assertEquals(1, JdbcTestUtils.countRowsInTableWhere(jdbcTemplate, "users", "username = '" + NEW_USERNAME + "'"));
	}
	
	@Test
	public void testFindUserById() {
		final Optional<User> user = userDao.findById(USER_ID_TO_FIND);
		Assert.assertTrue(user.isPresent());
		Assert.assertEquals(USER_ID_TO_FIND, user.get().getUserid());
	}
	
	@Test
	public void testFindUserByUsername() {
		final Optional<User> user = userDao.findByUsername(USER_USERNAME_TO_FIND);
		Assert.assertTrue(user.isPresent());
		Assert.assertEquals(USER_USERNAME_TO_FIND, user.get().getUsername());
	}
	
	@After
	public void tearDown() {
		JdbcTestUtils.deleteFromTables(jdbcTemplate, "users");
	}
}
