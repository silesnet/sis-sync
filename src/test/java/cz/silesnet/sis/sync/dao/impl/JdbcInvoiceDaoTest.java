package cz.silesnet.sis.sync.dao.impl;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.math.BigDecimal;

import javax.sql.DataSource;

import org.dbunit.DataSourceDatabaseTester;
import org.dbunit.IDatabaseTester;
import org.dbunit.dataset.DataSetException;
import org.dbunit.dataset.IDataSet;
import org.dbunit.dataset.xml.FlatXmlDataSet;
import org.junit.Test;
import org.springframework.core.io.ClassPathResource;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.AbstractDependencyInjectionSpringContextTests;

import cz.silesnet.sis.sync.domain.Invoice;

public class JdbcInvoiceDaoTest extends AbstractDependencyInjectionSpringContextTests {

    private static final long ID = 5L;
    private JdbcInvoiceDao dao;
    private JdbcTemplate template;
    private DataSource dataSource;

    public void setTemplate(JdbcTemplate template) {
        this.template = template;
    }

    public void setDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    protected String[] getConfigLocations() {
        return new String[] { "classpath:jobs/sisInvoiceJob.xml" };
    }

    @Override
    protected void prepareTestInstance() throws Exception {
        super.prepareTestInstance();
        dao = new JdbcInvoiceDao();
        dao.setTemplate(template);
        initializeDatabase();
    }

    @Test
    public void testFind() {
        Invoice invoice = dao.find(ID);
        assertEquals(ID, invoice.getId());
        assertEquals("200800005", invoice.getNumber());
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

    private void initializeDatabase() throws IOException, FileNotFoundException, DataSetException, Exception {
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
        // import invoices initial data
        IDatabaseTester databaseTester = new DataSourceDatabaseTester(dataSource);
        IDataSet dataSet = new FlatXmlDataSet((new ClassPathResource("data/20081226_db_invoices.xml")).getFile());
        databaseTester.setDataSet(dataSet);
        databaseTester.onSetup();
    }

}
