/*
 * Copyright 2008 the original author or authors.
 */
package cz.silesnet.sis.sync.item.writer;

import org.springframework.batch.item.ClearFailedException;
import org.springframework.batch.item.ExecutionContext;
import org.springframework.batch.item.FlushFailedException;
import org.springframework.batch.item.ItemStream;
import org.springframework.batch.item.ItemStreamException;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.file.FlatFileItemWriter;
import org.springframework.batch.item.file.mapping.PassThroughFieldSetMapper;
import org.springframework.core.io.Resource;

/**
 * Abstract implementation of file ItemWriter with header and trailer. Delegates
 * to {@link FlatFileItemWriter}.
 * 
 * @author sikorric
 * 
 */
public abstract class AbstractHeaderTrailerFileItemWriter implements ItemWriter, ItemStream {

    private long itemsWritten = 0;
    private FlatFileItemWriter itemWriter;

    public AbstractHeaderTrailerFileItemWriter() {
        itemWriter = new FlatFileItemWriter();
        itemWriter.setFieldSetCreator(new PassThroughFieldSetMapper());
        itemWriter.setShouldDeleteIfExists(true);
        itemWriter.setSaveState(false);
    }

    public final void setResource(Resource resource) {
        itemWriter.setResource(resource);
    }

    public final void open(ExecutionContext executionContext) throws ItemStreamException {
        itemWriter.open(executionContext);
        writeLines(headerLines());
        itemsWritten = 0;
    }

    public final void close(ExecutionContext executionContext) throws ItemStreamException {
        writeLines(trailerLines());
        itemWriter.flush();
        itemWriter.close(executionContext);
    }

    public final void write(Object item) throws Exception {
        writeLines(itemLines(item));
        itemsWritten++;
    }

    protected String[] headerLines() {
        return new String[]{};
    }

    protected String[] trailerLines() {
        return new String[]{};
    }

    protected abstract String[] itemLines(Object item);

    protected long getItemsWritten() {
        return itemsWritten;
    }

    private void writeLines(String[] lines) {
        for (String line : lines) {
            try {
                itemWriter.write(line);
            } catch (Exception e) {
                throw new ItemStreamException(e);
            }
        }
    }

    public final void clear() throws ClearFailedException {
        itemWriter.clear();
    }

    public final void flush() throws FlushFailedException {
        itemWriter.flush();
    }

    public final void update(ExecutionContext executionContext) throws ItemStreamException {
        itemWriter.update(executionContext);
    }

}
