package cz.silesnet.sis.sync;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;

import javax.sql.DataSource;

import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.job.SimpleJob;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.jdbc.core.JdbcTemplate;

public class InvoiceUpdateFunctionalTest {

  // FIXME figure out how to inject test resource!
  // @Test
  public void testUpdateInvoiceAccountsStep() throws Exception {
    ApplicationContext context = new ClassPathXmlApplicationContext("sisInvoiceJob.xml");
    DataSource dataSource = (DataSource) context.getBean("dataSource");
    DataSource spsDataSource = (DataSource) context.getBean("spsDataSource");
    JdbcTemplate spsJdbc = (JdbcTemplate) context.getBean("spsJdbcTemplate");
    DbUtils.initializeDatabase(dataSource, "init-hsqldb.sql");
    DbUtils.createAndInitializeDatabaseTester(dataSource, "data/invoices-update-account.xml");
    DbUtils.initializeDatabase(spsDataSource, "init-hsqldb-sps.sql");
    DbUtils
        .createAndInitializeDatabaseTester(spsDataSource, "data/invoices-update-account-sps.xml");

    JobLauncher launcher = (JobLauncher) context.getBean("jobLancher");
    SimpleJob job = (SimpleJob) context.getBean("sisInvoiceJob");
    job.setRestartable(true);
    ContextUtil.trimSteps(job, "updateInvoiceAccounts");

    // test
    JobParametersBuilder parametersBuilder = new JobParametersBuilder();
    parametersBuilder.addString("id", "1235");
    launcher.run(job, parametersBuilder.toJobParameters());
    int count = DbUtils.queryCount(spsJdbc,
        "SELECT count(*) FROM FA WHERE (Ucet IS NOT NULL) AND (KodBanky IS NOT NULL)");
    assertThat(count, is(5));

  }
}
