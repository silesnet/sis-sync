/*
 * Copyright 2009 the original author or authors.
 */

package cz.silesnet.sis.sync.item.reader;

import cz.silesnet.sis.sync.dao.CustomerDao;
import cz.silesnet.sis.sync.domain.Customer;
import cz.silesnet.sis.sync.domain.CustomerResult;

/**
 * Customer ItemReader that reads SPS Customer import result XML and retrieves
 * SIS Customer using Dao.
 * 
 * @author rsi
 * 
 */
public class SpsCustomerItemReader extends SpsCustomerResultItemReader {

    CustomerDao dao;

    public void setDao(CustomerDao dao) {
        this.dao = dao;
    }

    @Override
    protected Object mapLines(long id, String[] lines) {
        CustomerResult result = (CustomerResult) super.mapLines(id, lines);
        // find SIS customer
        Customer customer = dao.find(result.getSisId());
        return customer;
    }

}
