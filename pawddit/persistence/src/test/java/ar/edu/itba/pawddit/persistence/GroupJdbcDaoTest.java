package ar.edu.itba.pawddit.persistence;

import java.sql.Timestamp;
import java.util.List;
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

import ar.edu.itba.pawddit.model.Group;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = TestConfig.class)
@Sql("classpath:groupJdbcDaoTestScript.sql")
public class GroupJdbcDaoTest {
	private static final String GROUP_NAME = "createGroupTest";
	private static final Timestamp GROUP_CREATION_DATE = Timestamp.valueOf("2018-09-21 19:15:40.5");
	private static final String GROUP_DESCRIPTION = "testDescription";
	private static final String GROUP_OWNER = "testUser";
	
	private static final String CREATED_TEST_GROUP_1_NAME = "createdTestGroup1";
	private static final String CREATED_TEST_GROUP_2_NAME = "createdTestGroup2";
	private static final String CREATED_TEST_GROUP_3_NAME = "createdTestGroup3";
	
	@Autowired
	private DataSource ds;
	
	@Autowired
	private GroupJdbcDao groupDao;
	
	@Autowired
	private UserJdbcDao userDao;	//User dao tested successfully
	
	private JdbcTemplate jdbcTemplate;
	
	@Before
	public void setUp() {
		jdbcTemplate = new JdbcTemplate(ds);
	}
	
	@Test
	public void createGroupTest() {
		final Group group = groupDao.create(GROUP_NAME, GROUP_CREATION_DATE, GROUP_DESCRIPTION, userDao.findByUsername(GROUP_OWNER).get());
		Assert.assertEquals(GROUP_NAME, group.getName());
		Assert.assertEquals(GROUP_CREATION_DATE, group.getDate());
		Assert.assertEquals(GROUP_DESCRIPTION, group.getDescription());
		Assert.assertEquals(GROUP_OWNER, group.getOwner().getUsername());
		Assert.assertEquals(1, JdbcTestUtils.countRowsInTableWhere(jdbcTemplate, "groups", "name = '" + GROUP_NAME + "'"));
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
	
	@Test
	public void findAllTest() {
		final List<Group> groups = groupDao.findAll();
		Assert.assertEquals(3, groups.size());
		Assert.assertEquals(CREATED_TEST_GROUP_1_NAME, groups.get(0).getName());
		Assert.assertEquals(CREATED_TEST_GROUP_2_NAME, groups.get(1).getName());
		Assert.assertEquals(CREATED_TEST_GROUP_3_NAME, groups.get(2).getName());
	}

	@After
	public void tearDown() {
		JdbcTestUtils.deleteFromTables(jdbcTemplate, "users");
	}
}
