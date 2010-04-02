package cz.silesnet.sis.sync.item.reader;

public class ResponseId {

  private final String textId;
  private final String timeStamp;
  private final String sequenceNo;
  private final int id;

  private ResponseId(String textId) {
    this.textId = textId;
    String[] fields = textId.split("_");
    timeStamp = fields[0];
    sequenceNo = fields[1];
    id = Integer.valueOf(fields[2]);
  }

  public static ResponseId of(String id) {
    return new ResponseId(id);
  }

  public String timeStamp() {
    return timeStamp;
  }

  public String sequenceNo() {
    return sequenceNo;
  }

  public int id() {
    return id;
  }

  public String textId() {
    return textId;
  }
}
