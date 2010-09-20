/*
 * Copyright 2008 the original author or authors.
 */
package cz.silesnet.sis.sync.item.writer;

import java.sql.PreparedStatement;
import java.sql.SQLException;

import org.springframework.batch.item.database.ItemPreparedStatementSetter;

import cz.silesnet.sis.sync.domain.Invoice;

/**
 * Sets Invoice parameters to SQL PreparedStatement.
 *
 * @author Richard Sikora
 */
public class SpsInvoiceUpdatePreparedStatementSetter
    implements
    ItemPreparedStatementSetter<Invoice> {

  /**
   * Maps Invoice SIS bank account to SPS SQL update command.
   * <p/>
   * UPDATE FA SET account_number = ?, bank_code = ? WHERE id = ?
   *
   * @param item Invoice
   * @param ps   SQL update command wrapped in PreparedStatement
   */
  public void setValues(Invoice invoice, PreparedStatement ps) throws SQLException {
    ps.setString(1, invoice.getCustomer().getAccountNo());
    ps.setString(2, invoice.getCustomer().getBankCode());
    ps.setInt(3, (int) invoice.getSpsId());
  }

}
