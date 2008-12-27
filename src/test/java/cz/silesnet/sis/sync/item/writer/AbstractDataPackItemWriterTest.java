package cz.silesnet.sis.sync.item.writer;

import static org.junit.Assert.*;

import java.text.ParseException;
import java.util.Date;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class AbstractDataPackItemWriterTest {

    private static final Log log = LogFactory.getLog(AbstractDataPackItemWriterTest.class);
    private static final int MAX_ID_LENGTH = 64;
    private static final long ID = 1234L;
    private static final String ICO = "12345678";
    private static final String NAME_SPACE_1 = "NAME SPACE 1";
    private static final String NAME_SPACE_2 = "NAME SPACE 2";
    protected static final long ITEMS_WRITTEN = 5;
    private AbstractDataPackItemWriter itemWriter;

    @Before
    public void setUp() throws Exception {
        itemWriter = new AbstractDataPackItemWriter() {

            @Override
            protected String[] dataPackItemLines(Object item) {
                return new String[] { item.toString() };
            }

            @Override
            protected String[] nameSpaceLines() {
                return new String[] { NAME_SPACE_1, NAME_SPACE_2 };
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
        fail("Not yet implemented");
    }

    @Test
    public final void testTrailerLines() {
        fail("Not yet implemented");
    }

    @Test
    public final void testItemLines() {
        fail("Not yet implemented");
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
    public final void testGetDataPackItemId() {
        itemWriter.headerLines();
        String dataPackId = itemWriter.getDataPackId();
        String itemsWritten = itemWriter.getItemsWrittenString();
        String dataPackItemId = itemWriter.getDataPackItemId(ID);
        log.debug(dataPackItemId);
        assertEquals(dataPackId + "_" + itemsWritten + "_" + ID, dataPackItemId);
        assertTrue(dataPackItemId.length() <= MAX_ID_LENGTH);
    }

    @Test
    public void testGetItemsWrittenString() {
        String itemsWrittenString = itemWriter.getItemsWrittenString();
        assertEquals(Long.valueOf(itemsWrittenString), Long.valueOf(ITEMS_WRITTEN));
        assertEquals(AbstractDataPackItemWriter.ITEMS_WRITTEN_LENGHT, itemsWrittenString.length());
    }
}
