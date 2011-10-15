package cz.silesnet.sis.sync.dao.impl;

import cz.silesnet.sis.sync.DbUtils;
import org.dbunit.IDatabaseTester;
import org.junit.Test;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.AbstractDependencyInjectionSpringContextTests;

import javax.sql.DataSource;

public class JdbcSettingsDaoTest extends AbstractDependencyInjectionSpringContextTests {
  private JdbcSettingsDao dao;
  private DataSource dataSource;
  private IDatabaseTester dbTester;

  @Override
  protected String[] getConfigLocations() {
    return new String[]{"classpath:job-launcher-context.xml"};
  }

  @Override
  protected void onSetUp() throws Exception {
    super.onSetUp();
    dao = new JdbcSettingsDao();
    JdbcTemplate template = (JdbcTemplate) applicationContext.getBean("jdbcTemplate");
    dao.setJdbcTemplate(template);
    dataSource = (DataSource) applicationContext.getBean("dataSource");
    dbTester = initializeDatabase(dataSource);
  }

  @Override
  protected void onTearDown() throws Exception {
    super.onTearDown();
    dbTester.onTearDown();
  }

  @Test
  public void testRetrieveValueByKey() {
    assertEquals("SIS", dao.valueOf("sis-name"));
  }

  @Test
  public void testFailOnNonExistingKey() {
    try {
      dao.valueOf("non-existing-key-!!!");
      fail();
    } catch (RuntimeException e) {
      // expected to fail
    }
  }

  public static IDatabaseTester initializeDatabase(DataSource dataSource) throws Exception {
    DbUtils.initializeDatabase(dataSource, "init-hsqldb.sql");
    return DbUtils.createAndInitializeDatabaseTester(dataSource, "data/sis_db_settings.xml");
  }
}
