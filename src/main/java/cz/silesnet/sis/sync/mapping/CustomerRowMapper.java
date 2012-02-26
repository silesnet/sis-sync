/*
 * Copyright 2008 the original author or authors.
 */
package cz.silesnet.sis.sync.mapping;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import cz.silesnet.sis.sync.domain.Customer;

/**
 * Maps JDBC SIS customer result set to Customer entity.
 *
 * @author sikorric
 */
public class CustomerRowMapper implements RowMapper {

  public static final String ID_COLUMN = "id";
  public static final String SYMBOL_COLUMN = "symbol";
  public static final String NAME_COLUMN = "name";
  public static final String SUPPLEMENTARY_NAME_COLUMN = "supplementary_name";
  public static final String CONTACT_NAME_COLUMN = "contact_name";
  public static final String CITY_COLUMN = "city";
  public static final String STREET_COLUMN = "street";
  public static final String ZIP_COLUMN = "postal_code";
  public static final String ICO_COLUMN = "public_id";
  public static final String DIC_COLUMN = "dic";
  public static final String PHONE_COLUMN = "phone";
  public static final String EMAIL_COLUMN = "email";
  public static final String CONTRACT_COLUMN = "variable";
  public static final String ACCOUNT_NO_COLUMN = "account_no";
  public static final String BANK_CODE_COLUMN = "bank_no";

  public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
    Customer customer = new Customer();
    customer.setId(rs.getLong(ID_COLUMN));
    customer.setSymbol(rs.getString(SYMBOL_COLUMN));
    customer.setName(rs.getString(NAME_COLUMN));
    customer.setSupplementaryName(rs.getString(SUPPLEMENTARY_NAME_COLUMN));
    customer.setContactName(rs.getString(CONTACT_NAME_COLUMN));
    customer.setCity(rs.getString(CITY_COLUMN));
    customer.setStreet(rs.getString(STREET_COLUMN));
    customer.setZip(rs.getString(ZIP_COLUMN));
    customer.setIco(rs.getString(ICO_COLUMN));
    customer.setDic(rs.getString(DIC_COLUMN));
    customer.setPhone(rs.getString(PHONE_COLUMN));
    customer.setEmail(rs.getString(EMAIL_COLUMN));
    customer.setContract(rs.getString(CONTRACT_COLUMN));
    customer.setAccountNo(rs.getString(ACCOUNT_NO_COLUMN));
    customer.setBankCode(rs.getString(BANK_CODE_COLUMN));
    return customer;
  }

}
