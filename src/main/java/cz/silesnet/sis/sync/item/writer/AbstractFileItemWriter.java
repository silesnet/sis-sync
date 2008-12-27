/*
 * Copyright 2008 the original author or authors.
 */
package cz.silesnet.sis.sync.item.writer;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

import org.springframework.batch.item.ClearFailedException;
import org.springframework.batch.item.ExecutionContext;
import org.springframework.batch.item.FlushFailedException;
import org.springframework.batch.item.ItemStreamException;
import org.springframework.batch.item.support.AbstractItemStreamItemWriter;
import org.springframework.core.io.Resource;

/**
 * Abstract implementation of file ItemWriter.
 * 
 * @author Richard Sikora
 */
public abstract class AbstractFileItemWriter extends AbstractItemStreamItemWriter {

    private Resource resource;
    private BufferedWriter output;
    private boolean initialized = false;

    public AbstractFileItemWriter() {
        super();
    }

    /**
     * No operation implementation.
     */
    public void clear() throws ClearFailedException {
    }

    /**
     * Closes the output file writing trailer first.
     * 
     * @param executionContext
     *            execution context
     */
    @Override
    public void close(ExecutionContext executionContext) throws ItemStreamException {
        String trailer = trailerToString();
        if (trailer != null) {
            try {
                output.write(trailerToString() + "\n");
            } catch (IOException e) {
                throw new ItemStreamException("Can not write trailer to output.", e);
            }
        }
        try {
            output.close();
        } catch (IOException e) {
            throw new ItemStreamException(e);
        }
        super.close(executionContext);
        initialized = false;
    }

    /**
     * Flushes underlying output file.
     */
    public void flush() throws FlushFailedException {
        try {
            output.flush();
        } catch (IOException e) {
            throw new FlushFailedException("Can not flush the file: " + resource.getDescription(), e);
        }
    }

    /**
     * Returns file header. Called once from open(). {@link #open(ExecutionContext)} will add line separator after
     * header. Default implementation, null means nothing will be written to the output.
     * 
     * @return header string, can be null
     */
    protected String headerToString() {
        return null;
    }

    private void initializeOutput() {
        try {
            output = new BufferedWriter(new FileWriter(resource.getFile()));
        } catch (IOException e) {
            throw new IllegalStateException("Can not open file for writting: " + resource.getDescription(), e);
        }
        initialized = true;
    }

    /**
     * Converts item object to its string representation. Can be null, which means nothing will be written to the
     * output.
     * 
     * @param item
     *            object to be written, can be null
     * @return item string, can be null
     */
    protected abstract String itemToString(Object item);

    /**
     * Returns file trailer. Called once from close(). {@link #close(ExecutionContext)} will add line separator after
     * header. Default implementation, null means nothing will be written to the output.
     * 
     * @return header string, can be null
     */
    protected void itemWritten(Object item) {

    }

    /**
     * Open underlying resource for writing and writes file header to the output.
     * 
     * @param executionContext
     *            execution context
     */
    @Override
    public void open(ExecutionContext executionContext) throws ItemStreamException {
        if (initialized)
            throw new IllegalStateException("Writer already initialized.");
        super.open(executionContext);
        initializeOutput();
        String header = headerToString();
        if (header != null) {
            try {
                output.write(header + "\n");
            } catch (IOException e) {
                throw new ItemStreamException("Can not write header to output.", e);
            }
        }
    }

    /**
     * Input file injection method.
     * 
     * @param resource
     *            output file
     */
    public void setResource(Resource resource) {
        if (initialized)
            throw new IllegalStateException("Writer already initialized.");
        this.resource = resource;
    }

    protected String trailerToString() {
        return null;
    }

    /**
     * Writes item to output. Uses {@link #itemToString(Object)} to convert item object to its string representation.
     * 
     * @param item
     *            item to be written, can be null
     */
    public final void write(Object item) throws Exception {
        if (!initialized)
            throw new IllegalStateException("Writer is not initialized, call open() first.");
        String itemString = itemToString(item);
        if (itemString != null) {
            output.write(itemString + "\n");
            itemWritten(item);
        }
    }
}
