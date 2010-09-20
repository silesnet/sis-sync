package cz.silesnet.sis.sync.domain;

/**
 * @author Richard Sikora
 */
public class InvoiceResult implements Result {

  private long sisId;
  private long spsId;
  private String number;

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

  public String getNumber() {
    return number;
  }

  public void setNumber(String number) {
    this.number = number;
  }

}
