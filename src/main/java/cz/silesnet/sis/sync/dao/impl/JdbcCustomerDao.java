/*
 * Copyright 2009 the original author or authors.
 */

package cz.silesnet.sis.sync.dao.impl;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;

import cz.silesnet.sis.sync.dao.CustomerDao;
import cz.silesnet.sis.sync.domain.Customer;
import cz.silesnet.sis.sync.mapping.CustomerRowMapper;

/**
 * Implements CustomerDao using Sping's JdbcTemplate.
 *
 * @author rsi
 */
public class JdbcCustomerDao implements CustomerDao {

  private static final String CUSTOMER_SQL = "SELECT * FROM customers WHERE id = ?";

  private JdbcTemplate template;

  public void setJdbcTemplate(JdbcTemplate template) {
    this.template = template;
  }

  public Customer find(long id) {
    Customer customer = null;
    try {
      customer = (Customer) template.queryForObject(CUSTOMER_SQL, new Object[]{id}, new CustomerRowMapper());
    } catch (DataAccessException e) {
      throw new IllegalArgumentException(e);
    }
    return customer;
  }

}
