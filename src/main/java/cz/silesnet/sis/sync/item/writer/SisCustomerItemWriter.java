/*
 * Copyright 2008 the original author or authors.
 */
package cz.silesnet.sis.sync.item.writer;

import java.util.ArrayList;
import java.util.List;

import cz.silesnet.sis.sync.domain.Customer;

/**
 * Implementation of {@link AbstractDataPackItemWriter} that writes Customer
 * into SPS XML import format.
 * 
 * @author sikorric
 * 
 */
public class SisCustomerItemWriter extends AbstractDataPackItemWriter {

    public static final String ADDRESSBOOK_ELEMENT_VERSION = "1.3";

    public SisCustomerItemWriter() {
        super();
    }

    @Override
    protected String[] dataPackItemLines(Object item) {
        if (!(item instanceof Customer)) {
            throw new IllegalArgumentException("Item is not of Customer type.");
        }
        Customer customer = (Customer) item;
        List<String> lines = new ArrayList<String>();

        lines.add("<adb:addressbook version=\"" + ADDRESSBOOK_ELEMENT_VERSION + "\">");
        lines.add("<adb:addressbookHeader>");
        lines.add("<adb:identity>");
        lines.add("<typ:address>");
        // customer data
        lines.add("<typ:company>" + customer.getName() + "</typ:company>");
        lines.add("<typ:city>" + customer.getCity() + "</typ:city>");
        // element trailer
        lines.add("</typ:address>");
        lines.add("</adb:identity>");
        lines.add("</adb:addressbookHeader>");
        lines.add("</adb:addressbook>");

        return lines.toArray(new String[lines.size()]);
    }

    @Override
    protected String[] nameSpaceLines() {
        return new String[]{"xmlns:adb=\"http://www.stormware.cz/schema/addressbook.xsd\""};
    }

}
