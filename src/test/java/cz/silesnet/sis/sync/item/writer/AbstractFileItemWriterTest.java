package cz.silesnet.sis.sync.item.writer;

import static org.junit.Assert.*;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.core.io.FileSystemResource;

import cz.silesnet.sis.sync.item.writer.AbstractFileItemWriter;

public class AbstractFileItemWriterTest {

    private static final String RESOURCE_NAME = "target/abstract_file_itemwriter.TEMP.txt";
    private static final String ITEM = "ITEM LINE";
    private static final String HEADER = "HEADER";
    private static final String TRAILER = "TRAILER";
    private AbstractFileItemWriter writer;
    private boolean itemWritten;

    @Before
    public void setUp() throws Exception {
        writer = new AbstractFileItemWriter() {

            @Override
            protected String headerToString() {
                return HEADER;
            }

            @Override
            protected void itemWritten(Object item) {
                itemWritten = true;
            }

            @Override
            protected String trailerToString() {
                return TRAILER;
            }

            @Override
            protected String itemToString(Object item) {
                return (String) item;
            }

        };
        writer.setResource(new FileSystemResource(RESOURCE_NAME));
        itemWritten = false;
    }

    @After
    public void tearDown() throws Exception {
        writer = null;
    }

    @Test
    public void testWriteNulls() throws Exception {
        writer = new AbstractFileItemWriter() {

            @Override
            protected String itemToString(Object item) {
                return null;
            }

            @Override
            protected String headerToString() {
                return null;
            }

            @Override
            protected String trailerToString() {
                return null;
            }

        };
        writer.setResource(new FileSystemResource(RESOURCE_NAME));
        writer.open(null);
        writer.write(null);
        writer.close(null);
        File result = (new FileSystemResource(RESOURCE_NAME).getFile());
        BufferedReader reader = new BufferedReader(new FileReader(result));
        String line = reader.readLine();
        assertTrue(result.exists());
        assertNull(line);
    }

    @Test(expected = IllegalStateException.class)
    public void testFailWhenNewResourceOnOpenFile() {
        writer.open(null);
        writer.setResource(null);
    }

    @Test
    public void testNewResourceOnClosedFile() {
        writer.open(null);
        writer.close(null);
        writer.setResource(null);
    }

    @Test
    public void testWriteItem() throws Exception {
        writer.open(null);
        writer.write(ITEM);
        writer.close(null);
        File result = (new FileSystemResource(RESOURCE_NAME).getFile());
        BufferedReader reader = new BufferedReader(new FileReader(result));
        String headerLine = reader.readLine();
        String itemLine = reader.readLine();
        String trailerLine = reader.readLine();
        String afterTrailer = reader.readLine();
        reader.close();
        assertTrue(itemWritten);
        assertEquals(HEADER, headerLine);
        assertEquals(ITEM, itemLine);
        assertEquals(TRAILER, trailerLine);
        assertNull(afterTrailer);
    }
}
