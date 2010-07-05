/*
 * Copyright 2008 the original author or authors.
 */
package cz.silesnet.sis.sync.item.writer;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Date;

import cz.silesnet.sis.sync.item.reader.ResponseId;
import cz.stormware.schema.response.ResponsePackItemType;
import org.springframework.batch.item.database.ItemPreparedStatementSetter;

import cz.silesnet.sis.sync.domain.CustomerResult;

/**
 * Sets parameters for Customer update SQL.
 *
 * @author sikorric
 *
 */
public class CustomerUpdatePreparedStatementSetter
    implements
      ItemPreparedStatementSetter<ResponsePackItemType> {
  /**
   * Maps Customer members to SQL update command.
   * <p>
   * UPDATE customers SET symbol = ?, synchronized = ? WHERE id = ?
   *
   * @param item
   *          Customer object
   * @param ps
   *          SQL wrapped in PreparedStatement
   */
  public void setValues(ResponsePackItemType item, PreparedStatement ps) throws SQLException {
    ResponseId sisId = ResponseId.of(item.getId());
    String spsId = item.getAddressbookResponse().getProducedDetails().getId();
    ps.setString(1, spsId);
    ps.setTimestamp(2, new Timestamp((new Date()).getTime()));
    ps.setLong(3, sisId.id());
  }
}
