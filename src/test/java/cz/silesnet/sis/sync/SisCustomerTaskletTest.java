package cz.silesnet.sis.sync;

import static org.junit.Assert.*;

import java.io.File;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.ExitStatus;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.FileSystemResource;

public class SisCustomerTaskletTest {

    /**
     * 
     */
    private static final String SPS_FOLDER = "C:\\Program Files\\STORMWARE\\Pohoda";
    private static final String SIS_CUSTOMERS_FILE = "data/20081206_sis_customers.xml";
    private static final String SPS_CUSTOMERS_FILE = "target/20081206_sps_customers.xml";

    private Tasklet tasklet;

    @Before
    public void setUp() throws Exception {
        tasklet = new SisCustomerTasklet();
        File outFile = new File(SPS_CUSTOMERS_FILE);
        if (outFile.exists()) {
            outFile.delete();
        }
    }

    @After
    public void tearDown() throws Exception {
        tasklet = null;
    }

    @Test
    public void testExecute() throws Exception {
        SisCustomerTasklet sisTasklet = (SisCustomerTasklet) tasklet;
        sisTasklet.setInput(new ClassPathResource(SIS_CUSTOMERS_FILE));
        sisTasklet.setOutput(new FileSystemResource(SPS_CUSTOMERS_FILE));
        sisTasklet.setSpsFolder(new FileSystemResource(SPS_FOLDER));
        assertEquals(ExitStatus.FINISHED, tasklet.execute());
        assertTrue((new File(SPS_CUSTOMERS_FILE)).exists());
    }
    @Test
    public void testComposeCommand() throws Exception {
        SisCustomerTasklet sisTasklet = (SisCustomerTasklet) tasklet;
        sisTasklet.setSpsFolder(new FileSystemResource("C:\\SPS"));
        String command = sisTasklet.composeCommand("CONFIG");
        assertEquals("\"C:\\SPS\\Pohoda.exe\" /XML \"Admin\" \"\" \"CONFIG\"", command);
    }

    @Test
    public void testComposeSpsImportConfig() throws Exception {
        SisCustomerTasklet sisTasklet = (SisCustomerTasklet) tasklet;
        sisTasklet.setInput(new FileSystemResource("C:\\IN"));
        sisTasklet.setOutput(new FileSystemResource("C:\\OUT"));
        String config = sisTasklet.composeSpsImportConfig();
        assertEquals(
                "[XML]\ninput_xml=C:\\IN\nresponse_xml=C:\\OUT\ndatabase=12345678_2008.mdb\ncheck_duplicity=1\nformat_output=1\n",
                config);

    }

}
