/*
 * Copyright 2008 the original author or authors.
 */
package cz.silesnet.sis.sync;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.MarkFailedException;
import org.springframework.batch.item.NoWorkFoundException;
import org.springframework.batch.item.ParseException;
import org.springframework.batch.item.ResetFailedException;
import org.springframework.batch.item.UnexpectedInputException;
import org.springframework.core.io.Resource;

import cz.silesnet.sis.sync.domain.Customer;

/**
 * ItemReader implementation, that reads SPS generated customers import result
 * XML file.
 * 
 * 
 * @author Richard Sikora
 */
public class SpsResCustomerItemReader implements ItemReader {

    private Resource resource;
    private BufferedReader input;
    private boolean initialized = false;

    public SpsResCustomerItemReader() {
        super();
    }

    /**
     * Input file injection method.
     * 
     * @param resource
     *            input file
     */
    public void setResource(Resource resource) {
        if (initialized)
            throw new IllegalStateException("Reader already initialized.");
        this.resource = resource;
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
        Pattern detailsBegin = Pattern.compile("\\s*<rdc:producedDetails>\\s*");
        Pattern detailsEnd = Pattern.compile("\\s*</rdc:producedDetails>\\s*");
        Pattern idLine = Pattern.compile("\\s*<rdc:id>(\\d+)</rdc:id>\\s*");
        Pattern codeLine = Pattern.compile("\\s*<rdc:code>(.+)</rdc:code>\\s*");
        String line = null;
        Customer customer = null;
        Matcher matcher = null;
        while ((line = input.readLine()) != null) {
            if (detailsBegin.matcher(line).matches()) {
                customer = new Customer();
                continue;
            }
            matcher = idLine.matcher(line);
            if (matcher.matches()) {
                customer.setSymbol(matcher.group(1));
                continue;
            }
            matcher = codeLine.matcher(line);
            if (matcher.matches()) {
                customer.setName(matcher.group(1));
                continue;
            }
            if (detailsEnd.matcher(line).matches()) {
                break;
            }
        }
        if (line == null) {
            input.close();
        }
        return customer;
    }
    /**
     * Does nothing in this ItemReader implementation.
     * 
     * @see org.springframework.batch.item.ItemReader#reset()
     */
    public void reset() throws ResetFailedException {
    }

    private void initializeInput() throws IOException {
        input = new BufferedReader(new FileReader(resource.getFile()));
        initialized = true;
    }
}
