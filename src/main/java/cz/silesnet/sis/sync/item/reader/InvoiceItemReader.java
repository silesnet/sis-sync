/*
 * Copyright 2008 the original author or authors.
 */
package cz.silesnet.sis.sync.item.reader;

import cz.silesnet.sis.sync.dao.InvoiceDao;
import cz.silesnet.sis.sync.domain.Invoice;
import cz.stormware.schema.response.ResponsePackItemType;

/**
 * ItemReader that reads SPS invoice import result file and does lookup to SIS
 * for bank account.
 *
 * @author Richard Sikora
 */
public class InvoiceItemReader extends DaoResponsePackItemReader<Invoice, InvoiceDao> {

  @Override
  protected Invoice doReadWithDao(ResponsePackItemType responseItem, InvoiceDao dao) {
    ResponseId sisId = ResponseId.of(responseItem.getId());
    Invoice invoice = dao.find(sisId.id());

    // add SPS id
    String spsId = responseItem.getInvoiceResponse().getProducedDetails().getId();
    invoice.setSpsId(Long.valueOf(spsId));

    return invoice;
  }

}
