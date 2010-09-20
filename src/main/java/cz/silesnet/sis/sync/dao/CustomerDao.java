/*
 * Copyright 2008 the original author or authors.
 */
package cz.silesnet.sis.sync.dao;

import cz.silesnet.sis.sync.domain.Customer;

/**
 * Intraface describing SIS Customer database operations.
 *
 * @author rsi
 */
public interface CustomerDao {
  /**
   * Retrieve the Customer from the database.
   *
   * @param id customer id
   * @return persisted customer
   * @throws IllegalArgumentException when customer not found
   */
  Customer find(long id);

}
