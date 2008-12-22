/*
 * Copyright 2008 the original author or authors.
 */
package cz.silesnet.sis.sync.item.writer;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import cz.silesnet.sis.sync.domain.Customer;

/**
 * Concrete implementation of #{@link AbstractFileItemWritter} that writes customer data to SPS XML import file.
 * 
 * @author sikorric
 * 
 */
public class SisCustomerItemWriter extends AbstractFileItemWritter {

    private static Log log = LogFactory.getLog(SisCustomerItemWriter.class);

    private String ico;

    public void setIco(String ico) {
        this.ico = ico;
    }

    public SisCustomerItemWriter() {
        super();
    }

    @Override
    protected String headerToString() {
        StringBuffer header = new StringBuffer();
        header.append("<?xml version=\"1.0\" encoding=\"Windows-1250\"?>\n");
        header.append("<dat:dataPack id=\"").append(getBatchImportId()).append("\" ");
        header.append("ico=\"").append(ico).append("\" ");
        header.append("application=\"SIS\" version=\"1.0\" note=\"SIS customers import.\"\n");
        header.append("xmlns:dat=\"http://www.stormware.cz/schema/data.xsd\"\n");
        header.append("xmlns:adb=\"http://www.stormware.cz/schema/addressbook.xsd\"\n");
        header.append("xmlns:typ=\"http://www.stormware.cz/schema/type.xsd\">");
        String fullHeader = header.toString();
        log.debug(fullHeader);
        return fullHeader;
    }

    protected String getBatchImportId() {
        return "1234";
    }

    protected String getItemImportId(Object item) {
        return "12345";
    }

    @Override
    protected String itemToString(Object item) {
        if (item == null)
            return null;
        if (!(item instanceof Customer)) {
            throw new IllegalArgumentException("Item is not of Customer type.");
        }
        Customer customer = (Customer) item;
        StringBuffer xml = new StringBuffer();
        // element header
        xml.append("<dat:dataPackItem id=\"").append(getItemImportId(item)).append("\" version=\"1.0\">\n");
        xml.append("<adb:addressbook version=\"1.3\">\n");
        xml.append("<adb:addressbookHeader>\n");
        xml.append("<adb:identity>\n");
        xml.append("<typ:address>\n");
        // customer data
        xml.append("<typ:company>").append(customer.getName()).append("</typ:company>\n");
        // element trailer
        xml.append("</typ:address>\n");
        xml.append("</adb:identity>\n");
        xml.append("</adb:addressbookHeader>\n");
        xml.append("</adb:addressbook>\n");
        xml.append("</dat:dataPackItem>");
        String fullXml = xml.toString();
        log.debug(fullXml);
        return fullXml;
    }

    @Override
    protected String trailerToString() {
        String trailer = "</dat:dataPack>";
        log.debug(trailer);
        return trailer;
    }

}
