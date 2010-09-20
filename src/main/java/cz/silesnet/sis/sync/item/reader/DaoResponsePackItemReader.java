/*
 * Copyright 2008 the original author or authors.
 */
package cz.silesnet.sis.sync.item.reader;

import org.springframework.batch.item.ExecutionContext;
import org.springframework.batch.item.ItemStreamException;
import org.springframework.batch.item.file.ResourceAwareItemReaderItemStream;
import org.springframework.batch.item.support.AbstractItemCountingItemStreamItemReader;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.core.io.Resource;
import org.springframework.util.Assert;
import org.springframework.util.ClassUtils;

import cz.stormware.schema.response.ResponsePackItemType;

/**
 * ItemReader that reads SPS invoice import result file and does lookup to SIS
 * for bank account.
 *
 * @author Richard Sikora
 */
public abstract class DaoResponsePackItemReader<T, D>
    extends
    AbstractItemCountingItemStreamItemReader<T>
    implements
    ResourceAwareItemReaderItemStream<T>,
    InitializingBean {

  private final ResponsePackItemReader itemReader;
  protected D dao;

  public DaoResponsePackItemReader() {
    setName(ClassUtils.getShortName(DaoResponsePackItemReader.class));
    itemReader = new ResponsePackItemReader();
  }

  public void setDao(D dao) {
    this.dao = dao;
  }

  @Override
  protected T doRead() throws Exception {
    ResponsePackItemType responseItem = itemReader.read();
    if (responseItem == null)
      return null;
    return doReadWithDao(responseItem, dao);
  }

  protected abstract T doReadWithDao(ResponsePackItemType responseItem, D dao);

  @Override
  public void afterPropertiesSet() throws Exception {
    itemReader.afterPropertiesSet();
    Assert.notNull(dao, "The Dao must not be null.");
  }

  @Override
  protected void doClose() throws Exception {
    itemReader.close();
  }

  @Override
  public void open(ExecutionContext executionContext) throws ItemStreamException {
    itemReader.open(executionContext);
    super.open(executionContext);
  }

  @Override
  protected void doOpen() throws Exception {
    // NOP, we are overriding open()
  }

  @Override
  public void setResource(Resource resource) {
    itemReader.setResource(resource);
  }

}
