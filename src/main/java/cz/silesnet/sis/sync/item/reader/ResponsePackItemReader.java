package cz.silesnet.sis.sync.item.reader;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;

import org.springframework.batch.item.ExecutionContext;
import org.springframework.batch.item.ItemStreamException;
import org.springframework.batch.item.file.ResourceAwareItemReaderItemStream;
import org.springframework.batch.item.support.AbstractBufferedItemReaderItemStream;
import org.springframework.batch.item.xml.StaxEventItemReader;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.core.io.Resource;
import org.springframework.util.ClassUtils;

import cz.stormware.schema.response.ResponsePackItemType;

public class ResponsePackItemReader extends AbstractBufferedItemReaderItemStream
    implements
      ResourceAwareItemReaderItemStream,
      InitializingBean {

  private static final JAXBContext jaxbContext;
  static {
    try {
      jaxbContext = JAXBContext.newInstance("cz.stormware.schema.response");
    } catch (JAXBException e) {
      throw new RuntimeException(e);
    }
  }

  private final StaxEventItemReader staxItemReader;

  public ResponsePackItemReader() {
    setName(ClassUtils.getShortName(ResponsePackItemReader.class));

    JaxbEventReaderDeserializer deserializer = new JaxbEventReaderDeserializer();
    deserializer.setContext(jaxbContext);
    deserializer.setFragmentClass(ResponsePackItemType.class);

    staxItemReader = new StaxEventItemReader();
    staxItemReader.setFragmentRootElementName("responsePackItem");
    staxItemReader.setFragmentDeserializer(deserializer);
  }

  @Override
  public void setResource(Resource resource) {
    staxItemReader.setResource(resource);
  }

  @Override
  public void afterPropertiesSet() throws Exception {
    staxItemReader.afterPropertiesSet();
  }

  @Override
  protected Object doRead() throws Exception {
    return staxItemReader.read();
  }

  @Override
  public void close(ExecutionContext executionContext) throws ItemStreamException {
    super.close(executionContext);
    staxItemReader.close(executionContext);
  }

  @Override
  public void open(ExecutionContext executionContext) throws ItemStreamException {
    staxItemReader.open(executionContext);
    super.open(executionContext);
  }

  @Override
  protected void doClose() throws Exception {
    // NOP, we override close()
  }

  @Override
  protected void doOpen() throws Exception {
    // NOP, we override open()
  }

}
