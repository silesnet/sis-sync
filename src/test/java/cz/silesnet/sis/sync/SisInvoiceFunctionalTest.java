package cz.silesnet.sis.sync;

import java.io.BufferedReader;
import java.io.FileReader;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.sql.DataSource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.dbunit.IDatabaseTester;
import org.junit.Test;
import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.job.SimpleJob;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.core.io.Resource;
import org.springframework.test.AbstractDependencyInjectionSpringContextTests;

public class SisInvoiceFunctionalTest extends AbstractDependencyInjectionSpringContextTests {
  private static Log log = LogFactory.getLog(SisInvoiceFunctionalTest.class);

  private JobLauncher launcher;
  private SimpleJob job;
  private JobParameters jobParameters;
  private DataSource dataSource;
  private IDatabaseTester dbTester;

  public void setLauncher(JobLauncher launcher) {
    this.launcher = launcher;
  }

  public void setJob(SimpleJob job) {
    this.job = job;
  }

  @Override
  protected String[] getConfigLocations() {
    return new String[]{"classpath:sisInvoiceJob.xml"};
  }

  @Override
  protected void onSetUp() throws Exception {
    super.onSetUp();
    dataSource = (DataSource) applicationContext.getBean("dataSource");
    dbTester = SisInvoiceFunctionalTest.initializeDatabase(dataSource);
  }

  @Override
  protected void onTearDown() throws Exception {
    super.onTearDown();
    dbTester.onTearDown();
  }

  @Test
  public void testName() throws Exception {
    // FIXME remove
  }

  // @Test
  public void fixtestSisInvoiceJob() throws Exception {
    // FIXME
    String nowTimeStamp = (new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")).format(new Date());
    log.debug(nowTimeStamp);
    dbTester = initializeDatabase(dataSource);
    ContextUtil.trimSteps(job, "importInvoices");
    // run the job
    jobParameters = new JobParameters();
    JobExecution jobExecution = launcher.run(job, jobParameters);
    assertEquals(ExitStatus.COMPLETED, jobExecution.getExitStatus());
    /*
     * Can not check results against database because SPS duplicity checking. At
     * least we will check against SPS response file for correct number of
     * processed invoices. Last writer is thus not tested.
     */
    Resource responseResource = (Resource) getApplicationContext().getBean("invoicesResponseFile");
    BufferedReader reader = new BufferedReader(new FileReader(responseResource.getFile()));
    Pattern itemPattern = Pattern
        .compile("<rsp:responsePackItem.* id=\".+_\\d+_(\\d+)\".* state=\"ok\".*>");
    int count = 0;
    String line;
    while ((line = reader.readLine()) != null) {
      Matcher matcher = itemPattern.matcher(line);
      if (matcher.matches()) {
        count++;
        log.debug(matcher.group(1));
      }
    }
    assertEquals(3, count);
  }

  public static IDatabaseTester initializeDatabase(DataSource dataSource) throws Exception {
    DbUtils.initializeDatabase(dataSource, "init-hsqldb.sql");
    return DbUtils.createAndInitializeDatabaseTester(dataSource, "data/20081226_db_invoices.xml");
  }

}
