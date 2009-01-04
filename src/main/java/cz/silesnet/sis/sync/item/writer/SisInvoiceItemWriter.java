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

    public static final String INVOICE_ELEMENT_VERSION = "1.3";
    public static final String ITEM_UNIT = "m&#236;s.";

    public SisInvoiceItemWriter() {
        super();
    }

    /**
     * Converts Invoice into XML lines. It will be content of <dataPackItem> element.
     */
    @Override
    protected String[] dataPackItemLines(Object dataPackItem) {
        if (!(dataPackItem instanceof Invoice))
            throw new IllegalArgumentException("Item has to be an Invoice.");
        Invoice invoice = (Invoice) dataPackItem;
        List<String> lines = new ArrayList<String>();
        lines.add(elBeg("inv:invoice version=\"" + INVOICE_ELEMENT_VERSION + "\""));
        // Header
        lines.add(elBeg("inv:invoiceHeader"));
        lines.add(elValue("inv:invoiceType", "issuedInvoice"));
        lines.add(elBeg("inv:number"));
        lines.add(elValue("typ:numberRequested", invoice.getNumber()));
        lines.add(elEnd("inv:number"));
        lines.add(elValue("inv:date", ELEMENT_DATE_FORMAT.format(new Date(invoice.getDate().getMillis()))));
        lines.add(elValue("inv:text", invoice.getText()));
        lines.add(elBeg("inv:partnerIdentity"));
        // NOTE: SPS customer Id == SIS customer Symbol
        lines.add(elValue("typ:id", invoice.getCustomerSymbol()));
        lines.add(elEnd("inv:partnerIdentity"));
        lines.add(elEnd("inv:invoiceHeader"));
        // Details
        if (invoice.getItems().size() > 0) {
            lines.add(elBeg("inv:invoiceDetail"));
            for (Item item : invoice.getItems()) {
                lines.add(elBeg("inv:invoiceItem"));
                lines.add(elValue("inv:text", item.getText()));
                lines.add(elValue("inv:quantity", "1"));
                lines.add(elValue("inv:unit", ITEM_UNIT));
                lines.add(elValue("inv:coefficient", "1.0"));
                lines.add(elBeg("inv:homeCurrency"));
                lines.add(elValue("typ:unitPrice", item.getNet()));
                lines.add(elValue("typ:price", item.getNet()));
                lines.add(elValue("typ:priceVAT", item.getVat()));
                lines.add(elValue("typ:priceSum", item.getBrt()));
                lines.add(elEnd("inv:homeCurrency"));
                lines.add(elEnd("inv:invoiceItem"));
            }
            lines.add(elEnd("inv:invoiceDetail"));
        }
        // Summary
        lines.add(elBeg("inv:invoiceSummary"));
        lines.add(elValue("inv:roundingDocument", "math2one"));
        lines.add(elValue("inv:roundingVAT", "none"));
        lines.add(elBeg("inv:homeCurrency"));
        lines.add(elValue("typ:priceHigh", invoice.getNet()));
        lines.add(elValue("typ:priceHighVAT", invoice.getVat()));
        lines.add(elValue("typ:priceHighSum", invoice.getBrt()));
        lines.add(elBeg("typ:round"));
        lines.add(elValue("typ:priceRound", invoice.getRounding()));
        lines.add(elEnd("typ:round"));
        lines.add(elEnd("inv:homeCurrency"));
        lines.add(elEnd("inv:invoiceSummary"));
        lines.add(elEnd("inv:invoice"));
        return lines.toArray(new String[lines.size()]);
    }

    /**
     * Returns name space definitions to be appended to the root element (<dataPack>).
     */
    @Override
    protected String[] nameSpaceLines() {
        return new String[] { "xmlns:inv=\"http://www.stormware.cz/schema/invoice.xsd\"" };
    }

}
