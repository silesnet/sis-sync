/*
 * Copyright 2008 the original author or authors.
 */
package cz.silesnet.sis.sync.domain;

/**
 * Enable clients to get Item identity.
 * 
 * @author sikorric
 * 
 */
public interface ItemIdentity {
    /**
     * Returns item unique id.
     * 
     * @return item id
     */
    long getId();
}
