package cz.silesnet.sis.sync;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
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
import org.springframework.core.io.ClassPathResource;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.test.AbstractDependencyInjectionSpringContextTests;

import cz.silesnet.sis.sync.domain.Invoice;
import cz.silesnet.sis.sync.mapping.InvoiceRowMapper;

public class SisInvoiceFunctionalTest extends AbstractDependencyInjectionSpringContextTests {
    private static Log log = LogFactory.getLog(SisInvoiceFunctionalTest.class);

    private JobLauncher launcher;
    private Job job;
    private JobParameters jobParameters;
    private DataSource dataSource;
    private IDatabaseTester dbTester;
    private JdbcTemplate template;

    public void setLauncher(JobLauncher launcher) {
        this.launcher = launcher;
    }

    public void setJob(Job job) {
        this.job = job;
    }

    public void setDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
        this.template = jdbcTemplate;
    }

    @Override
    protected String[] getConfigLocations() {
        return new String[]{"classpath:conf/sisInvoiceJob.xml"};
    }

    @SuppressWarnings("unchecked")
    @Test
    public void testSisInvoiceJob() throws Exception {
        String nowTimeStamp = (new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")).format(new Date());
        log.debug(nowTimeStamp);
        dbTester = initializeDatabase(dataSource);
        // run the job
        jobParameters = new JobParameters();
        JobExecution jobExecution = launcher.run(job, jobParameters);
        assertEquals(ExitStatus.FINISHED, jobExecution.getExitStatus());
        dbTester.onTearDown();
        // get synchronized invoices
        List<Invoice> invoices = template.query("SELECT * FROM bills WHERE synchronized >= '" + nowTimeStamp + "'",
                new RowMapper() {
                    public Object mapRow(ResultSet arg0, int arg1) throws SQLException {
                        Invoice invoice = new Invoice();
                        invoice.setId(arg0.getLong(InvoiceRowMapper.ID_COLUMN));
                        return invoice;
                    }
                });
        for (Invoice invoice : invoices) {
            log.debug(invoice.getId());
        }
        assertEquals(3, invoices.size());
    }
    public static IDatabaseTester initializeDatabase(DataSource dataSource) throws Exception {
        DbUtils.initializeDatabase(dataSource, new ClassPathResource("conf/init-hsqldb.sql"));
        return DbUtils.createAndInitializeDatabaseTester(dataSource, new ClassPathResource(
                "data/20081226_db_invoices.xml"));
    }

}
