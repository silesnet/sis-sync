/*
 * Copyright 2008 the original author or authors.
 */
package cz.silesnet.sis.sync.item.writer;

import cz.silesnet.sis.sync.item.reader.ResponseId;
import cz.stormware.schema.response.ResponsePackItemType;
import cz.stormware.schema.type.StavType2;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.database.ItemPreparedStatementSetter;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Date;

/**
 * Sets Invoice response parameters to SQL {@link PreparedStatement}.
 *
 * @author Richard Sikora
 */
public class InvoiceUpdatePreparedStatementSetter implements ItemPreparedStatementSetter<ResponsePackItemType> {

  private static Logger logger = LoggerFactory.getLogger(InvoiceUpdatePreparedStatementSetter.class);

  /**
   * Maps Invoice response to SQL update command.
   * <p/>
   * UPDATE invoices SET synchronized = ? WHERE id = ?
   *
   * @param responseItem {@link ResponsePackItemType}
   * @param ps   {@link PreparedStatement}
   */
  public void setValues(ResponsePackItemType responseItem, PreparedStatement ps) throws SQLException {
    String responseId = responseItem.getId();
    ResponseId itemId = ResponseId.of(responseId);
    if (responseItem.getState() != StavType2.OK || responseItem.getInvoiceResponse().getState() != StavType2.OK)
      throw new IllegalArgumentException("Failed to import the invoice [id='" + itemId.id() + "', raw='" + responseId + "']");
    Timestamp now = new Timestamp(new Date().getTime());
    ps.setTimestamp(1, now);

    ps.setLong(2, itemId.id());
  }

}
