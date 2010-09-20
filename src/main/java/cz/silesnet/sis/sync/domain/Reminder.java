/*
 * Copyright 2009 the original author or authors.
 */

package cz.silesnet.sis.sync.domain;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import org.joda.time.LocalDate;

/**
 * Data class containing reminder for customer's delayed invoice payments. The
 * Reminder is immutable.
 *
 * @author rsi
 */
public class Reminder {
  private final Customer customer;
  private final List<Invoice> invoices = new ArrayList<Invoice>();

  public Reminder(long id, String name, String email, String address) {
    customer = new Customer(id, name, email, address);
  }

  public Customer getCustomer() {
    return customer;
  }

  public List<Invoice> getInvoices() {
    return Collections.unmodifiableList(invoices);
  }

  public void addInvoice(Invoice invoice) {
    if (invoice.getCustomerId() != customer.getId()) {
      throw new IllegalArgumentException("The invoice " + invoice.getNumber()
          + " does not belong to the customer " + customer.getName() + ".");
    }
    invoices.add(invoice);
  }

  @Override
  public String toString() {
    StringBuilder reminder = new StringBuilder();
    reminder.append(getCustomer().getId()).append(": ").append(getCustomer().getName());
    reminder.append(" <").append(getCustomer().getEmail()).append("> [");
    for (Iterator<Invoice> invoiceIterator = getInvoices().iterator(); invoiceIterator.hasNext();) {
      Invoice invoice = invoiceIterator.next();
      reminder.append(invoice.getNumber()).append(": ").append(invoice.getDueAmount());
      if (invoiceIterator.hasNext()) {
        reminder.append(", ");
      }
    }
    reminder.append("]");
    return reminder.toString();
  }

  public class Customer {
    private final long id;
    private final String name;
    private final String email;
    private final String address;

    public Customer(long id, String name, String email, String address) {
      super();
      this.id = id;
      this.name = name;
      this.email = email;
      this.address = address;
    }

    public long getId() {
      return id;
    }

    public String getName() {
      return name;
    }

    public String getEmail() {
      return email;
    }

    public String getAddress() {
      return address;
    }
  }

  public class Invoice {
    private final long id;
    private final long customerId;
    private final String number;
    private final String referenceNumber;
    private final LocalDate dueDate;
    private final BigDecimal totalAmount;
    private final BigDecimal dueAmount;

    public Invoice(long id, long customerId, String number, String referenceNumber, LocalDate dueDate,
                   BigDecimal totalAmount, BigDecimal dueAmount) {
      super();
      this.id = id;
      this.customerId = customerId;
      this.number = number;
      this.referenceNumber = referenceNumber;
      this.dueDate = dueDate;
      this.totalAmount = totalAmount;
      this.dueAmount = dueAmount;
    }

    public long getId() {
      return id;
    }

    public long getCustomerId() {
      return customerId;
    }

    public String getNumber() {
      return number;
    }

    public String getReferenceNumber() {
      return referenceNumber;
    }

    public LocalDate getDueDate() {
      return dueDate;
    }

    public BigDecimal getTotalAmount() {
      return totalAmount;
    }

    public BigDecimal getDueAmount() {
      return dueAmount;
    }

  }
}
