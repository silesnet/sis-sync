package cz.silesnet.sis.sync;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;

import javax.sql.DataSource;

import org.dbunit.DataSourceDatabaseTester;
import org.dbunit.IDatabaseTester;
import org.dbunit.dataset.IDataSet;
import org.dbunit.dataset.xml.FlatXmlDataSet;
import org.junit.Test;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.repeat.ExitStatus;
import org.springframework.core.io.ClassPathResource;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.AbstractDependencyInjectionSpringContextTests;

public class SisInvoiceFunctionalTest extends AbstractDependencyInjectionSpringContextTests {
    // private static Log log = LogFactory.getLog(SisInvoiceFunctionalTest.class);

    private JobLauncher launcher;
    private Job job;
    private JobParameters jobParameters;
    private DataSource dataSource;

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
        // create customers table
        File scriptFile = new ClassPathResource("init-hsqldb.sql").getFile();
        BufferedReader reader = new BufferedReader(new FileReader(scriptFile));
        StringBuffer fileData = new StringBuffer(1000);
        char[] buf = new char[1024];
        int read = 0;
        while ((read = reader.read(buf)) != -1) {
            fileData.append(buf, 0, read);
        }
        String[] sqlCommands = fileData.toString().split(";");
        JdbcTemplate template = new JdbcTemplate(dataSource);
        for (String rawSql : sqlCommands) {
            String sql = rawSql.trim();
            if (!"".equals(sql)) {
                template.execute(rawSql.trim());
            }
        }
        // import customers initial data
        IDatabaseTester databaseTester = new DataSourceDatabaseTester(dataSource);
        IDataSet dataSet = new FlatXmlDataSet((new ClassPathResource("data/20081226_db_invoices.xml")).getFile());
        databaseTester.setDataSet(dataSet);
        databaseTester.onSetup();
        // run the job
        jobParameters = new JobParameters();
        JobExecution jobExecution = launcher.run(job, jobParameters);
        assertEquals(jobExecution.getExitStatus(), ExitStatus.FINISHED);
        databaseTester.onTearDown();
        // test synchronized customers
        // List<Customer> customers = template
        // .query("SELECT * FROM customers WHERE symbol <> ''", new SisCustomerMapper());
        // assertEquals(5, customers.size());
        // for (Customer customer : customers) {
        // assertTrue(Long.valueOf(customer.getSymbol()) > 0);
        // log.debug(customer.getSymbol());
        // }
    }
}
