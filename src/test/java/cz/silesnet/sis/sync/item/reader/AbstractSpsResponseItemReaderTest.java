package cz.silesnet.sis.sync.item.reader;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.batch.item.ExecutionContext;
import org.springframework.core.io.ClassPathResource;

public class AbstractSpsResponseItemReaderTest {

    private AbstractSpsResponseItemReader reader;
    private ExecutionContext executionContext;

    @Before
    public void setUp() throws Exception {
        reader = new AbstractSpsResponseItemReader() {
            // return id as string
            protected Object mapLines(long id, String[] lines) {
                return "" + id;
            }
        };
        reader.setResource(new ClassPathResource("data/20081206_sps_customers.xml"));
        executionContext = new ExecutionContext();
        reader.open(executionContext);
    }

    @After
    public void tearDown() throws Exception {
        reader.close(executionContext);
        reader = null;
        executionContext = null;
    }

    @Test
    public final void testReadFirst() throws Exception {
        assertEquals("123456", reader.read());
    }

    @Test
    public final void testReadSecond() throws Exception {
        reader.read();
        assertEquals("123457", reader.read());
    }

    @Test
    public final void testReadAll() throws Exception {
        int counter = 0;
        while (reader.read() != null) {
            counter++;
        }
        assertEquals(2, counter);
    }

}
