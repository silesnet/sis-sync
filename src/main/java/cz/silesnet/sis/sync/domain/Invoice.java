/*
 * Copyright 2008 the original author or authors.
 */
package cz.silesnet.sis.sync.domain;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;

import org.joda.time.DateTime;

import edu.emory.mathcs.backport.java.util.Collections;

/**
 * Invoice data object. It contains only fields needed for synchronization.
 * 
 * @author Richard Sikora
 */
public class Invoice implements ItemIdentity {

    public static final String INVOICE_TEXT = "Invoice text";
    private static final int DEFAULT_VAT_RATE = 19;

    private long id;
    private String number;
    private DateTime date;
    private DateTime dueDate;
    private String text = INVOICE_TEXT;
    private long invoicingId;
    private String customerName;
    private DateTime periodFrom;
    private DateTime periodTo;
    private int vatRate = DEFAULT_VAT_RATE;
    private String hashCode;
    private List<Item> items = new ArrayList<Item>();
    private float net;
    private BigDecimal vatValue = BigDecimal.valueOf(vatRate, 2);
    private Customer customer;

    private void addItem(Item item) {
        if (item == null)
            throw new IllegalArgumentException(new NullPointerException());
        if (items.add(item)) {
            net += item.getNet();
        }
    }

    public float getVat() {
        return calculateVat(net);
    }

    public float getBrt() {
        return calculateBrt(net);
    }

    public float getRounding() {
        return calculateRounding(net);
    }

    @SuppressWarnings("unchecked")
    public List<Item> getItems() {
        return Collections.unmodifiableList(items);
    }

    public float getNet() {
        return net;
    }

    public long getId() {
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

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
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

    public int getVatRate() {
        return vatRate;
    }

    public void setVatRate(int vatRate) {
        this.vatRate = vatRate;
        vatValue = BigDecimal.valueOf(vatRate, 2);
    }

    public String getHashCode() {
        return hashCode;
    }

    public void setHashCode(String hashCode) {
        this.hashCode = hashCode;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public class Item {
        private String text;
        private float amount;
        private int price;
        private boolean isDisplayUnit;
        private float net;

        public Item(String text, float amount, int price) {
            this(text, amount, price, true);
        }

        public Item(String text, float amount, int price, boolean isDisplayUnit) {
            this.text = text;
            this.amount = amount;
            this.price = price;
            this.net = amount * ((float) price);
            this.isDisplayUnit = isDisplayUnit;
            // automatically associates new item with the invoice
            addItem(this);
        }

        public float getVat() {
            return calculateVat(net);
        }

        public float getBrt() {
            return calculateBrt(net);
        }

        public String getText() {
            return this.text;
        }

        public float getNet() {
            return this.net;
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

    }

    protected float calculateVat(float net) {
        BigDecimal vB = BigDecimal.valueOf(net).multiply(vatValue);
        return vB.setScale(2, RoundingMode.HALF_UP).floatValue();
    }

    protected float calculateBrt(float net) {
        return net + calculateVat(net);
    }

    protected float calculateRounding(float net) {
        BigDecimal bB = BigDecimal.valueOf(calculateBrt(net));
        BigDecimal rB = bB.setScale(0, RoundingMode.HALF_UP).subtract(bB);
        return rB.setScale(2, RoundingMode.HALF_UP).floatValue();
    }
}
