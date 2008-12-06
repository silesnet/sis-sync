/*
 * Copyright 2008 the original author or authors.
 */
package cz.silesnet.sis.sync;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;

import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.MarkFailedException;
import org.springframework.batch.item.NoWorkFoundException;
import org.springframework.batch.item.ParseException;
import org.springframework.batch.item.ResetFailedException;
import org.springframework.batch.item.UnexpectedInputException;

import cz.silesnet.sis.sync.domain.Customer;

/**
 * ItemReader implementation, that reads SPS generated Customers import result
 * XML file.
 * 
 * 
 * @author Richard Sikora
 */
public class SpsResCutomerReader implements ItemReader {

    private File file;
    private BufferedReader input;
    private boolean initialized = false;

    public SpsResCutomerReader() {
        super();
    }

    /**
     * Input file injection method.
     * 
     * @param file
     *            input file
     */
    public void setInputFile(File file) {
        if (initialized)
            throw new IllegalStateException("Reader already initialized.");
        this.file = file;
    }

    /**
     * Does nothing in this ItemReader implementation.
     * 
     * @see org.springframework.batch.item.ItemReader#mark()
     */
    public void mark() throws MarkFailedException {
    }

    /**
     * Reads new Customer status from SPS import result XML by simple string
     * comparison.
     * 
     * @return customer with initialized name and symbol, null when end of file
     *         reached
     */
    public Object read() throws Exception, UnexpectedInputException, NoWorkFoundException, ParseException {
        if (!initialized) {
            initializeInput();
        }
        String line = input.readLine();
        while (line != null) {
            /*-
             * TODO read lines till "<rdc:producedDetails>",
             * next line is "<rdc:id>spsId</rdc:id>" map it to customer.symbol,
             * next line is "<rdc:code>customerName</rdc:code>" map it to customer.name
             */
            return new Customer();
        }
        return null;
    }

    /**
     * Does nothing in this ItemReader implementation.
     * 
     * @see org.springframework.batch.item.ItemReader#reset()
     */
    public void reset() throws ResetFailedException {
    }

    private void initializeInput() throws FileNotFoundException {
        input = new BufferedReader(new FileReader(file));
        initialized = true;
    }
}
