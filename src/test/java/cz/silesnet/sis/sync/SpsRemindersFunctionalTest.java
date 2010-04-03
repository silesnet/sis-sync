package cz.silesnet.sis.sync;

import java.sql.ResultSet;
import java.sql.SQLException;
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
import org.springframework.batch.item.ExecutionContext;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemStream;
import org.springframework.batch.repeat.ExitStatus;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.test.AbstractDependencyInjectionSpringContextTests;
import org.subethamail.wiser.Wiser;
import org.subethamail.wiser.WiserMessage;

import cz.silesnet.sis.sync.domain.Reminder;

public class SpsRemindersFunctionalTest extends AbstractDependencyInjectionSpringContextTests {
  private static Log log = LogFactory.getLog(SpsRemindersFunctionalTest.class);

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
    return new String[]{"classpath:spsReminderJob.xml"};
  }

  @SuppressWarnings("unchecked")
  @Test
  public void testRemindersSelectSQL() throws Exception {
    dataSource = (DataSource) applicationContext.getBean("spsDataSource");
    dbTester = initializeDatabase(dataSource);
    dbTester.onTearDown();
    // test selected SPS customers
    JdbcTemplate template = new JdbcTemplate(dataSource);
    String sql = "SELECT DISTINCT RefAD FROM FA WHERE DATEDIFF('dd', DatSplat, '2009-02-25') >= 10 AND KcLikv >= 5 ORDER BY RefAD";
    List<String> customers = template.query(sql, new RowMapper() {

      public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
        return rs.getString("RefAD");
      }
    });
    for (String number : customers) {
      log.debug(number);
    }
    assertEquals(3, customers.size());
  }

  @Test
  public void testReminderItemReader() throws Exception {
    dataSource = (DataSource) applicationContext.getBean("spsDataSource");
    dbTester = initializeDatabase(dataSource);
    dbTester.onTearDown();
    ItemReader reader = (ItemReader) applicationContext.getBean("reminderItemReader");
    ExecutionContext executionContext = new ExecutionContext();
    ((ItemStream) reader).open(executionContext);
    // read 3 reminders
    Reminder reminder;
    reminder = (Reminder) reader.read();
    log.debug(reminder);
    assertEquals(2, reminder.getCustomer().getId());
    assertEquals(1, reminder.getInvoices().size());
    reminder = (Reminder) reader.read();
    log.debug(reminder);
    assertEquals(3, reminder.getCustomer().getId());
    assertEquals(1, reminder.getInvoices().size());
    reminder = (Reminder) reader.read();
    log.debug(reminder);
    assertEquals(4, reminder.getCustomer().getId());
    assertEquals(2, reminder.getInvoices().size());
    reminder = (Reminder) reader.read();
    log.debug(reminder);
    assertNull(reminder);
    ((ItemStream) reader).close(executionContext);
  }

  public void testReminderJob() throws Exception {
    dataSource = (DataSource) applicationContext.getBean("spsDataSource");
    dbTester = initializeDatabase(dataSource);
    // start SMTP server
    Wiser smtp = new Wiser();
    smtp.start();
    jobParameters = new JobParameters();
    JobExecution jobExecution = launcher.run(job, jobParameters);
    assertEquals(ExitStatus.FINISHED, jobExecution.getExitStatus());
    for (WiserMessage message : smtp.getMessages()) {
      log.debug("Email received for: " + message.getEnvelopeReceiver());
    }
    assertEquals(3, smtp.getMessages().size());
    smtp.stop();
    dbTester.onTearDown();
  }

  public static IDatabaseTester initializeDatabase(DataSource dataSource) throws Exception {
    DbUtils.initializeDatabase(dataSource, "init-hsqldb-sps.sql");
    return DbUtils.createAndInitializeDatabaseTester(dataSource,
        "data/20090310_db_sps_reminders.xml");
  }

}
