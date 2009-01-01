package cz.silesnet.sis.sync.dao.impl;

import java.math.BigDecimal;

import javax.sql.DataSource;

import org.dbunit.IDatabaseTester;
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
        assertEquals(3, invoice.getCustomerId());
        assertEquals(2, invoice.getItems().size());
        assertEquals(new BigDecimal("130.0"), BigDecimal.valueOf((double) invoice.getNet()));
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
