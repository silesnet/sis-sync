package cz.silesnet.sis.sync.domain;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

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
  }

  @After
  public void tearDown() {
    invoice = null;
  }

}
