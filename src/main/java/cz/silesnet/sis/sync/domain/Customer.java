/*
 * Copyright 2008 the original author or authors.
 */
package cz.silesnet.sis.sync.domain;

/**
 * Customer data object with no logic. It contain only fields needed for synchronization.
 * 
 * @author Richard Sikora
 */
public class Customer {

    private long id;
    private String name;
    private String symbol;
    private String city;

    public Customer() {
        // TODO Auto-generated constructor stub
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    /**
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * @param name
     *            the name to set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @return the symbol
     */
    public String getSymbol() {
        return symbol;
    }

    /**
     * @param symbol
     *            the symbol to set
     */
    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

}
