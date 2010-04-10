/*
 * Copyright 2008 the original author or authors.
 */
package cz.silesnet.sis.sync.item.writer;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import org.springframework.batch.item.ExecutionContext;
import org.springframework.batch.item.ItemStreamException;
import org.springframework.batch.item.file.FlatFileItemWriter;
import org.springframework.batch.item.file.ResourceAwareItemWriterItemStream;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.core.io.Resource;

/**
 * Abstract implementation of file ItemWriter with header and trailer. Delegates
 * to {@link FlatFileItemWriter}.
 * 
 * @author sikorric
 * 
 */
public abstract class AbstractHeaderTrailerFileItemWriter<T>
    implements
      ResourceAwareItemWriterItemStream<T>,
      InitializingBean {

  private long itemsWritten = 0;
  private final FlatFileItemWriter<String> itemWriter;

  public AbstractHeaderTrailerFileItemWriter() {
    itemWriter = new FlatFileItemWriter<String>();
    itemWriter.setShouldDeleteIfExists(true);
    itemWriter.setSaveState(false);
  }

  @Override
  public void write(List<? extends T> items) throws Exception {
    for (T item : items) {
      writeLines(itemLines(item));
      itemsWritten++;
    }
  }

  protected abstract String[] itemLines(T item);

  protected String[] headerLines() {
    return new String[]{};
  }

  protected String[] trailerLines() {
    return new String[]{};
  }

  private void writeLines(String[] lines) {
    LinkedList<String> linesList = new LinkedList<String>(Arrays.asList(lines));
    try {
      itemWriter.write(linesList);
    } catch (Exception e) {
      throw new ItemStreamException(e);
    }
  }

  protected long getItemsWritten() {
    return itemsWritten;
  }

  @Override
  public void setResource(Resource resource) {
    itemWriter.setResource(resource);
  }

  @Override
  public void afterPropertiesSet() throws Exception {
    itemWriter.afterPropertiesSet();
  }

  public void setEncoding(String encoding) {
    itemWriter.setEncoding(encoding);
  }

  @Override
  public final void open(ExecutionContext executionContext) throws ItemStreamException {
    itemWriter.open(executionContext);
    writeLines(headerLines());
    itemsWritten = 0;
  }

  @Override
  public final void close() throws ItemStreamException {
    writeLines(trailerLines());
    itemWriter.close();
  }

  @Override
  public final void update(ExecutionContext executionContext) throws ItemStreamException {
    itemWriter.update(executionContext);
  }

}
