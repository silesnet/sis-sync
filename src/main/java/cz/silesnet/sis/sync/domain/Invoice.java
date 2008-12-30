/*
 * Copyright 2008 the original author or authors.
 */
package cz.silesnet.sis.sync.domain;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;

import edu.emory.mathcs.backport.java.util.Collections;

/**
 * Invoice data object with no logic. It contains only fields needed for
 * synchronization.
 * 
 * @author Richard Sikora
 */
public class Invoice implements ItemIdentity {

    public static final int VAT_PERCENT = 19;
    private static final BigDecimal VAT_BIG_DECIMAL = BigDecimal.valueOf(VAT_PERCENT, 2);

    private long id;
    private long customerId;
    private String number;
    private List<Item> items = new ArrayList<Item>();
    private float net;

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

    public long getCustomerId() {
        return customerId;
    }

    public void setCustomerId(long customerId) {
        this.customerId = customerId;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public class Item {
        private String name;
        private float net;

        public Item(String name, float net) {
            this.name = name;
            this.net = net;
            // automatically associates new item with the invoice
            addItem(this);
        }

        public float getVat() {
            return calculateVat(net);
        }

        public float getBrt() {
            return calculateBrt(net);
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public float getNet() {
            return net;
        }

        public void setNet(float net) {
            this.net = net;
        }
    }

    protected float calculateVat(float net) {
        BigDecimal vB = BigDecimal.valueOf(net).multiply(VAT_BIG_DECIMAL);
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
