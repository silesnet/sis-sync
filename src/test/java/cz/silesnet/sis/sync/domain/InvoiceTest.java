package cz.silesnet.sis.sync.domain;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import cz.silesnet.sis.sync.domain.Invoice.Item;

public class InvoiceTest {
    private static final float NET = 10.25F;
    private static final float VAT = 1.95F;
    private static final float BRT = 12.20F;
    private static final float ROUNDING = -0.20F;
    private static final float PRECISION = 0.00F;

    Invoice invoice;

    @Before
    public void setUp() {
        invoice = new Invoice();
        invoice.new Item("Item 1", NET);
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
        invoice.new Item("Item 2", NET);
        assertEquals(2 * NET, invoice.getNet(), PRECISION);
    }

    @Test
    public void testInvoiceAmounts() throws Exception {
        assertEquals(NET, invoice.getNet(), 0.0);
        assertEquals(invoice.calculateVat(NET), invoice.getVat(), PRECISION);
        assertEquals(invoice.calculateBrt(NET), invoice.getBrt(), PRECISION);
        assertEquals(invoice.calculateRounding(NET), invoice.getRounding(), PRECISION);
    }

    @Test
    public void testInvoiceItemAmounts() throws Exception {
        Item item = invoice.getItems().get(0);
        assertEquals(NET, item.getNet(), 0.0);
        assertEquals(invoice.calculateVat(NET), item.getVat(), 0.0);
        assertEquals(invoice.calculateBrt(NET), item.getBrt(), 0.0);
    }

}
