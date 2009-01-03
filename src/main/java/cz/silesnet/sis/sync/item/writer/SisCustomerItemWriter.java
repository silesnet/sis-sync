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
        // header
        lines.add("<adb:addressbook version=\"" + ADDRESSBOOK_ELEMENT_VERSION + "\">");
        lines.add("<adb:addressbookHeader>");
        // identity
        lines.add("<adb:identity>");
        lines.add("<typ:address>");
        lines.add("<typ:company>" + customer.getName() + "</typ:company>");
        lines.add("<typ:division>" + customer.getSupplementaryName() + "</typ:division>");
        lines.add("<typ:name>" + customer.getContactName() + "</typ:name>");
        lines.add("<typ:city>" + customer.getCity() + "</typ:city>");
        lines.add("<typ:street>" + customer.getStreet() + "</typ:street>");
        lines.add("<typ:zip>" + customer.getZip() + "</typ:zip>");
        lines.add("<typ:ico>" + customer.getIco() + "</typ:ico>");
        lines.add("<typ:dic>" + customer.getDic() + "</typ:dic>");
        lines.add("</typ:address>");
        lines.add("</adb:identity>");
        // other
        lines.add("<adb:phone>" + customer.getPhone() + "</adb:phone>");
        lines.add("<adb:email>" + customer.getEmail() + "</adb:email>");
        lines.add("<adb:adGroup>" + Customer.AD_GROUP_KEY + "</adb:adGroup>");
        lines.add("<adb:contract>" + customer.getContract() + "</adb:contract>");
        // duplicity check
        lines.add("<adb:duplicityFields actualize=\"true\">");
        lines.add("<adb:fieldICO>true</adb:fieldICO>");
        lines.add("<adb:fieldFirma>true</adb:fieldFirma>");
        lines.add("</adb:duplicityFields>");
        // trailer
        lines.add("</adb:addressbookHeader>");
        lines.add("</adb:addressbook>");

        return lines.toArray(new String[lines.size()]);
    }

    @Override
    protected String[] nameSpaceLines() {
        return new String[]{"xmlns:adb=\"http://www.stormware.cz/schema/addressbook.xsd\""};
    }

}
