/*
 * Copyright 2008 the original author or authors.
 */
package cz.silesnet.sis.sync.item.writer;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Date;

import org.springframework.batch.item.database.ItemPreparedStatementSetter;

import cz.silesnet.sis.sync.item.reader.ResponseId;
import cz.stormware.schema.response.ResponsePackItemType;

/**
 * Sets Invoice response parameters to SQL {@link PreparedStatement}.
 *
 * @author Richard Sikora
 */
public class InvoiceUpdatePreparedStatementSetter
    implements
    ItemPreparedStatementSetter<ResponsePackItemType> {

  /**
   * Maps Invoice response to SQL update command.
   * <p/>
   * UPDATE invoices SET synchronized = ? WHERE id = ?
   *
   * @param item {@link ResponsePackItemType}
   * @param ps   {@link PreparedStatement}
   */
  public void setValues(ResponsePackItemType responseItem, PreparedStatement ps)
      throws SQLException {
    Timestamp now = new Timestamp(new Date().getTime());
    ps.setTimestamp(1, now);

    ResponseId responseId = ResponseId.of(responseItem.getId());
    ps.setLong(2, responseId.id());
  }

}
