/*
 * Copyright 2008 the original author or authors.
 */
package cz.silesnet.sis.sync.item.reader;

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
 * ItemReader implementation, that reads SPS generated customers import result XML file.
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
     * Reads new Customer status from SPS import result XML by simple string comparison.
     * 
     * @return customer with initialized name and symbol, null when end of file reached
     */
    public Object read() throws Exception, UnexpectedInputException, NoWorkFoundException, ParseException {
        if (!initialized) {
            initializeInput();
        }
        Pattern itemBegin = Pattern.compile("<rsp:responsePackItem.* id=\".+_\\d+_(\\d+)\".* state=\"ok\".*>");
        Pattern itemEnd = Pattern.compile("</rsp:responsePackItem>");
        Pattern idLine = Pattern.compile("<rdc:id>(\\d+)</rdc:id>");
        Pattern codeLine = Pattern.compile("<rdc:code>(.+)</rdc:code>");
        String line = null;
        Customer customer = null;
        Matcher itemMatcher = null;
        Matcher idMatcher = null;
        Matcher codeMatcher = null;
        while ((line = input.readLine()) != null) {
            line = line.trim();
            itemMatcher = itemBegin.matcher(line);
            if (itemMatcher.matches()) {
                customer = new Customer();
                customer.setId(Long.valueOf(itemMatcher.group(1)));
                continue;
            }
            idMatcher = idLine.matcher(line);
            if (idMatcher.matches()) {
                customer.setSymbol(idMatcher.group(1));
                continue;
            }
            codeMatcher = codeLine.matcher(line);
            if (codeMatcher.matches()) {
                customer.setName(codeMatcher.group(1));
                continue;
            }
            if (itemEnd.matcher(line).matches()) {
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
