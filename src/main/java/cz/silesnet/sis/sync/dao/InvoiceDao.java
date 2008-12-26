/*
 * Copyright 2008 the original author or authors.
 */
package cz.silesnet.sis.sync.dao;

import cz.silesnet.sis.sync.domain.Invoice;

/**
 * Interface describing SIS invoice persistence operations.
 * 
 * @author sikorric
 * 
 */
public interface InvoiceDao {
    /**
     * Retrieve an invoice from the database.
     * 
     * @param id
     *            invoice id
     * @return persistent invoice
     * @throws IllegalArgumentException
     *             when invoice not found
     */
    Invoice find(long id);
}
