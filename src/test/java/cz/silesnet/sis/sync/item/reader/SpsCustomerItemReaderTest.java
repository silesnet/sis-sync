/*
 * Copyright 2008 the original author or authors.
 */
package cz.silesnet.sis.sync.item.reader;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.NoWorkFoundException;
import org.springframework.batch.item.ParseException;
import org.springframework.batch.item.UnexpectedInputException;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

import cz.silesnet.sis.sync.domain.Customer;
import cz.silesnet.sis.sync.item.reader.SpsCustomerItemReader;

public class SpsCustomerItemReaderTest {

    private Resource input;
    private ItemReader reader;

    @Before
    public void setUp() throws Exception {
        input = new ClassPathResource("data/20081206_sps_customers.xml");
        reader = new SpsCustomerItemReader();
        ((SpsCustomerItemReader) reader).setResource(input);
    }

    @After
    public void tearDown() throws Exception {
        reader = null;
        input = null;
    }

    @Test
    public void testReadFirst() throws UnexpectedInputException, NoWorkFoundException, ParseException, Exception {
        Customer customer = (Customer) reader.read();
        assertEquals(123456, customer.getId());
        assertEquals("62", customer.getSymbol());
        assertEquals("Test Customer 0", customer.getName());
    }

    @Test
    public void testReadSecond() throws UnexpectedInputException, NoWorkFoundException, ParseException, Exception {
        reader.read();
        Customer customer = (Customer) reader.read();
        assertEquals(123457, customer.getId());
        assertEquals("63", customer.getSymbol());
        assertEquals("Test Customer 1", customer.getName());
    }

    @Test
    public void testReadAll() throws UnexpectedInputException, NoWorkFoundException, ParseException, Exception {
        int counter = 0;
        while (reader.read() != null) {
            counter++;
        }
        assertEquals(2, counter);
    }

    @Test(expected = IllegalStateException.class)
    public void testFailWhenSetingFileOnInitializedReader() throws UnexpectedInputException, NoWorkFoundException,
            ParseException, Exception {
        reader.read();
        ((SpsCustomerItemReader) reader).setResource(null);
    }
}
