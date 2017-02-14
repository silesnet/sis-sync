package cz.silesnet.sis.sync.item.reader;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;

import org.junit.Test;

public class ResponseIdTest {

  private static final String TEXT_ID = "2010-03-10T17:02:48.593_0000000000_134409";
  private static final ResponseId RID = ResponseId.of(TEXT_ID);

  @Test
  public void testTimeStamp() throws Exception {
    assertThat(RID.timeStamp(), is("2010-03-10T17:02:48.593"));
  }

  @Test
  public void testSequenceNo() throws Exception {
    assertThat(RID.sequenceNo(), is("0000000000"));
  }

  @Test
  public void testId() throws Exception {
    assertThat(RID.id(), is(134409L));
  }

  @Test
  public void testTextId() throws Exception {
    assertThat(RID.textId(), is(TEXT_ID));
  }
}
