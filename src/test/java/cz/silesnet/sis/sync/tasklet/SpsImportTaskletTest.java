package cz.silesnet.sis.sync.tasklet;

import static org.junit.Assert.*;

import java.io.File;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.FileSystemResource;

public class SpsImportTaskletTest {

  private static final Log log = LogFactory.getLog(SpsImportTaskletTest.class);

  private static final String SPS_EXECUTABLE = "C:\\Program Files\\STORMWARE\\POHODA\\Pohoda.exe";
  private static final String SIS_CUSTOMERS_FILE = "data/20081206_sis_customers.xml";
  private static final String SPS_CUSTOMERS_FILE = "target/20081206_sps_customers.TEMP.xml";
  private static final String SPS_INI_FILE = "target/sisImport.ini";
  private static final String SPS_LOGIN = "Admin";
  private static final String SPS_PASSWORD = "";
  private static final String SPS_DATABASE = "12345678_2008.mdb";

  private SpsImportTasklet tasklet;

  @Before
  public void setUp() throws Exception {
    tasklet = new SpsImportTasklet();
    tasklet.setInput(new ClassPathResource(SIS_CUSTOMERS_FILE));
    tasklet.setOutput(new FileSystemResource(SPS_CUSTOMERS_FILE));
    tasklet.setIni(new FileSystemResource(SPS_INI_FILE));
    tasklet.setExecutable(new FileSystemResource(SPS_EXECUTABLE));
    tasklet.setLogin(SPS_LOGIN);
    tasklet.setPassword(SPS_PASSWORD);
    tasklet.setDatabase(SPS_DATABASE);
  }

  @After
  public void tearDown() throws Exception {
    tasklet = null;
  }

  @Test
  @Ignore
  public void testExecute() throws Exception {
    File outFile = new File(SPS_CUSTOMERS_FILE);
    if (outFile.exists()) {
      outFile.delete();
    }
    assertEquals(RepeatStatus.FINISHED, tasklet.execute(null, null));
    assertTrue((new File(SPS_CUSTOMERS_FILE)).exists());
  }

  @Test
  public void testCreateCommandLine() throws Exception {
    tasklet.setIni(new FileSystemResource("C:\\INI"));
    String command = tasklet.createCommandLine();
    log.debug(command);
    assertEquals("\"" + SPS_EXECUTABLE + "\" /XML \"" + SPS_LOGIN + "\" \"" + SPS_PASSWORD
        + "\" \"C:\\INI\"", command);
  }

  @Test
  public void testIniContent() throws Exception {
    tasklet.setInput(new FileSystemResource("C:\\IN"));
    tasklet.setOutput(new FileSystemResource("C:\\OUT"));
    String config = tasklet.iniContent();
    log.debug(config);
    assertEquals("[XML]\ninput_xml=C:\\IN\nresponse_xml=C:\\OUT\ndatabase=" + SPS_DATABASE
        + "\ncheck_duplicity=1\nformat_output=1\n", config);

  }
}
