package cz.silesnet.sis.sync;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;

import org.junit.Test;
import org.springframework.core.io.Resource;

public class DbUtilsTest {
  // defaults to class path resource
  private static final String RESOURCE = "empty-resource.txt";
  private static final String RESOURCES = RESOURCE + ", " + RESOURCE;

  @Test
  public void testResolveResources() throws Exception {
    Resource[] resources = DbUtils.resolveResources(RESOURCES);
    assertThat(resources[1].exists(), is(true));
  }
}
