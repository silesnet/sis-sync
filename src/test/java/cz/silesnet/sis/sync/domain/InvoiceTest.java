package cz.silesnet.sis.sync.domain;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.math.BigDecimal;

public class InvoiceTest {

  private static final float AMOUNT = 1.025F;
  private static final int PRICE = 10;
  private static final int VAT_PCT = 21;

  Invoice invoice;

  @Before
  public void setUp() {
    invoice = new Invoice();
    invoice.new Item("Item 1", AMOUNT, PRICE, VAT_PCT);
  }

  @Test
  public void testItems() throws Exception {
    assertEquals(1, invoice.getItems().size());
    assertNotNull(invoice.getItems().get(0).getCharge());
    assertNotNull(invoice.getCharge());
    assertEquals(invoice.getItems().get(0).getCharge(), invoice.getCharge());
    assertEquals(BigDecimal.valueOf(992, 2), invoice.getCharge().getNet());
    assertEquals(BigDecimal.valueOf(208, 2), invoice.getCharge().getVat());
    assertEquals(BigDecimal.valueOf(1200, 2), invoice.getCharge().getBrt());
  }

  @After
  public void tearDown() {
    invoice = null;
  }

}
