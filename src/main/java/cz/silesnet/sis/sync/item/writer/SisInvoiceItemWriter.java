/*
 * Copyright 2008 the original author or authors.
 */
package cz.silesnet.sis.sync.item.writer;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import cz.silesnet.sis.sync.domain.Invoice;
import cz.silesnet.sis.sync.domain.Invoice.Item;

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
    protected String[] dataPackItemLines(Object dataPackItem) {
        if (!(dataPackItem instanceof Invoice))
            throw new IllegalArgumentException("Item has to be an Invoice.");
        Invoice invoice = (Invoice) dataPackItem;
        List<String> lines = new ArrayList<String>();
        lines.add("<inv:invoice version=\"" + ELEMENT_INVOICE_VERSION + "\">");
        // Header
        lines.add("<inv:invoiceHeader>");
        lines.add("<inv:invoiceType>issuedInvoice</inv:invoiceType>");
        lines.add("<inv:number>");
        lines.add("<typ:numberRequested>" + invoice.getNumber() + "</typ:numberRequested>");
        lines.add("</inv:number>");
        lines.add("<inv:date>"
                + AbstractDataPackItemWriter.ELEMENT_DATE_FORMAT.format(new Date(invoice.getDate().getMillis()))
                + "</inv:date>");
        lines.add("<inv:text>" + invoice.getText() + "</inv:text>");
        lines.add("<inv:partnerIdentity>");
        // NOTE: SPS customer Id == SIS customer Symbol
        lines.add("<typ:id>" + invoice.getCustomerSymbol() + "</typ:id>");
        lines.add("</inv:partnerIdentity>");
        lines.add("</inv:invoiceHeader>");
        // Details
        if (invoice.getItems().size() > 0) {
            lines.add("<inv:invoiceDetail>");
            for (Item item : invoice.getItems()) {
                lines.add("<inv:invoiceItem>");
                lines.add("<inv:text>" + item.getText() + "</inv:text>");
                lines.add("<inv:quantity>" + "1" + "</inv:quantity>");
                lines.add("<inv:unit>" + "m&#236;s." + "</inv:unit>");
                lines.add("<inv:coefficient>" + "1.0" + "</inv:coefficient>");
                lines.add("<inv:homeCurrency>");
                lines.add("<typ:unitPrice>" + item.getNet() + "</typ:unitPrice>");
                lines.add("<typ:price>" + item.getNet() + "</typ:price>");
                lines.add("<typ:priceVAT>" + item.getVat() + "</typ:priceVAT>");
                lines.add("<typ:priceSum>" + item.getBrt() + "</typ:priceSum>");
                lines.add("</inv:homeCurrency>");
                lines.add("</inv:invoiceItem>");
            }
            lines.add("</inv:invoiceDetail>");
        }
        // Summary
        lines.add("<inv:invoiceSummary>");
        lines.add("<inv:roundingDocument>math2one</inv:roundingDocument>");
        lines.add("<inv:roundingVAT>none</inv:roundingVAT>");
        lines.add("<inv:homeCurrency>");
        lines.add("<typ:priceHigh>" + invoice.getNet() + "</typ:priceHigh>");
        lines.add("<typ:priceHighVAT>" + invoice.getVat() + "</typ:priceHighVAT>");
        lines.add("<typ:priceHighSum>" + invoice.getBrt() + "</typ:priceHighSum>");
        lines.add("<typ:round>");
        lines.add("<typ:priceRound>" + invoice.getRounding() + "</typ:priceRound>");
        lines.add("</typ:round>");
        lines.add("</inv:homeCurrency>");
        lines.add("</inv:invoiceSummary>");
        lines.add("</inv:invoice>");
        return lines.toArray(new String[lines.size()]);
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
