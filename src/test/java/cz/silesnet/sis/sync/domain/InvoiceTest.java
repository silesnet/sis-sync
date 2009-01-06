package cz.silesnet.sis.sync.domain;

import static org.junit.Assert.*;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.joda.time.DateTime;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import cz.silesnet.sis.sync.domain.Invoice.Item;

public class InvoiceTest {

    private static Log log = LogFactory.getLog(InvoiceTest.class);

    private static final float AMOUNT = 1.025F;
    private static final int PRICE = 10;
    private static final float NET = 10.25F;
    private static final int VAT_RATE = 19;
    private static final float VAT = 1.95F;
    private static final float BRT = 12.20F;
    private static final float ROUNDING = -0.20F;
    private static final float PRECISION = 0.00F;

    Invoice invoice;

    @Before
    public void setUp() {
        invoice = new Invoice();
        invoice.setVatRate(VAT_RATE);
        invoice.new Item("Item 1", AMOUNT, PRICE);
    }

    @After
    public void tearDown() {
        invoice = null;
    }

    @Test
    public void testCalculateVat() {
        assertEquals(VAT, invoice.calculateVat(NET), PRECISION);
    }

    @Test
    public void testCalculateBrt() {
        assertEquals(BRT, invoice.calculateBrt(NET), PRECISION);
    }

    @Test
    public void testCalculateRounding() {
        assertEquals(ROUNDING, invoice.calculateRounding(NET), PRECISION);
    }

    @Test
    public void testInvoiceNet() throws Exception {
        invoice.new Item("Item 2", AMOUNT, PRICE);
        assertEquals(2 * NET, invoice.getNet(), PRECISION);
    }

    @Test
    public void testInvoiceAmounts() throws Exception {
        assertEquals(NET, invoice.getNet(), PRECISION);
        assertEquals(invoice.calculateVat(NET), invoice.getVat(), PRECISION);
        assertEquals(invoice.calculateBrt(NET), invoice.getBrt(), PRECISION);
        assertEquals(invoice.calculateRounding(NET), invoice.getRounding(), PRECISION);
    }

    @Test
    public void testInvoiceItemAmounts() throws Exception {
        Item item = invoice.getItems().get(0);
        assertEquals(NET, item.getNet(), PRECISION);
        assertEquals(invoice.calculateVat(NET), item.getVat(), PRECISION);
        assertEquals(invoice.calculateBrt(NET), item.getBrt(), PRECISION);
    }

    @Test
    public void testGetTextWithPeriod() throws Exception {
        invoice.setPeriodFrom(new DateTime("2009-01-01"));
        invoice.setPeriodTo(new DateTime("2009-01-31"));
        String text = invoice.getText();
        assertEquals(Invoice.INVOICE_TEXT + " " + Invoice.INVOICE_TEXT_PERIOD + " 01.01.2009-31.01.2009:", text);
        log.debug(text);
    }

    @Test
    public void testGetTextWithoutPeriod() throws Exception {
        String text = invoice.getText();
        assertEquals(Invoice.INVOICE_TEXT + ":", text);
        log.debug(text);
    }

}
