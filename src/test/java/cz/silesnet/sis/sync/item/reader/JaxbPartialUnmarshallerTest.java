package cz.silesnet.sis.sync.item.reader;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.Unmarshaller;
import javax.xml.stream.XMLEventReader;

import org.junit.Test;
import org.springframework.xml.transform.StaxSource;

public class JaxbPartialUnmarshallerTest {

  @SuppressWarnings("unchecked")
  @Test
  public void testUnmarshal() throws Exception {
    JaxbPartialUnmarshaller unmarshaller = new JaxbPartialUnmarshaller();

    // configure JAXB Unmarshaller
    JAXBContext context = mock(JAXBContext.class);
    Unmarshaller jaxbUnmarshaller = mock(Unmarshaller.class);
    when(context.createUnmarshaller()).thenReturn(jaxbUnmarshaller);
    unmarshaller.setContext(context);

    // configure fragment class
    Class<Object> fragmentClass = Object.class;
    unmarshaller.setFragmentClass(fragmentClass);

    // record behavior
    XMLEventReader eventReader = mock(XMLEventReader.class);
    StaxSource source = mock(StaxSource.class);
    when(source.getXMLEventReader()).thenReturn(eventReader);
    JAXBElement<Object> fragmentElement = mock(JAXBElement.class);
    Object fragment = new Object();
    when(fragmentElement.getValue()).thenReturn(fragment);
    when(jaxbUnmarshaller.unmarshal(eventReader, fragmentClass)).thenReturn(fragmentElement);

    // test
    assertThat(unmarshaller.unmarshal(source), is(sameInstance(fragment)));
  }
}
