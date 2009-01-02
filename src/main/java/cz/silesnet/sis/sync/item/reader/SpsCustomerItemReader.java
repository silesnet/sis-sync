/*
 * Copyright 2008 the original author or authors.
 */
package cz.silesnet.sis.sync.item.reader;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import cz.silesnet.sis.sync.domain.Customer;

/**
 * ItemReader implementation, that reads SPS generated customers import result
 * XML file.
 * 
 * @author Richard Sikora
 */
public class SpsCustomerItemReader extends AbstractSpsResponseItemReader {

    @Override
    protected Object mapLines(long id, String[] lines) {
        Customer customer = new Customer();
        customer.setId(id);
        Pattern idLinePattern = Pattern.compile("<rdc:id>(\\d+)</rdc:id>");
        Pattern codeLinePattern = Pattern.compile("<rdc:code>(.+)</rdc:code>");
        boolean idFound = false;
        boolean codeFound = false;
        for (String line : lines) {
            if (idFound && codeFound) {
                break;
            }
            if (!idFound) {
                String idValue = getElementValue(idLinePattern.matcher(line));
                if (idValue != null) {
                    customer.setSymbol(idValue);
                    idFound = true;
                    continue;
                }
            }
            if (!codeFound) {
                String codeValue = getElementValue(codeLinePattern.matcher(line));
                if (codeValue != null) {
                    customer.setName(codeValue);
                    codeFound = true;
                    continue;
                }
            }
        }
        return customer;
    }

    /**
     * Returns first matching group of the matcher, null otherwise.
     * 
     * @param matcher
     *            initialized matcher with at least one group defined
     * @return first matching group or null
     */
    private String getElementValue(Matcher matcher) {
        return matcher.matches() ? matcher.group(1) : null;
    }

}
