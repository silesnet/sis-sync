/*
 * Copyright 2008 the original author or authors.
 */
package cz.silesnet.sis.sync.item.reader;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.batch.item.ExecutionContext;
import org.springframework.batch.item.NoWorkFoundException;
import org.springframework.batch.item.ParseException;
import org.springframework.batch.item.UnexpectedInputException;
import org.springframework.core.io.ClassPathResource;

import cz.silesnet.sis.sync.domain.CustomerResult;

public class SpsCustomerResultItemReaderTest {

    private SpsCustomerResultItemReader reader;
    private ExecutionContext executionContext;

    @Before
    public void setUp() throws Exception {
        reader = new SpsCustomerResultItemReader();
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
    public void testReadFirst() throws UnexpectedInputException, NoWorkFoundException, ParseException, Exception {
        CustomerResult result = (CustomerResult) reader.read();
        assertEquals(123456, result.getSisId());
        assertEquals(62L, result.getSpsId());
        assertEquals("Test Customer 0", result.getName());
    }

    @Test
    public void testReadSecond() throws UnexpectedInputException, NoWorkFoundException, ParseException, Exception {
        reader.read();
        CustomerResult result = (CustomerResult) reader.read();
        assertEquals(123457, result.getSisId());
        assertEquals(63L, result.getSpsId());
        assertEquals("Test Customer 1", result.getName());
    }

    @Test
    public void testReadAll() throws UnexpectedInputException, NoWorkFoundException, ParseException, Exception {
        int counter = 0;
        while (reader.read() != null) {
            counter++;
        }
        assertEquals(2, counter);
    }
}
