/*
 * Copyright 2008 the original author or authors.
 */
package cz.silesnet.sis.sync.item.writer;

import java.util.ArrayList;
import java.util.List;

import org.springframework.util.StringUtils;

import cz.silesnet.sis.sync.domain.Customer;

/**
 * Implementation of {@link AbstractDataPackItemWriter} that writes Customer
 * into SPS XML import format.
 * 
 * @author sikorric
 * 
 */
public class SisCustomerItemWriter extends AbstractDataPackItemWriter<Customer> {

  public static final String ADDRESSBOOK_ELEMENT_VERSION = "1.5";
  public static final String AD_GROUP_KEY = "SIS";
  public static final int DUE_DAYS = 7;
  public static final String CONTACT_NAME_PREFIX = "Kontaktn\u00ed osoba: ";

  public SisCustomerItemWriter() {
    super();
  }

  @Override
  protected String[] dataPackItemLines(Customer item) {
    if (!(item instanceof Customer)) {
      throw new IllegalArgumentException("Item is not of Customer type.");
    }
    Customer customer = item;
    List<String> lines = new ArrayList<String>();
    // header
    lines.add(elBeg("adb:addressbook version=\"" + ADDRESSBOOK_ELEMENT_VERSION + "\""));
    // address
    lines.add(elBeg("adb:addressbookHeader"));
    // identity
    lines.add(elBeg("adb:identity"));
    lines.add(elBeg("typ:address"));
    lines.add(elValue("typ:company", customer.getName()));
    lines.add(elValue("typ:division", customer.getSupplementaryName()));
    lines.add(elValue("typ:city", customer.getCity()));
    lines.add(elValue("typ:street", customer.getStreet()));
    lines.add(elValue("typ:zip", customer.getZip()));
    lines.add(elValue("typ:ico", customer.getIco()));
    lines.add(elValue("typ:dic", customer.getDic()));
    lines.add(elEnd("typ:address"));
    lines.add(elEnd("adb:identity"));
    // other
    lines.add(elValue("adb:phone", customer.getPhone()));
    lines.add(elValue("adb:email", customer.getEmail()));
    lines.add(elValue("adb:adGroup", AD_GROUP_KEY));
    lines.add(elValue("adb:maturity", DUE_DAYS));
    lines.add(elValue("adb:agreement", customer.getSpsContract()));
    lines.add(elValue("adb:p2", "true"));
    lines.add(elValue("adb:note", CONTACT_NAME_PREFIX + customer.getContactName()));
    // update if already exists in SPS
    if (StringUtils.hasText(customer.getSymbol())) {
      lines.add(elBeg("adb:duplicityFields actualize=\"true\""));
      lines.add(elValue("adb:id", customer.getSymbol()));
      lines.add(elEnd("adb:duplicityFields"));
    }
    lines.add(elEnd("adb:addressbookHeader"));
    if (StringUtils.hasText(customer.getAccountNo()) && StringUtils.hasText(customer.getBankCode())) {
      // bank account
      lines.add(elBeg("adb:addressbookAccount"));
      lines.add(elBeg("adb:accountItem"));
      lines.add(elValue("adb:accountNumber", customer.getAccountNo()));
      lines.add(elValue("adb:bankCode", customer.getBankCode()));
      lines.add(elValue("adb:defaultAccount", "true"));
      lines.add(elEnd("adb:accountItem"));
      lines.add(elEnd("adb:addressbookAccount"));
    }
    // trailer
    lines.add(elEnd("adb:addressbook"));

    return lines.toArray(new String[lines.size()]);
  }

  @Override
  protected String[] nameSpaceLines() {
    return new String[]{"xmlns:adb=\"http://www.stormware.cz/schema/addressbook.xsd\""};
  }

}
