/*
 * Copyright 2008 the original author or authors.
 */
package cz.silesnet.sis.sync.item.writer;

import java.util.ArrayList;
import java.util.List;

import cz.silesnet.sis.sync.domain.Invoice;

/**
 * Writes SIS invoice in DataPack XML format to the output file.
 * 
 * @author sikorric
 * 
 */
public class SisInvoiceItemWriter extends AbstractDataPackItemWriter {

    public static final String ELEMENT_INVOICE_VERSION = "1.3";
    public static final String MY_COMPANY_NAME = "SilesNet s.r.o.";

    public SisInvoiceItemWriter() {
        super();
    }

    /**
     * Converts Invoice into XML lines. It will be content of <dataPackItem>
     * element.
     */
    @Override
    protected String[] dataPackItemLines(Object item) {
        if (!(item instanceof Invoice))
            throw new IllegalArgumentException("Item has to be an Invoice.");
        Invoice invoice = (Invoice) item;
        List<String> lines = new ArrayList<String>();
        lines.add("<inv:invoice version=\"" + ELEMENT_INVOICE_VERSION + "\">");
        lines.add("<inv:invoiceHeader>");
        lines.add("<inv:invoiceType>issuedInvoice</inv:invoiceType>");
        lines.add("<inv:number>");
        lines.add("<typ:numberRequested>" + invoice.getNumber() + "</typ:numberRequested>");
        lines.add("</inv:number>");
        lines.add("<inv:partnerIdentity>");
        lines.add("<typ:id>" + invoice.getCustomerId() + "</typ:id>");
        lines.add("</inv:partnerIdentity>");
        // lines.add("");

        return lines.toArray(new String[]{});
    }

    /**
     * Returns name space definitions to be appended to the root element
     * (<dataPack>).
     */
    @Override
    protected String[] nameSpaceLines() {
        return new String[]{"xmlns:inv=\"http://www.stormware.cz/schema/invoice.xsd\""};
    }

}
