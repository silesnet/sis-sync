package cz.silesnet.sis.sync.item.reader;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import cz.silesnet.sis.sync.domain.InvoiceResult;

public class SpsInvoiceItemReaderTest {

  private SpsInvoiceItemReader reader;
  private InvoiceResult result;

  private static final int INVOICE_ID = 1234;
  private static final int SPS_INVOICE_ID = 1235;
  private static final String INVOICE_NUMBER = "123456789";
  private static final String[] lines = new String[]{"header",
      "<rdc:id>" + SPS_INVOICE_ID + "</rdc:id>", "<rdc:number>" + INVOICE_NUMBER + "</rdc:number>",
      "trailer"};

  @Before
  public void setUp() throws Exception {
    reader = new SpsInvoiceItemReader();
    result = (InvoiceResult) reader.mapLines(INVOICE_ID, lines);
  }

  @After
  public void tearDown() throws Exception {
    result = null;
    reader = null;
  }

  @Test
  public final void testInvoiceId() {
    assertEquals(INVOICE_ID, result.getSisId());
  }

  @Test
  public final void testSpsInvoiceId() {
    assertEquals(SPS_INVOICE_ID, result.getSpsId());
  }

  @Test
  public final void testInvoiceNumber() {
    assertEquals(INVOICE_NUMBER, result.getNumber());
  }

}
