package cz.silesnet.sis.sync.dao.impl;

import java.math.BigDecimal;

import javax.sql.DataSource;

import org.dbunit.IDatabaseTester;
import org.joda.time.LocalDate;
import org.junit.Test;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.AbstractDependencyInjectionSpringContextTests;

import cz.silesnet.sis.sync.SpsRemindersFunctionalTest;
import cz.silesnet.sis.sync.domain.Reminder;

public class JdbcReminderDaoTest extends AbstractDependencyInjectionSpringContextTests {
    private static final long ID = 4L;
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
        // adjust DAO to work with HSQLDB for testing
        dao.setDayPartName("'dd'");
        dao.setCurrentDateFunction("'2009-02-25'"); // CURRENT_DATE for HSQLDB
        dao.setMinimalDueAmount(5);
        template = (JdbcTemplate) applicationContext.getBean("spsJdbcTemplate");
        dao.setJdbcTemplate(template);
        dataSource = (DataSource) applicationContext.getBean("spsDataSource");
        dbTester = SpsRemindersFunctionalTest.initializeDatabase(dataSource);
    }

    @Override
    protected void onTearDown() throws Exception {
        super.onTearDown();
        dbTester.onTearDown();
    }

    @Test
    public void testFindDelayedAll() {
        Reminder reminder = dao.find(ID);
        assertNotNull(reminder);
        assertEquals(ID, reminder.getCustomer().getId());
        assertEquals("Test Customer4", reminder.getCustomer().getName());
        assertEquals("contact@customer4.cz", reminder.getCustomer().getEmail());
        assertEquals(10, reminder.getCustomer().getGraceDays());
        assertEquals(2, reminder.getInvoices().size());
        assertEquals(6, reminder.getInvoices().get(0).getId());
        assertEquals(7, reminder.getInvoices().get(1).getId());
        assertEquals("200900007", reminder.getInvoices().get(1).getNumber());
        assertEquals("200900007", reminder.getInvoices().get(1).getReferenceNumber());
        assertEquals(new LocalDate("2009-02-15"), reminder.getInvoices().get(1).getDueDate());
        assertEquals(new BigDecimal("120"), reminder.getInvoices().get(1).getTotalAmount());
        assertEquals(new BigDecimal("100"), reminder.getInvoices().get(1).getDueAmount());
        assertEquals(ID, reminder.getInvoices().get(1).getCustomerId());
    }

    public void testFindDelayedOnly() throws Exception {
        Reminder reminder = dao.find(3);
        assertEquals(1, reminder.getInvoices().size());
        assertEquals(5, reminder.getInvoices().get(0).getId());
    }

    public void testFindDelayedNone() throws Exception {
        Reminder reminder = dao.find(1);
        assertEquals(0, reminder.getInvoices().size());
    }

    public void testFindDelayedOneOnly() throws Exception {
        Reminder reminder = dao.find(2);
        assertEquals(1, reminder.getInvoices().size());
        assertEquals(3, reminder.getInvoices().get(0).getId());
    }

    public void testFindNotDelayedGracePeriod() throws Exception {
        Reminder reminder = dao.find(5);
        assertEquals(0, reminder.getInvoices().size());
    }

    @Test
    public void testComposeAddressLine() {
        assertEquals("street 3, 123 45 town", dao.composeAddressLine("street 3", "123 45", "town"));
    }

    @Test
    public void testComposeAddressLineEmpty() {
        assertEquals("", dao.composeAddressLine(null, null, null));
    }

    @Test
    public void testComposeAddressLineStreetOnly() {
        assertEquals("street", dao.composeAddressLine("street", "", ""));
    }
}
