package cz.silesnet.sis.sync.item.reader;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;

import org.junit.Test;
import org.springframework.batch.item.ExecutionContext;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

import cz.stormware.schema.response.ResponsePackItemType;
import cz.stormware.schema.type.StavType2;

public class ResponsePackItemReaderTest {

  @Test
  public void testRead() throws Exception {
    ResponsePackItemReader itemReader = new ResponsePackItemReader();
    Resource resource = new ClassPathResource("xml/invoices-response-20100313.xml");
    itemReader.setResource(resource);
    itemReader.afterPropertiesSet();

    itemReader.open(new ExecutionContext());
    ResponsePackItemType item = itemReader.read();
    assertThat(item.getState(), is(StavType2.OK));
    assertThat(item.getId(), is("2010-03-10T17:02:48.593_0000000000_134409"));
  }
}
