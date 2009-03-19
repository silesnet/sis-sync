/*
 * Copyright 2009 the original author or authors.
 */

package cz.silesnet.sis.sync.domain;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.joda.time.LocalDate;

/**
 * Data class containing reminder for customer's delayed invoice payments. The
 * Reminder is immutable.
 * 
 * @author rsi
 * 
 */
public class Reminder {
    private final Customer customer;
    private final List<Invoice> invoices = new ArrayList<Invoice>();

    public Reminder(long id, String name, String email, int graceDays) {
        customer = new Customer(id, name, email, graceDays);
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

    public class Customer {
        private final long id;
        private final String name;
        private final String email;
        private final int graceDays;

        public Customer(long id, String name, String email, int graceDays) {
            super();
            this.id = id;
            this.name = name;
            this.email = email;
            this.graceDays = graceDays;
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

        public int getGraceDays() {
            return graceDays;
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
