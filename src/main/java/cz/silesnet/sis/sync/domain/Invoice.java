/*
 * Copyright 2008 the original author or authors.
 */
package cz.silesnet.sis.sync.domain;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.joda.time.DateTime;

/**
 * Invoice data object. It contains only fields needed for synchronization.
 *
 * @author Richard Sikora
 */
public class Invoice implements ItemIdentity, Result {

  private long id;
  private long spsId;
  private String number;
  private DateTime date;
  private DateTime dueDate;
  private long invoicingId;
  private String customerName;
  private DateTime periodFrom;
  private DateTime periodTo;
  private final List<Item> items = new ArrayList<Item>();
  private Customer customer;

  private void addItem(Item item) {
    if (item == null)
      throw new IllegalArgumentException(new NullPointerException());
    items.add(item);
  }

  public List<Item> getItems() {
    return Collections.unmodifiableList(items);
  }

  public long getId() {
    return id;
  }

  public long getSisId() {
    return id;
  }

  public void setId(long id) {
    this.id = id;
  }

  public String getNumber() {
    return number;
  }

  public void setNumber(String number) {
    this.number = number;
  }

  public DateTime getDate() {
    return date;
  }

  public void setDate(DateTime date) {
    this.date = date;
  }

  public DateTime getDueDate() {
    return dueDate;
  }

  public void setDueDate(DateTime dueDate) {
    this.dueDate = dueDate;
  }

  public long getInvoicingId() {
    return invoicingId;
  }

  public void setInvoicingId(long invoicingId) {
    this.invoicingId = invoicingId;
  }

  public String getCustomerName() {
    return customerName;
  }

  public void setCustomerName(String customerName) {
    this.customerName = customerName;
  }

  public DateTime getPeriodFrom() {
    return periodFrom;
  }

  public void setPeriodFrom(DateTime periodFrom) {
    this.periodFrom = periodFrom;
  }

  public DateTime getPeriodTo() {
    return periodTo;
  }

  public void setPeriodTo(DateTime periodTo) {
    this.periodTo = periodTo;
  }

  public Customer getCustomer() {
    return customer;
  }

  public void setCustomer(Customer customer) {
    this.customer = customer;
  }

  public long getSpsId() {
    return spsId;
  }

  public void setSpsId(long spsId) {
    this.spsId = spsId;
  }

  public class Item {
    private final String text;
    private final float amount;
    private final int price;
    private final boolean isDisplayUnit;
    private final boolean isIncludeVat;

    public Item(String text, float amount, int price) {
      this(text, amount, price, true, true);
    }

    public Item(String text, float amount, int price, boolean isDisplayUnit, boolean isIncludeVat) {
      this.text = text;
      this.amount = amount;
      this.price = price;
      this.isDisplayUnit = isDisplayUnit;
      this.isIncludeVat = isIncludeVat;
      // automatically associates new item with the invoice
      addItem(this);
    }

    public String getText() {
      return this.text;
    }

    public float getAmount() {
      return amount;
    }

    public int getPrice() {
      return price;
    }

    public boolean isDisplayUnit() {
      return isDisplayUnit;
    }

    public boolean isIncludeVat() {
      return isIncludeVat;
    }
  }

}
