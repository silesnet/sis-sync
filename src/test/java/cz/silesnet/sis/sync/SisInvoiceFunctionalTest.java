package cz.silesnet.sis.sync;

import javax.sql.DataSource;

import org.dbunit.IDatabaseTester;
import org.junit.Test;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.repeat.ExitStatus;
import org.springframework.core.io.ClassPathResource;
import org.springframework.test.AbstractDependencyInjectionSpringContextTests;

public class SisInvoiceFunctionalTest extends AbstractDependencyInjectionSpringContextTests {
    // private static Log log = LogFactory.getLog(SisInvoiceFunctionalTest.class);

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

    public void setDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    protected String[] getConfigLocations() {
        return new String[] { "classpath:jobs/sisInvoiceJob.xml" };
    }

    @Test
    public void testSisInvoiceJob() throws Exception {
        dbTester = initializeDatabase(dataSource);
        // run the job
        jobParameters = new JobParameters();
        JobExecution jobExecution = launcher.run(job, jobParameters);
        assertEquals(ExitStatus.FINISHED, jobExecution.getExitStatus());
        dbTester.onTearDown();
        // test synchronized customers
        // List<Customer> customers = template
        // .query("SELECT * FROM customers WHERE symbol <> ''", new SisCustomerMapper());
        // assertEquals(5, customers.size());
        // for (Customer customer : customers) {
        // assertTrue(Long.valueOf(customer.getSymbol()) > 0);
        // log.debug(customer.getSymbol());
        // }
    }

    public static IDatabaseTester initializeDatabase(DataSource dataSource) throws Exception {
        DbUtils.initializeDatabase(dataSource, new ClassPathResource("init-hsqldb.sql"));
        return DbUtils.createAndInitializeDatabaseTester(dataSource, new ClassPathResource(
                "data/20081226_db_invoices.xml"));
    }

}
