package cz.silesnet.sis.sync;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * User: der3k
 * Date: 9.6.11
 * Time: 23:01
 */
public class LoggingTest {
  @Test
  public void configuration() throws Exception {
    Logger logger = LoggerFactory.getLogger(LoggingTest.class);
    logger.debug("Hello {}!", "world");
    logger.error("Error {}!", "by mail");
  }
}
