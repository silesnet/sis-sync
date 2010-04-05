/*
 * Copyright 2008 the original author or authors.
 */
package cz.silesnet.sis.sync.item.reader;

import org.springframework.util.Assert;

import cz.silesnet.sis.sync.dao.InvoiceDao;
import cz.silesnet.sis.sync.domain.Invoice;
import cz.stormware.schema.response.ResponsePackItemType;

/**
 * ItemReader that reads SPS invoice import result file and does lookup to SIS
 * for bank account.
 * 
 * @author Richard Sikora
 * 
 */
public class SpsInvoiceBankAccountItemReader extends ResponsePackItemReader {

  private InvoiceDao dao;

  public SpsInvoiceBankAccountItemReader() {
    super(); // initializing ResponsePackItemReader
  }

  public void setDao(InvoiceDao dao) {
    this.dao = dao;
  }

  @Override
  protected Object doRead() throws Exception {
    ResponsePackItemType responseItem = (ResponsePackItemType) readResponseItemInternal();
    if (responseItem == null)
      return null;
    ResponseId sisId = ResponseId.of(responseItem.getId());
    Invoice invoice = dao.find(sisId.id());

    // add SPS id
    String spsId = responseItem.getInvoiceResponse().getProducedDetails().getId();
    invoice.setSpsId(Long.valueOf(spsId));

    return invoice;
  }

  // externalized for testing
  protected Object readResponseItemInternal() throws Exception {
    return super.doRead();
  }

  @Override
  public void afterPropertiesSet() throws Exception {
    super.afterPropertiesSet();
    Assert.notNull(dao, "The Dao must not be null.");
  }

}
