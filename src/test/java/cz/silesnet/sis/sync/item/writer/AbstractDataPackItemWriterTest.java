package cz.silesnet.sis.sync.item.writer;

import static org.junit.Assert.*;

import java.text.ParseException;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import cz.silesnet.sis.sync.domain.ItemIdentity;

public class AbstractDataPackItemWriterTest {

  private static final Log log = LogFactory.getLog(AbstractDataPackItemWriterTest.class);
  private static final int MAX_ID_LENGTH = 64;
  private static final long ID = 1234L;
  private static final long ID2 = 1235L;
  private static final String ICO = "12345678";
  private static final String NAME_SPACE_1 = "NAME SPACE 1";
  private static final String NAME_SPACE_2 = "NAME SPACE 2";
  private static final long ITEMS_WRITTEN = 5;
  private static final String ITEM = "<item />";
  private AbstractDataPackItemWriter<String> itemWriter;

  @Before
  public void setUp() throws Exception {
    itemWriter = new AbstractDataPackItemWriter<String>() {

      @Override
      protected String[] dataPackItemLines(String item) {
        return new String[]{item};
      }

      @Override
      protected String[] nameSpaceLines() {
        return new String[]{NAME_SPACE_1, NAME_SPACE_2};
      }

      @Override
      protected long getItemsWritten() {
        return ITEMS_WRITTEN;
      }

    };
    itemWriter.setIco(ICO);
  }

  @After
  public void tearDown() throws Exception {
    itemWriter = null;
  }

  @Test
  public final void testHeaderLines() {
    String[] headerLines = itemWriter.headerLines();
    log.debug(headerLines[0]);
    assertEquals("<?xml version=\"1.0\" encoding=\"" + AbstractDataPackItemWriter.XML_ENCODING
        + "\"?>", headerLines[0]);
    log.debug(headerLines[1]);
    Pattern pattern = Pattern
        .compile("<dat:dataPack id=\"(.+?)\" ico=\"(.+?)\" application=\"(.+?)\" version=\"(.+?)\" note=\"(.+?)\"");
    Matcher matcher = pattern.matcher(headerLines[1]);
    assertTrue(matcher.matches());
    assertEquals(itemWriter.getDataPackId(), matcher.group(1));
    assertEquals(ICO, matcher.group(2));
    assertEquals(AbstractDataPackItemWriter.DATA_PACK_APPLICATION, matcher.group(3));
    assertEquals(AbstractDataPackItemWriter.DATA_PACK_VERSION, matcher.group(4));
    assertEquals(AbstractDataPackItemWriter.DATA_PACK_NOTE, matcher.group(5));
    log.debug(headerLines[2]);
    assertEquals("xmlns:dat=\"http://www.stormware.cz/schema/data.xsd\"", headerLines[2]);
    log.debug(headerLines[3]);
    assertEquals("xmlns:typ=\"http://www.stormware.cz/schema/type.xsd\"", headerLines[3]);
    log.debug(headerLines[4]);
    assertEquals(NAME_SPACE_1, headerLines[4]);
    log.debug(headerLines[5]);
    assertEquals(NAME_SPACE_2, headerLines[5]);
    log.debug(headerLines[6]);
    assertEquals(">", headerLines[6]);
    assertEquals(7, headerLines.length);
  }

  private class Item implements ItemIdentity {
    private final long id;

    public Item(long id) {
      super();
      this.id = id;
    }

    @Override
    public long getId() {
      return id;
    }

    @Override
    public String toString() {
      return ITEM;
    }

    @Override
    public int hashCode() {
      return new Long(id).hashCode();
    }

  }

  // initialize data pack id
  private static AbstractDataPackItemWriter<Item> createItemWriter() {
    return new AbstractDataPackItemWriter<Item>() {

      @Override
      protected String[] dataPackItemLines(Item item) {
        return new String[]{item.toString()};
      }

      @Override
      protected String[] nameSpaceLines() {
        return new String[]{NAME_SPACE_1, NAME_SPACE_2};
      }

      @Override
      protected long getItemsWritten() {
        return ITEMS_WRITTEN;
      }
    };
  }

  @Test
  public final void testItemLines() {
    // initialize data pack id
    AbstractDataPackItemWriter<Item> itemWriter = createItemWriter();

    Item item = new Item(ID);

    itemWriter.headerLines();
    String[] itemLines = itemWriter.itemLines(item);
    assertEquals("<dat:dataPackItem id=\"" + itemWriter.getDataPackItemId(item) + "\" version=\""
        + AbstractDataPackItemWriter.DATA_PACK_ITEM_VERSION + "\">", itemLines[0]);
    log.debug(itemLines[0]);
    assertEquals(ITEM, itemLines[1]);
    log.debug(itemLines[1]);
    assertEquals("</dat:dataPackItem>", itemLines[2]);
    log.debug(itemLines[2]);
    assertEquals(3, itemLines.length);
  }

  @Test
  public final void testTrailerLines() {
    String[] trailerLines = itemWriter.trailerLines();
    assertEquals("</dat:dataPack>", trailerLines[0]);
    log.debug(trailerLines[0]);
    assertEquals(1, trailerLines.length);
  }

  @Test
  public final void testGetDataPackId() throws ParseException {
    long before = (new Date()).getTime();
    // dataPackId is set afresh always while headerLines are read
    itemWriter.headerLines();
    String dataPackId = itemWriter.getDataPackId();
    log.debug(dataPackId);
    Date dataPackStamp = AbstractDataPackItemWriter.DATA_PACK_ID_DATE_FORMAT.parse(dataPackId);
    long dataPackIstant = dataPackStamp.getTime();
    long after = (new Date()).getTime();
    assertTrue(before <= dataPackIstant);
    assertTrue(dataPackIstant <= after);
    assertTrue(dataPackId.length() <= MAX_ID_LENGTH);
  }

  @Test
  public final void testGetDataPackItemIdHashCode() {
    AbstractDataPackItemWriter<Item> itemWriter = createItemWriter();
    itemWriter.headerLines();
    String dataPackId = itemWriter.getDataPackId();
    String itemsWritten = itemWriter.getItemsWrittenString();
    String dataPackItemId = itemWriter.getDataPackItemId(new Item(ID));
    log.debug(dataPackItemId);
    assertEquals(dataPackId + "_" + itemsWritten + "_" + ID, dataPackItemId);
    assertTrue(dataPackItemId.length() <= MAX_ID_LENGTH);
  }

  @Test
  public final void testGetDataPackItemIdNegativeHashCode() {
    AbstractDataPackItemWriter<Item> itemWriter = createItemWriter();
    itemWriter.headerLines();
    String dataPackId = itemWriter.getDataPackId();
    String itemsWritten = itemWriter.getItemsWrittenString();
    String dataPackItemId = itemWriter.getDataPackItemId(new Item(-ID));
    log.debug(dataPackItemId);
    assertEquals(dataPackId + "_" + itemsWritten + "_" + ID, dataPackItemId);
    assertTrue(dataPackItemId.length() <= MAX_ID_LENGTH);
  }

  @Test
  public final void testGetDataPackItemIdIdentity() {
    AbstractDataPackItemWriter<Item> itemWriter = createItemWriter();
    itemWriter.headerLines();
    String dataPackId = itemWriter.getDataPackId();
    String itemsWritten = itemWriter.getItemsWrittenString();
    String dataPackItemId = itemWriter.getDataPackItemId(new Item(ID2));
    log.debug(dataPackItemId);
    assertEquals(dataPackId + "_" + itemsWritten + "_" + ID2, dataPackItemId);
    assertTrue(dataPackItemId.length() <= MAX_ID_LENGTH);
  }

  @Test
  public void testGetItemsWrittenString() {
    String itemsWrittenString = itemWriter.getItemsWrittenString();
    assertEquals(Long.valueOf(itemsWrittenString), Long.valueOf(ITEMS_WRITTEN));
    assertEquals(AbstractDataPackItemWriter.ITEMS_WRITTEN_STRING_LENGHT, itemsWrittenString
        .length());
  }

  @Test
  public void testElValue() throws Exception {
    assertEquals("<name>value</name>", AbstractDataPackItemWriter.elValue("name", "value"));
  }

  @Test
  public void testElValueFloat() throws Exception {
    assertEquals("<name>10.0</name>", AbstractDataPackItemWriter.elValue("name", 10.0F));
  }

  @Test
  public void testElValueLong() throws Exception {
    assertEquals("<name>10</name>", AbstractDataPackItemWriter.elValue("name", 10L));
  }

  @Test
  public void testElValueEscape() throws Exception {
    assertEquals("<name>a&amp;b</name>", AbstractDataPackItemWriter.elValue("name", "a&b"));
  }

  @Test
  public void testElValueNoEscape() throws Exception {
    assertEquals("<name>\u0161</name>", AbstractDataPackItemWriter.elValue("name", "\u0161"));
  }

  @Test
  public void testElValueTrim() throws Exception {
    assertEquals("<name>first last</name>", AbstractDataPackItemWriter.elValue("name",
        "\t\t  first last  \t\n"));
  }

  @Test
  public void testElValueRemoveDoubleSpaces() throws Exception {
    assertEquals("<name>a b c d e</name>", AbstractDataPackItemWriter.elValue("name",
        "a b  c   d    e"));
  }

  @Test
  public void testElValueNull() throws Exception {
    assertEquals("<name />", AbstractDataPackItemWriter.elValue("name", null));
  }

  @Test
  public void testElValueEmpty() throws Exception {
    assertEquals("<name />", AbstractDataPackItemWriter.elValue("name", ""));
  }

  @Test
  public void testElBeg() throws Exception {
    assertEquals("<name>", AbstractDataPackItemWriter.elBeg("name"));
  }

  @Test
  public void testElEnd() throws Exception {
    assertEquals("</name>", AbstractDataPackItemWriter.elEnd("name"));
  }

  @Test
  public void testXmlEscape() {
    assertEquals("a&quot;&lt;&amp;&gt;&apos;z", AbstractDataPackItemWriter.escapeXml("a\"<&>'z"));
  }
}
