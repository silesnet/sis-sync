/*
 * Copyright 2008 the original author or authors.
 */
package cz.silesnet.sis.sync.item.reader;

import cz.silesnet.sis.sync.dao.InvoiceDao;
import cz.silesnet.sis.sync.domain.Invoice;
import cz.silesnet.sis.sync.domain.InvoiceResult;

/**
 * ItemReader that reads SPS invoice import result file and does lookup to SIS
 * for bank account.
 * 
 * @author Richard Sikora
 * 
 */
public class SpsInvoiceBankAccountItemReader extends SpsInvoiceItemReader {

    private InvoiceDao dao;

    public void setDao(InvoiceDao dao) {
        this.dao = dao;
    }

    @Override
    protected Object mapLines(long id, String[] lines) {
        InvoiceResult result = (InvoiceResult) super.mapLines(id, lines);
        // find corresponding invoice in SIS
        Invoice invoice = dao.find(result.getSisId());
        // refresh SPS id in SIS invoice
        invoice.setSpsId(result.getSpsId());
        return invoice;
    }
}
