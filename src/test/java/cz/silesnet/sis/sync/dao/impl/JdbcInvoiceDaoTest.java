package cz.silesnet.sis.sync.dao.impl;

import java.math.BigDecimal;

import javax.sql.DataSource;

import org.dbunit.IDatabaseTester;
import org.joda.time.LocalDate;
import org.junit.Test;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.AbstractDependencyInjectionSpringContextTests;

import cz.silesnet.sis.sync.SisInvoiceFunctionalTest;
import cz.silesnet.sis.sync.domain.Invoice;

public class JdbcInvoiceDaoTest extends AbstractDependencyInjectionSpringContextTests {

    private static final long ID = 5L;
    private JdbcInvoiceDao dao;
    private JdbcTemplate template;
    private DataSource dataSource;
    private IDatabaseTester dbTester;

    public void setTemplate(JdbcTemplate template) {
        this.template = template;
    }

    public void setDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    protected String[] getConfigLocations() {
        return new String[]{"classpath:sisInvoiceJob.xml"};
    }

    @Override
    protected void onSetUp() throws Exception {
        super.onSetUp();
        dao = new JdbcInvoiceDao();
        dao.setTemplate(template);
        dbTester = SisInvoiceFunctionalTest.initializeDatabase(dataSource);
    }

    @Override
    protected void onTearDown() throws Exception {
        super.onTearDown();
        dbTester.onTearDown();
    }

    @Test
    public void testFind() {
        Invoice invoice = dao.find(ID);
        assertEquals(ID, invoice.getId());
        assertEquals("200800005", invoice.getNumber());
        assertEquals(new LocalDate("2009-01-05"), invoice.getDate().toLocalDate());
        assertEquals(new LocalDate("2009-01-19"), invoice.getDueDate().toLocalDate());
        assertEquals(2, invoice.getInvoicingId());
        assertEquals("Old Test Customer3", invoice.getCustomerName());
        assertEquals(new LocalDate("2009-01-01"), invoice.getPeriodFrom().toLocalDate());
        assertEquals(new LocalDate("2009-01-31"), invoice.getPeriodTo().toLocalDate());
        assertEquals(19, invoice.getVatRate());
        assertEquals("#1234567895#", invoice.getHashCode());
        assertEquals(2, invoice.getItems().size());
        assertEquals(new BigDecimal("130.0"), BigDecimal.valueOf((double) invoice.getNet()));
        assertEquals("Connectivity 1", invoice.getItems().get(0).getText());
        assertEquals(2.0F, invoice.getItems().get(0).getAmount());
        assertEquals(60, invoice.getItems().get(0).getPrice());
        assertTrue(invoice.getItems().get(0).isDisplayUnit());
        assertEquals("Connectivity 3", invoice.getItems().get(1).getText());
        assertEquals(1.0F, invoice.getItems().get(1).getAmount());
        assertEquals(10, invoice.getItems().get(1).getPrice());
        assertFalse(invoice.getItems().get(1).isDisplayUnit());
        assertEquals(3, invoice.getCustomer().getId());
        assertEquals("1003", invoice.getCustomer().getSymbol());
    }

    @Test
    public void testFailWhenInvoiceDoesNotExist() {
        try {
            dao.find(0L);
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

}
