package cz.silesnet.sis.sync.item.writer;

import static org.junit.Assert.*;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.joda.time.DateTime;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import cz.silesnet.sis.sync.domain.Invoice;

public class SisInvoiceItemWriterStaticMethodsTest {

    private static Log log = LogFactory.getLog(SisInvoiceItemWriterStaticMethodsTest.class);

    private Invoice invoice;

    @Before
    public void setUp() {
        invoice = new Invoice();
    }
    @After
    public void tearDown() {
        invoice = null;
    }

    @Test
    public void testGetTextWithPeriod() throws Exception {
        invoice.setPeriodFrom(new DateTime("2009-01-01"));
        invoice.setPeriodTo(new DateTime("2009-01-31"));
        String text = SisInvoiceItemWriter.getInvoiceText(invoice);
        assertEquals(SisInvoiceItemWriter.INVOICE_TEXT + " " + SisInvoiceItemWriter.INVOICE_TEXT_PERIOD
                + " 01.01.2009-31.01.2009:", text);
        log.debug(text);
    }

    @Test
    public void testGetTextWithoutPeriod() throws Exception {
        String text = SisInvoiceItemWriter.getInvoiceText(invoice);
        assertEquals(SisInvoiceItemWriter.INVOICE_TEXT + ":", text);
        log.debug(text);
    }

    @Test
    public void testAccountingConnectivity() throws Exception {
        invoice.new Item("WIRELESSdirect", 1, 10);
        assertEquals(SisInvoiceItemWriter.ACCOUNTING_CONNECTIVITY, SisInvoiceItemWriter.getItemAccounting(invoice
                .getItems().get(0)));
    }
    @Test
    public void testAccountingWebhosting() throws Exception {
        invoice.new Item("WEBhosting", 1, 10);
        assertEquals(SisInvoiceItemWriter.ACCOUNTING_WEBHOSTING, SisInvoiceItemWriter.getItemAccounting(invoice
                .getItems().get(0)));
    }

    @Test
    public void testAccountingServerHousing() throws Exception {
        invoice.new Item("SERVERhousing", 1, 10);
        assertEquals(SisInvoiceItemWriter.ACCOUNTING_SERVERHOUSING, SisInvoiceItemWriter.getItemAccounting(invoice
                .getItems().get(0)));
    }

    @Test
    public void testAccountingActivation() throws Exception {
        invoice.new Item("Aktivace", 1, 10);
        assertEquals(SisInvoiceItemWriter.ACCOUNTING_ACTIVATION, SisInvoiceItemWriter.getItemAccounting(invoice
                .getItems().get(0)));
    }

    @Test
    public void testAccountingDefault() throws Exception {
        invoice.new Item("Default", 1, 10);
        assertEquals(SisInvoiceItemWriter.ACCOUNTING_DEFAULT, SisInvoiceItemWriter.getItemAccounting(invoice.getItems()
                .get(0)));
    }
}
