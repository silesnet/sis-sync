/*
 * Copyright 2008 the original author or authors.
 */
package cz.silesnet.sis.sync.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.joda.time.DateTime;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import cz.silesnet.sis.sync.dao.InvoiceDao;
import cz.silesnet.sis.sync.domain.Customer;
import cz.silesnet.sis.sync.domain.Invoice;
import cz.silesnet.sis.sync.mapping.CustomerRowMapper;

/**
 * Implements InvoiceDao using Spring's JdbcTemplate.
 *
 * @author sikorric
 */
public class JdbcInvoiceDao implements InvoiceDao {

  private static final String INVOICE_SQL = "SELECT * FROM bills WHERE id = ?";
  private JdbcTemplate template;

  public void setJdbcTemplate(JdbcTemplate template) {
    this.template = template;
  }

  public Invoice find(long id) {
    Invoice invoice = null;
    try {
      invoice = (Invoice) template.queryForObject(INVOICE_SQL, new Object[]{id}, new InvoiceRowMapper());
    } catch (DataAccessException e) {
      throw new IllegalArgumentException(e);
    }
    return invoice;
  }

  /**
   * Maps result set to Invoice object. Uses JdbcTemplate to extract invoice
   * items.
   *
   * @author sikorric
   */
  private class InvoiceRowMapper implements RowMapper {

    private static final String ID_COLUMN = "id";
    private static final String NUMBER_COLUMN = "number";
    private static final String DATE_COLUMN = "billing_date";
    private static final String DUE_DATE_COLUMN = "purge_date";
    private static final String CUSTOMER_ID_COLUMN = "customer_id";
    private static final String INVOICING_ID_COLUMN = "invoicing_id";
    private static final String CUSTOMER_NAME_COLUMN = "customer_name";
    private static final String PERIOD_FROM_COLUMN = "period_from";
    private static final String PERIOD_TO_COLUMN = "period_to";
    private static final String ITEMS_SQL = "SELECT * FROM bill_items WHERE bill_id = ?";
    private static final String ITEMS_TEXT_COLUMN = "text";
    private static final String ITEMS_AMOUNT_COLUMN = "amount";
    private static final String ITEMS_PRICE_COLUMN = "price";
    private static final String ITEMS_DISPLAY_UNIT_COLUMN = "is_display_unit";
    private static final String ITEMS_INCLUDE_VAT= "dph";
    private static final String CUSTOMERS_SYMBOL_SQL = "SELECT * FROM customers WHERE id = ?";

    /**
     * Maps result set to Invoice object. Retrieves invoice items and
     * associates them with the invoice.
     */
    public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
      final Invoice invoice = new Invoice();
      invoice.setId(rs.getLong(ID_COLUMN));
      invoice.setNumber(rs.getString(NUMBER_COLUMN));
      invoice.setDate(new DateTime(rs.getTimestamp(DATE_COLUMN)));
      invoice.setDueDate(new DateTime(rs.getTimestamp(DUE_DATE_COLUMN)));
      invoice.setInvoicingId(rs.getLong(INVOICING_ID_COLUMN));
      invoice.setCustomerName(rs.getString(CUSTOMER_NAME_COLUMN));
      invoice.setPeriodFrom(new DateTime(rs.getTimestamp(PERIOD_FROM_COLUMN)));
      invoice.setPeriodTo(new DateTime(rs.getTimestamp(PERIOD_TO_COLUMN)));
      // retrieve the customer
      Customer customer = (Customer) template.queryForObject(CUSTOMERS_SYMBOL_SQL, new Object[]{rs
          .getLong(CUSTOMER_ID_COLUMN)}, new CustomerRowMapper());
      invoice.setCustomer(customer);
      // retrieve invoice items from database by invoice_id
      template.query(ITEMS_SQL, new Object[]{invoice.getId()}, new RowMapper() {
        public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
          // result set contains row from invoice items table
          invoice.new Item(rs.getString(ITEMS_TEXT_COLUMN), rs.getFloat(ITEMS_AMOUNT_COLUMN), rs
              .getInt(ITEMS_PRICE_COLUMN), rs.getBoolean(ITEMS_DISPLAY_UNIT_COLUMN), rs.getBoolean(ITEMS_INCLUDE_VAT));
          // just creating new Item automatically associates it with
          // the invoice
          // result is not read, null can be returned
          return null;
        }
      });
      return invoice;
    }
  }

}
