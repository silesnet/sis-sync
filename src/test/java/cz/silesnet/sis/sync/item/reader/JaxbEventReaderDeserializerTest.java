package cz.silesnet.sis.sync.item.reader;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import javax.xml.stream.XMLEventReader;

import org.junit.Test;

public class JaxbEventReaderDeserializerTest {

  @Test
  public void testDeserializeFragment() throws Exception {
    JaxbEventReaderDeserializer deserializer = new JaxbEventReaderDeserializer();
    XMLEventReader eventReader = mock(XMLEventReader.class);
    JaxbPartialUnmarshaller unmarshaller = mock(JaxbPartialUnmarshaller.class);
    Object fragment = new Object();
    when(unmarshaller.unmarshal(eventReader)).thenReturn(fragment);

    deserializer.setPartialUnmarshaller(unmarshaller);

    Object deserializedFragment = deserializer.deserializeFragment(eventReader);

    assertThat(deserializedFragment, is(sameInstance(fragment)));
  }
}
