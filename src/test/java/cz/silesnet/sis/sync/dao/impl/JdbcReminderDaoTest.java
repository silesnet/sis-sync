package cz.silesnet.sis.sync.dao.impl;

import javax.sql.DataSource;

import org.dbunit.IDatabaseTester;
import org.junit.Test;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.AbstractDependencyInjectionSpringContextTests;

import cz.silesnet.sis.sync.SpsRemindersFunctionalTest;

public class JdbcReminderDaoTest extends AbstractDependencyInjectionSpringContextTests {
    // private static final long ID = 4L;
    private JdbcReminderDao dao;
    private JdbcTemplate template;
    private DataSource dataSource;
    private IDatabaseTester dbTester;

    @Override
    protected String[] getConfigLocations() {
        return new String[] { "classpath:spsReminderJob.xml" };
    }

    @Override
    protected void onSetUp() throws Exception {
        super.onSetUp();
        dao = new JdbcReminderDao();
        template = (JdbcTemplate) applicationContext.getBean("spsJdbcTemplate");
        dao.setJdbcTemplate(template);
        dataSource = (DataSource) applicationContext.getBean("dataSource");
        dbTester = SpsRemindersFunctionalTest.initializeDatabase(dataSource);
    }

    @Override
    protected void onTearDown() throws Exception {
        super.onTearDown();
        dbTester.onTearDown();
    }

    @Test
    public void testGet() {
        fail("Not yet implemented");
    }

}
