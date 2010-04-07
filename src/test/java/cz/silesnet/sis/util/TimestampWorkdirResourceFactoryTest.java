package cz.silesnet.sis.util;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;

import java.io.File;
import java.io.IOException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;

public class TimestampWorkdirResourceFactoryTest {

  private static Log log = LogFactory.getLog(TimestampWorkdirResourceFactoryTest.class);

  private static final String FILE_SUFFIX = "suffix";

  private TimestampWorkdirResourceFactory factory;

  @Before
  public void setUp() throws Exception {
    factory = new TimestampWorkdirResourceFactory();
  }

  @After
  public void tearDown() throws Exception {
    factory = null;
  }

  @Test
  public void testDefaultWorkdir() throws Exception {
    Resource resource = factory.createInstance(FILE_SUFFIX);
    log.debug(resource);
    assertEquals((new File(".")).getCanonicalPath(), getResourceDir(resource));
  }

  @Test
  public void testSetWorkDirNewCreate() throws IOException {
    File workDir = new File("tmp_" + System.currentTimeMillis());
    assertFalse(workDir.exists());
    factory.setWorkDir(new FileSystemResource(workDir));
    assertTrue(workDir.exists());
    log.debug(workDir.getCanonicalPath());
    workDir.delete();
  }

  @Test
  public void testSetWorkDirResouce() throws IOException {
    File workDir = new File("tmp_" + System.currentTimeMillis());
    factory.setWorkDir(new FileSystemResource(workDir));
    Resource resource = factory.createInstance(FILE_SUFFIX);
    log.debug(resource);
    assertEquals(workDir.getCanonicalPath(), getResourceDir(resource));
    workDir.delete();
  }

  @Test
  public void testCreateInstanceSuffix() throws Exception {
    String[] nameParts = factory.createInstance(FILE_SUFFIX).getFilename().split("_");
    assertEquals(FILE_SUFFIX, nameParts[2]);
  }

  @Test
  public void testCreateInstancePrefix() throws Exception {
    DateTime currentStamp = new DateTime();
    Resource resource = factory.createInstance(FILE_SUFFIX);
    log.debug(resource);
    String[] nameParts = resource.getFilename().split("_");
    // get time stamp from file name
    DateTime fileStamp = DateTimeFormat.forPattern("yyyyMMddHHmmss").parseDateTime(
        nameParts[0] + nameParts[1]);
    log.debug(fileStamp);
    assertThat(currentStamp.isBefore(fileStamp.plusSeconds(1)), is(true));

  }

  private String getResourceDir(Resource resource) throws IOException {
    return resource.getFile().getParentFile().getCanonicalPath();
  }
}
