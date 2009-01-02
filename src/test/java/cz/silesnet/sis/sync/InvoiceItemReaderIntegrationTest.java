package cz.silesnet.sis.sync;

import javax.sql.DataSource;

import org.dbunit.IDatabaseTester;
import org.junit.Test;
import org.springframework.batch.item.ExecutionContext;
import org.springframework.batch.item.database.DrivingQueryItemReader;
import org.springframework.test.AbstractDependencyInjectionSpringContextTests;

import cz.silesnet.sis.sync.domain.Invoice;

public class InvoiceItemReaderIntegrationTest extends AbstractDependencyInjectionSpringContextTests {

    private IDatabaseTester dbTester;
    private DataSource dataSource;
    private DrivingQueryItemReader itemReader;
    private ExecutionContext executionContext;

    public void setDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public void setInvoiceItemReader(DrivingQueryItemReader invoiceItemReader) {
        this.itemReader = invoiceItemReader;
    }

    @Override
    protected String[] getConfigLocations() {
        return new String[] { "classpath:sisInvoiceJob.xml" };
    }

    @Override
    protected void onSetUp() throws Exception {
        super.onSetUp();
        dbTester = SisInvoiceFunctionalTest.initializeDatabase(dataSource);
        executionContext = new ExecutionContext();
        itemReader.open(executionContext);
    }

    @Override
    protected void onTearDown() throws Exception {
        super.onTearDown();
        itemReader.close(executionContext);
        dbTester.onTearDown();
    }

    @Test
    public void testReader() {
        assertEquals(3, readInvoice().getId());
        assertEquals(4, readInvoice().getId());
        assertEquals(5, readInvoice().getId());
        assertNull(readInvoice());
    }

    private Invoice readInvoice() {
        return (Invoice) itemReader.read();
    }
}
