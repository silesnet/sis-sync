/*
 * Copyright 2008 the original author or authors.
 */
package cz.silesnet.sis.sync.item.reader;

import cz.silesnet.sis.sync.domain.Invoice;

/**
 * ItemReader implementation that reads SPS Response XML file and returns Invoice instance with just invoice id set.
 * 
 * @author sikorric
 * 
 */
public class SpsInvoiceItemReader extends AbstractSpsResponseItemReader {

    public SpsInvoiceItemReader() {
    }

    @Override
    protected Object mapLines(long id, String[] lines) {
        Invoice invoice = new Invoice();
        invoice.setId(id);
        return invoice;
    }

}
