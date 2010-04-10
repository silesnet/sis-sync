package cz.silesnet.sis.sync.item.writer;

import static org.junit.Assert.*;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.batch.item.ExecutionContext;
import org.springframework.core.io.FileSystemResource;

public class AbstractHeaderTrailerFileItemWriterTest {
  private static final String RESOURCE_NAME = "target/header_trailer_itemwriter.TEMP.txt";
  private static final String ITEM = "ITEM";
  private static final String HEADER = "HEADER";
  private static final String TRAILER = "TRAILER";
  private AbstractHeaderTrailerFileItemWriter writer;
  private ExecutionContext executionContext;

  @Before
  public void setUp() throws Exception {
    writer = new AbstractHeaderTrailerFileItemWriter() {

      @Override
      protected String[] headerLines() {
        return new String[]{HEADER};
      }

      @Override
      protected String[] trailerLines() {
        return new String[]{TRAILER};
      }

      @Override
      protected String[] itemLines(Object item) {
        return new String[]{item.toString()};
      }

      @Override
      public void write(List<? extends String> items) throws Exception {
        // TODO Auto-generated method stub

      }

    };
    writer.setResource(new FileSystemResource(RESOURCE_NAME));
    executionContext = new ExecutionContext();
  }

  @After
  public void tearDown() throws Exception {
    writer = null;
    executionContext = null;
  }

  @Test
  public void testWriteItem() throws Exception {
    writer.open(executionContext);
    writer.write(ITEM);
    writer.close(executionContext);
    // check results
    File result = (new FileSystemResource(RESOURCE_NAME).getFile());
    BufferedReader reader = new BufferedReader(new FileReader(result));
    assertEquals(1, writer.getItemsWritten());
    assertEquals(HEADER, reader.readLine());
    assertEquals(ITEM, reader.readLine());
    assertEquals(TRAILER, reader.readLine());
    assertNull(reader.readLine());
    reader.close();
  }

}
