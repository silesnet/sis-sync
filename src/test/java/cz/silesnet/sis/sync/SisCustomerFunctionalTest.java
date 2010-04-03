package cz.silesnet.sis.sync;

import java.util.List;

import javax.sql.DataSource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.dbunit.IDatabaseTester;
import org.junit.Test;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.repeat.ExitStatus;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.AbstractDependencyInjectionSpringContextTests;

import cz.silesnet.sis.sync.domain.Customer;
import cz.silesnet.sis.sync.mapping.CustomerRowMapper;

public class SisCustomerFunctionalTest extends AbstractDependencyInjectionSpringContextTests {
  private static Log log = LogFactory.getLog(SisCustomerFunctionalTest.class);

  private JobLauncher launcher;
  private Job job;
  private JobParameters jobParameters;
  private DataSource dataSource;
  private IDatabaseTester dbTester;

  public void setLauncher(JobLauncher launcher) {
    this.launcher = launcher;
  }

  public void setJob(Job job) {
    this.job = job;
  }

  @Override
  protected String[] getConfigLocations() {
    return new String[]{"classpath:sisCustomerJob.xml"};
  }

  @SuppressWarnings("unchecked")
  @Test
  public void testSisCustomerJob() throws Exception {
    // FIXME
    fail("FIXME");
    dataSource = (DataSource) applicationContext.getBean("dataSource");
    dbTester = initializeDatabase(dataSource);
    jobParameters = new JobParameters();
    JobExecution jobExecution = launcher.run(job, jobParameters);
    assertEquals(ExitStatus.FINISHED, jobExecution.getExitStatus());
    dbTester.onTearDown();
    // test synchronized customers
    JdbcTemplate template = new JdbcTemplate(dataSource);
    List<Customer> customers = template.query("SELECT * FROM customers WHERE symbol <> ''",
        new CustomerRowMapper());
    for (Customer customer : customers) {
      assertTrue(Long.valueOf(customer.getSymbol()) > 0);
      log.debug(customer.getSymbol() + " [" + customer.getId() + "]");
    }
    assertEquals(5, customers.size());
  }

  public static IDatabaseTester initializeDatabase(DataSource dataSource) throws Exception {
    DbUtils.initializeDatabase(dataSource, "init-hsqldb.sql");
    return DbUtils.createAndInitializeDatabaseTester(dataSource, "data/20081223_db_customers.xml");
  }

}
