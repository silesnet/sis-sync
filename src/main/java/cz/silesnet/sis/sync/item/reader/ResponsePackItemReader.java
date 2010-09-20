package cz.silesnet.sis.sync.item.reader;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;

import org.apache.log4j.Logger;
import org.springframework.batch.item.ExecutionContext;
import org.springframework.batch.item.ItemStreamException;
import org.springframework.batch.item.file.ResourceAwareItemReaderItemStream;
import org.springframework.batch.item.support.AbstractItemCountingItemStreamItemReader;
import org.springframework.batch.item.xml.StaxEventItemReader;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.core.io.Resource;
import org.springframework.util.ClassUtils;

import cz.stormware.schema.response.ResponsePackItemType;

public class ResponsePackItemReader
    extends
    AbstractItemCountingItemStreamItemReader<ResponsePackItemType>
    implements
    ResourceAwareItemReaderItemStream<ResponsePackItemType>,
    InitializingBean {
  private static final Logger log = Logger.getLogger(ResponsePackItemReader.class);

  private static final JAXBContext jaxbContext;

  static {
    try {
      jaxbContext = JAXBContext.newInstance("cz.stormware.schema.response");
    } catch (JAXBException e) {
      throw new RuntimeException(e);
    }
  }

  private final StaxEventItemReader<ResponsePackItemType> staxItemReader;

  public ResponsePackItemReader() {
    setName(ClassUtils.getShortName(ResponsePackItemReader.class));

    JaxbPartialUnmarshaller unmarshaller = new JaxbPartialUnmarshaller();
    unmarshaller.setContext(jaxbContext);
    unmarshaller.setFragmentClass(ResponsePackItemType.class);

    staxItemReader = new StaxEventItemReader<ResponsePackItemType>();
    staxItemReader.setFragmentRootElementName("responsePackItem");
    staxItemReader.setUnmarshaller(unmarshaller);
  }

  @Override
  public void setResource(Resource resource) {
    staxItemReader.setResource(resource);
    log.debug("Resource was set :" + resource);
  }

  @Override
  public void afterPropertiesSet() throws Exception {
    staxItemReader.afterPropertiesSet();
  }

  @Override
  protected ResponsePackItemType doRead() throws Exception {
    return staxItemReader.read();
  }

  @Override
  public void open(ExecutionContext executionContext) throws ItemStreamException {
    staxItemReader.open(executionContext);
    super.open(executionContext);
  }

  @Override
  protected void doOpen() throws Exception {
    // NOP, we are overriding open()
  }

  @Override
  protected void doClose() throws Exception {
    staxItemReader.close();
  }

}
