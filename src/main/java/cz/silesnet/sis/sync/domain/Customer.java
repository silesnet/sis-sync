/*
 * Copyright 2008 the original author or authors.
 */
package cz.silesnet.sis.sync.domain;

/**
 * Customer data object with no logic. It contain only fields needed for
 * synchronization.
 * 
 * @author Richard Sikora
 */
public class Customer {

    private String name;
    private String symbol;

    /**
     *  
     */
    public Customer() {
        // TODO Auto-generated constructor stub
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

}
