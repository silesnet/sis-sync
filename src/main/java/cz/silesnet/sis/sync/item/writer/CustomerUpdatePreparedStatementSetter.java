/*
 * Copyright 2008 the original author or authors.
 */
package cz.silesnet.sis.sync.item.writer;

import cz.silesnet.sis.sync.item.reader.ResponseId;
import cz.stormware.schema.response.ResponsePackItemType;
import cz.stormware.schema.type.StavType2;
import org.springframework.batch.item.database.ItemPreparedStatementSetter;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Date;

/**
 * Sets parameters for Customer update SQL.
 *
 * @author sikorric
 */
public class CustomerUpdatePreparedStatementSetter implements ItemPreparedStatementSetter<ResponsePackItemType> {

  /**
   * Maps Customer members to SQL update command.
   * <p/>
   * UPDATE customers SET symbol = ?, synchronized = ? WHERE id = ?
   *
   * @param item Customer object
   * @param ps   SQL wrapped in PreparedStatement
   */
  public void setValues(ResponsePackItemType item, PreparedStatement ps) throws SQLException {
    String responseId = item.getId();
    ResponseId sisId = ResponseId.of(responseId);
    if (item.getState() != StavType2.OK)
      throw new CustomerSpsImportException(
          String.format("Customer import error [id='%d', raw='%s']", sisId.id(), responseId));
    if (item.getAddressbookResponse().getState() != StavType2.OK)
      throw new CustomerSpsImportException(
          String.format("Failed to import the customer [id='%d', raw='%s']", sisId.id(), responseId));
    String spsId = item.getAddressbookResponse().getProducedDetails().getId();
    ps.setString(1, spsId);
    ps.setTimestamp(2, new Timestamp((new Date()).getTime()));
    ps.setLong(3, sisId.id());
  }
}
