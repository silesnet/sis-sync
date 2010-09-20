/*
 * Copyright 2009 the original author or authors.
 */

package cz.silesnet.sis.sync.domain;

/**
 * SPS Cutomer result data object.
 *
 * @author rsi
 */
public class CustomerResult implements Result {

  private long sisId;
  private long spsId;
  private String name;

  public long getSisId() {
    return sisId;
  }

  public void setSisId(long sisId) {
    this.sisId = sisId;
  }

  public long getSpsId() {
    return spsId;
  }

  public void setSpsId(long spsId) {
    this.spsId = spsId;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

}
