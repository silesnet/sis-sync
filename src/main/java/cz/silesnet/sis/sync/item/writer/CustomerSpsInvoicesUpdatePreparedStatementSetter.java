/*
 * Copyright 2008 the original author or authors.
 */
package cz.silesnet.sis.sync.item.writer;

import java.sql.PreparedStatement;
import java.sql.SQLException;

import org.springframework.batch.item.database.ItemPreparedStatementSetter;

import cz.silesnet.sis.sync.domain.Customer;

/**
 * Sets Customer invoices parameters to SQL PreparedStatement.
 *
 * @author Richard Sikora
 */
public class CustomerSpsInvoicesUpdatePreparedStatementSetter
    implements
    ItemPreparedStatementSetter<Customer> {

  /**
   * Maps Customer SIS bank account to SPS SQL update command. SQL updates all
   * customers SPS invoices that are not paired.
   * <p/>
   * UPDATE FA SET Ucet = ?, KodBanky = ? WHERE RefAD = ? AND DatLikv IS NULL
   *
   * @param item Customer
   * @param ps   SQL update command wrapped in PreparedStatement
   */
  public void setValues(Customer customer, PreparedStatement ps) throws SQLException {
    ps.setString(1, customer.getAccountNo());
    ps.setString(2, customer.getBankCode());
    ps.setInt(3, Integer.valueOf(customer.getSymbol()));
  }
}
