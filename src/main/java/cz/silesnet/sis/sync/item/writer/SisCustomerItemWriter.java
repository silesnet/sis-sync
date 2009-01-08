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
    public static final String CONTRACT_NUMBER_PREFIX = "\u010c\u00edslo smlouvy: ";

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
        // header
        lines.add(elBeg("adb:addressbook version=\"" + ADDRESSBOOK_ELEMENT_VERSION + "\""));
        lines.add(elBeg("adb:addressbookHeader"));
        // identity
        lines.add(elBeg("adb:identity"));
        lines.add(elBeg("typ:address"));
        lines.add(elValue("typ:company", customer.getName()));
        lines.add(elValue("typ:division", customer.getSupplementaryName()));
        lines.add(elValue("typ:name", customer.getContactName()));
        lines.add(elValue("typ:city", customer.getCity()));
        lines.add(elValue("typ:street", customer.getStreet()));
        lines.add(elValue("typ:zip", customer.getZip()));
        lines.add(elValue("typ:ico", customer.getIco()));
        lines.add(elValue("typ:dic", customer.getDic()));
        lines.add(elEnd("typ:address"));
        lines.add(elEnd("adb:identity"));
        // other
        lines.add(elValue("adb:phone", customer.getPhone()));
        lines.add(elValue("adb:email", customer.getEmail()));
        lines.add(elValue("adb:adGroup", Customer.AD_GROUP_KEY));
        lines.add(elValue("adb:p2", "true"));
        lines.add(elValue("adb:note", CONTRACT_NUMBER_PREFIX + customer.getContract()));
        // duplicity check
        lines.add(elBeg("adb:duplicityFields actualize=\"true\""));
        lines.add(elValue("adb:fieldICO", "true"));
        lines.add(elValue("adb:fieldFirma", "true"));
        lines.add(elEnd("adb:duplicityFields"));
        // trailer
        lines.add(elEnd("adb:addressbookHeader"));
        lines.add(elEnd("adb:addressbook"));

        return lines.toArray(new String[lines.size()]);
    }
    @Override
    protected String[] nameSpaceLines() {
        return new String[]{"xmlns:adb=\"http://www.stormware.cz/schema/addressbook.xsd\""};
    }

}
