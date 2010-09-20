package cz.silesnet.sis.sync;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;

import javax.sql.DataSource;

import org.junit.Test;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.job.SimpleJob;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.jdbc.core.JdbcTemplate;

public class InvoiceSyncFunctionalTest {

//  skipped for now
//  @Test
  public void testConfirmInvoicesImportStep() throws Exception {
    ApplicationContext context = new ClassPathXmlApplicationContext("sisInvoiceJob.xml");
    DataSource dataSource = (DataSource) context.getBean("dataSource");
    JdbcTemplate jdbc = (JdbcTemplate) context.getBean("jdbcTemplate");
    DbUtils.initializeDatabase(dataSource, "init-hsqldb.sql");
    DbUtils.createAndInitializeDatabaseTester(dataSource, "data/invoices-confirm-import.xml");

    JobLauncher launcher = (JobLauncher) context.getBean("jobLancher");
    SimpleJob job = (SimpleJob) context.getBean("sisInvoiceJob");
    ContextUtil.trimSteps(job, "confirmInvoicesImport");

    // test
    JobParametersBuilder parametersBuilder = new JobParametersBuilder();
    parametersBuilder.addString("response.file", "xml/invoices-response-20100313.xml");
    launcher.run(job, parametersBuilder.toJobParameters());
    int count = DbUtils.queryCount(jdbc,
        "SELECT count(*) FROM bills WHERE synchronized IS NOT NULL");
    assertThat(count, is(5));
  }

}
