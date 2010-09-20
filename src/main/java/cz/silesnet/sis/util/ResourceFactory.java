/*
 * Copyright 2009 the original author or authors.
 */

package cz.silesnet.sis.util;

import org.springframework.core.io.Resource;

/**
 * Factory for creating resource in batch runtime context.
 *
 * @author rsi
 */
public interface ResourceFactory {
  /**
   * Return resource within batch runtime context.
   *
   * @param fileSuffix
   * @return new resource
   */
  public Resource createInstance(String fileSuffix);
}
