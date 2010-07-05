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
    public void testAccountingConnectivityKbps() throws Exception {
        invoice.new Item("Connectivity 1234/456 kbps", 1, 10);
        String accounting = SisInvoiceItemWriter.getItemAccounting(invoice.getItems().get(0));
        assertEquals(SisInvoiceItemWriter.ACCOUNTING_CONNECTIVITY, accounting);
        log.debug(accounting);
    }
    @Test
    public void testAccountingConnectivityFup() throws Exception {
        invoice.new Item("Connectivity 1234/456 kbps FUP", 1, 10);
        String accounting = SisInvoiceItemWriter.getItemAccounting(invoice.getItems().get(0));
        assertEquals(SisInvoiceItemWriter.ACCOUNTING_CONNECTIVITY, accounting);
        log.debug(accounting);
    }

    @Test
    public void testAccountingConnectivityMbps() throws Exception {
        invoice.new Item("Connectivity 1234/456 Mbps", 1, 10);
        String accounting = SisInvoiceItemWriter.getItemAccounting(invoice.getItems().get(0));
        assertEquals(SisInvoiceItemWriter.ACCOUNTING_CONNECTIVITY, accounting);
        log.debug(accounting);
    }
    @Test
    public void testAccountingConnectivityMbpsFup() throws Exception {
        invoice.new Item("Connectivity 1234/456 Mbps FUP", 1, 10);
        String accounting = SisInvoiceItemWriter.getItemAccounting(invoice.getItems().get(0));
        assertEquals(SisInvoiceItemWriter.ACCOUNTING_CONNECTIVITY, accounting);
        log.debug(accounting);
    }
    @Test
    public void testAccountingWebhosting() throws Exception {
        invoice.new Item("WEBhosting", 1, 10);
        String accounting = SisInvoiceItemWriter.getItemAccounting(invoice.getItems().get(0));
        assertEquals(SisInvoiceItemWriter.ACCOUNTING_WEBHOSTING, accounting);
        log.debug(accounting);
    }

    @Test
    public void testAccountingServerHousing() throws Exception {
        invoice.new Item("SERVERhousing", 1, 10);
        String accounting = SisInvoiceItemWriter.getItemAccounting(invoice.getItems().get(0));
        assertEquals(SisInvoiceItemWriter.ACCOUNTING_SERVERHOUSING, accounting);
        log.debug(accounting);
    }

    @Test
    public void testAccountingActivation() throws Exception {
        invoice.new Item("Aktivace", 1, 10);
        String accounting = SisInvoiceItemWriter.getItemAccounting(invoice.getItems().get(0));
        assertEquals(SisInvoiceItemWriter.ACCOUNTING_ACTIVATION, accounting);
        log.debug(accounting);
    }

    @Test
    public void testAccountingDefault() throws Exception {
        invoice.new Item("Default", 1, 10);
        String accounting = SisInvoiceItemWriter.getItemAccounting(invoice.getItems().get(0));
        assertEquals(SisInvoiceItemWriter.ACCOUNTING_DEFAULT, accounting);
        log.debug(accounting);
    }
}
