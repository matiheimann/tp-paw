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

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = TestConfig.class)
@Sql("classpath:imageHibernateDaoTestScript.sql")
@Transactional
public class ImageHibernateDaoTest {
	
	private static final byte[] NEW_IMAGE_TEST = {0};
	private static final String NEW_TOKEN_TEST = "testToken";
	private static final String EXISTING_IMAGE_TOKEN = "token_1";
	
	@PersistenceContext
	private EntityManager em;
	
	@Autowired
	private DataSource ds;
	
	@Autowired
	private ImageHibernateDao imageDao;
	
	private JdbcTemplate jdbcTemplate;
	
	@Before
	public void setUp() {
		jdbcTemplate = new JdbcTemplate(ds);
	}
	
	@Test
	public void saveImageTest() {
		Assert.assertEquals(NEW_TOKEN_TEST, imageDao.saveImage(NEW_IMAGE_TEST, NEW_TOKEN_TEST));
	}
	
	@Test
	public void findByTokenTest() {
		Assert.assertTrue(imageDao.findByToken(EXISTING_IMAGE_TOKEN).isPresent());
	}
	
	@After
	public void tearDown() {
		JdbcTestUtils.deleteFromTables(jdbcTemplate, "images");
	}

}
