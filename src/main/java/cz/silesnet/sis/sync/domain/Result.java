/*
 * Copyright 2008 the original author or authors.
 */
package cz.silesnet.sis.sync.domain;

/**
 * Interface describing item's SPS import result identities.
 * 
 * @author Richard Sikora
 * 
 */
public interface Result {

    /**
     * Returns SIS item id.
     * 
     * @return item id
     */
    long getSisId();

    /**
     * Returns SPS item id.
     * 
     * @return item id
     */
    long getSpsId();
}
