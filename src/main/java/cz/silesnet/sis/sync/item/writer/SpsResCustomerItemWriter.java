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

import cz.silesnet.sis.sync.domain.Customer;

/**
 * Implementation of ItemWriter updating customer.symbol and customer.name to
 * SIS persistence according to SPS read values.
 * 
 * @author Richard Sikora
 */
public class SpsResCustomerItemWriter extends AbstractItemStreamItemWriter {

    private Resource resource;
    private BufferedWriter output;
    private boolean initialized = false;

    public SpsResCustomerItemWriter() {
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

    public void clear() throws ClearFailedException {
    }

    public void flush() throws FlushFailedException {
        try {
            output.flush();
        } catch (IOException e) {
            throw new FlushFailedException("Can not flush the file: " + resource.getDescription(), e);
        }
    }

    /**
     * Writes customer data to output file. One customer per line, fields will
     * be pipe ('|') separated.
     */
    public void write(Object customerObject) throws Exception {
        if (!initialized)
            throw new IllegalStateException("Writer is not initialized, call open() first.");
        if (!(customerObject instanceof Customer)) {
            throw new IllegalArgumentException("Trying to write non Customer type object.");
        }
        Customer customer = (Customer) customerObject;
        output.write(customer.getName() + "|" + customer.getSymbol() + "\n");
    }

    @Override
    public void close(ExecutionContext executionContext) throws ItemStreamException {
        super.close(executionContext);
        try {
            output.close();
        } catch (IOException e) {
            throw new ItemStreamException(e);
        }
    }
    @Override
    public void open(ExecutionContext executionContext) throws ItemStreamException {
        if (initialized)
            throw new IllegalStateException("Writer already initialized.");
        super.open(executionContext);
        initializeOutput();
    }

    private void initializeOutput() {
        try {
            resource.getFile().createNewFile();
            output = new BufferedWriter(new FileWriter(resource.getFile()));
        } catch (IOException e) {
            throw new IllegalStateException("Can not open file for writting: " + resource.getDescription(), e);
        }
        initialized = true;
    }

}
