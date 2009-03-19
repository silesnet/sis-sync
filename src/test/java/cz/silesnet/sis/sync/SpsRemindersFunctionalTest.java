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
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.core.io.ClassPathResource;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.test.AbstractDependencyInjectionSpringContextTests;

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
        return new String[] { "classpath:spsReminderJob.xml" };
    }

    @SuppressWarnings("unchecked")
    @Test
    public void testRemindersSelect() throws Exception {
        dataSource = (DataSource) applicationContext.getBean("dataSource");
        dbTester = initializeDatabase(dataSource);
        // jobParameters = new JobParameters();
        // JobExecution jobExecution = launcher.run(job, jobParameters);
        // assertEquals(ExitStatus.FINISHED, jobExecution.getExitStatus());
        dbTester.onTearDown();
        // test synchronized customers
        JdbcTemplate template = new JdbcTemplate(dataSource);
        String sql = "SELECT DISTINCT AD.ID FROM AD INNER JOIN FA ON AD.ID = FA.RefAD WHERE DATEDIFF('dd', FA.DatSplat, '2009-02-25') >= AD.ADSplat AND KcLikv >= 5 ORDER BY AD.ID";
        List<String> customers = template.query(sql, new RowMapper() {

            public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
                return rs.getString("ID");
            }
        });
        for (String number : customers) {
            log.debug(number);
        }
        assertEquals(3, customers.size());
    }

    public static IDatabaseTester initializeDatabase(DataSource dataSource) throws Exception {
        DbUtils.initializeDatabase(dataSource, new ClassPathResource("init-hsqldb-sps.sql"));
        return DbUtils.createAndInitializeDatabaseTester(dataSource, new ClassPathResource(
                "data/20090310_db_sps_reminders.xml"));
    }

}
