/*
 * Copyright 2008 the original author or authors.
 */
package cz.silesnet.sis.sync.item.reader;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.batch.item.ExecutionContext;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemStream;
import org.springframework.batch.item.ItemStreamException;
import org.springframework.batch.item.MarkFailedException;
import org.springframework.batch.item.NoWorkFoundException;
import org.springframework.batch.item.ParseException;
import org.springframework.batch.item.ResetFailedException;
import org.springframework.batch.item.UnexpectedInputException;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.mapping.DefaultFieldSet;
import org.springframework.batch.item.file.mapping.FieldSet;
import org.springframework.batch.item.file.mapping.FieldSetMapper;
import org.springframework.batch.item.file.transform.LineTokenizer;
import org.springframework.core.io.Resource;

/**
 * Abstract implementation of SPS response XML file reader. This implementation
 * reads sequence of <responsePackItem> elements extracting SIS item id form
 * element composite id.
 * 
 * @author sikorric
 * 
 */
public abstract class AbstractSpsResponseItemReader implements ItemReader, ItemStream {

  private static final Log log = LogFactory.getLog(AbstractSpsResponseItemReader.class);

  private static final Pattern ITEM_BEGIN_PATTERN = Pattern
      .compile("<rsp:responsePackItem.* id=\".+_\\d+_(\\d+)\".* state=\"(.+)\".*>");
  private static final Pattern ELEMENT_STATE_OK_PATTERN = Pattern.compile("<.+ state=\"ok\".*>");
  private static final String STATUS_OK = "ok";
  private static final String ITEM_END_LINE = "</rsp:responsePackItem>";
  private static final int UNDEFINED_ITEM_ID = -1;

  private final FlatFileItemReader itemReader;

  private boolean logImportErrors = true;

  public void setLogImportErrors(boolean logImportErrors) {
    this.logImportErrors = logImportErrors;
  }

  public final Object read() throws Exception, UnexpectedInputException, NoWorkFoundException,
      ParseException {
    List<String> itemLines = null;
    long itemId = UNDEFINED_ITEM_ID;
    boolean itemBeginFound = false;
    boolean itemEndFound = false;
    String line = null;
    while ((line = readTrimmed()) != null) {
      // find item beginning and store its id
      if (!itemBeginFound) {
        Matcher itemBeginMatcher = ITEM_BEGIN_PATTERN.matcher(line);
        if (itemBeginMatcher.matches()) {
          // found response item, get response id and status
          itemId = Long.valueOf(itemBeginMatcher.group(1));
          String itemStatus = itemBeginMatcher.group(2);
          if (!STATUS_OK.equalsIgnoreCase(itemStatus)) {
            // not imported item, continue searching for next one
            if (logImportErrors) {
              log.warn("Item import failed [" + itemId + "]: " + line);
            }
            continue;
          }
          // read ahead for contained element
          String containedLine = readTrimmed();
          if (containedLine == null)
            return null; // EOF
          if (!ELEMENT_STATE_OK_PATTERN.matcher(containedLine).matches()) {
            // element not imported, continue search for next item
            if (logImportErrors) {
              log.warn("Element import failed [" + itemId + "]" + containedLine);
            }
            continue;
          }
          itemLines = new ArrayList<String>();
          // add eagerly read line to item lines
          itemLines.add(containedLine);
          itemBeginFound = true;
          continue;
        }
      } else if (ITEM_END_LINE.equals(line)) {
        // whole item read, break the loop
        itemEndFound = true;
        break;
      } else {
        // store item line
        itemLines.add(line);
      }
    }
    return (itemBeginFound && itemEndFound) ? mapLines(itemId, itemLines
        .toArray(new String[itemLines.size()])) : null;
  }

  /**
   * Return trimmed line read from itemReader.read()
   * 
   * @return trimmed line, can be null
   * @throws Exception
   */
  private String readTrimmed() throws Exception {
    String line = (String) itemReader.read();
    return line != null ? line.trim() : null;
  }

  protected abstract Object mapLines(long id, String[] lines);

  public AbstractSpsResponseItemReader() {
    itemReader = new FlatFileItemReader();
    // set tokenizer that puts the line into one token of a FieldSet
    itemReader.setLineTokenizer(new LineTokenizer() {
      public FieldSet tokenize(String line) {
        return new DefaultFieldSet(new String[]{line});
      }
    });
    // set mapper that returns the first value of the FieldSet
    itemReader.setFieldSetMapper(new FieldSetMapper() {
      public Object mapLine(FieldSet fs) {
        return fs.getValues()[0];
      }
    });
  }

  public final void setResource(Resource resource) {
    itemReader.setResource(resource);
  }

  public final void mark() throws MarkFailedException {
    itemReader.mark();
  }

  public final void reset() throws ResetFailedException {
    itemReader.reset();
  }

  public final void close(ExecutionContext executionContext) throws ItemStreamException {
    itemReader.close(executionContext);
  }

  public final void open(ExecutionContext executionContext) throws ItemStreamException {
    itemReader.open(executionContext);
  }

  public final void update(ExecutionContext executionContext) throws ItemStreamException {
    itemReader.update(executionContext);
  }

}
