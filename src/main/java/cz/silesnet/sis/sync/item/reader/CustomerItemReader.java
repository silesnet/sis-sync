/*
 * Copyright 2009 the original author or authors.
 */

package cz.silesnet.sis.sync.item.reader;

import cz.silesnet.sis.sync.dao.CustomerDao;
import cz.silesnet.sis.sync.domain.Customer;
import cz.stormware.schema.response.ResponsePackItemType;

/**
 * Customer ItemReader that reads SPS Customer import result XML and retrieves
 * SIS Customer using Dao.
 * 
 * @author rsi
 * 
 */
public class CustomerItemReader extends DaoResponsePackItemReader<Customer, CustomerDao> {

  @Override
  protected Customer doReadWithDao(ResponsePackItemType responseItem, CustomerDao dao) {
    ResponseId sisId = ResponseId.of(responseItem.getId());
    return dao.find(sisId.id());
  }

}
