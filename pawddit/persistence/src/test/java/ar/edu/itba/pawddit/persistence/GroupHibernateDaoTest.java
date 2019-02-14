package ar.edu.itba.pawddit.persistence;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
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

import ar.edu.itba.pawddit.model.Group;
import ar.edu.itba.pawddit.model.User;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = TestConfig.class)
@Sql("classpath:groupHibernateDaoTestScript.sql")
@Transactional
public class GroupHibernateDaoTest {
	private static final String GROUP_NAME = "createGroupTest";
	private static final LocalDateTime GROUP_CREATION_DATE = LocalDateTime.parse("2018-09-21 19:15", DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
	private static final String GROUP_DESCRIPTION = "testDescription";
	private static final String GROUP_OWNER = "testUser";
	
	private static final String CREATED_TEST_GROUP_1_NAME = "createdTestGroup1";
	
	@PersistenceContext
	private EntityManager em;
	
	@Autowired
	private DataSource ds;
	
	@Autowired
	private UserHibernateDao userDao;
	
	@Autowired
	private GroupHibernateDao groupDao;
		
	private JdbcTemplate jdbcTemplate;
	
	@Before
	public void setUp() {
		jdbcTemplate = new JdbcTemplate(ds);
	}
	
	@Test
	public void createGroupTest() {		
		final User userDummy = userDao.create("testUser", "testPassword", "testEmail", false, true);
		
		final Group groupToCreate = groupDao.create(GROUP_NAME, GROUP_CREATION_DATE, GROUP_DESCRIPTION, userDummy);
		
		Assert.assertEquals(GROUP_NAME, groupToCreate.getName());
		Assert.assertEquals(GROUP_CREATION_DATE, groupToCreate.getDate());
		Assert.assertEquals(GROUP_DESCRIPTION, groupToCreate.getDescription());
		Assert.assertEquals(GROUP_OWNER, groupToCreate.getOwner().getUsername());
	}
	
	@Test
	public void findGroupByNameTest() {
		final Optional<Group> group = groupDao.findByName(CREATED_TEST_GROUP_1_NAME);
		Assert.assertTrue(group.isPresent());
		Assert.assertEquals(CREATED_TEST_GROUP_1_NAME, group.get().getName());
	}
	
	@Test
	public void findUnexistingGroupByNameTest() {
		final Optional<Group> group = groupDao.findByName("this_group_does_not_exist");
		Assert.assertFalse(group.isPresent());
	}

	@After
	public void tearDown() {
		JdbcTestUtils.deleteFromTables(jdbcTemplate, "groups");
		JdbcTestUtils.deleteFromTables(jdbcTemplate, "users");
	}
}
