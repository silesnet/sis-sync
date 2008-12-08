/*
 * Copyright 2008 the original author or authors.
 */
package cz.silesnet.sis.sync;

import static org.junit.Assert.*;

import java.io.File;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.NoWorkFoundException;
import org.springframework.batch.item.ParseException;
import org.springframework.batch.item.UnexpectedInputException;
import org.springframework.core.io.ClassPathResource;

import cz.silesnet.sis.sync.domain.Customer;

public class SpsResCustomerItemReaderTest {

    private File input;
    private ItemReader reader;

    @Before
    public void setUp() throws Exception {
        input = (new ClassPathResource("/data/20081206_sps_res_customers_small.xml")).getFile();
        reader = new SpsResCustomerItemReader();
        ((SpsResCustomerItemReader) reader).setInputFile(input);
    }

    @After
    public void tearDown() throws Exception {
        reader = null;
        input = null;
    }

    @Test
    public void testReadFirst() throws UnexpectedInputException, NoWorkFoundException, ParseException, Exception {
        Customer customer = (Customer) reader.read();
        assertEquals("62", customer.getSymbol());
        assertEquals("Test Customer 0", customer.getName());
    }
    @Test
    public void testReadSecond() throws UnexpectedInputException, NoWorkFoundException, ParseException, Exception {
        reader.read();
        Customer customer = (Customer) reader.read();
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
        ((SpsResCustomerItemReader) reader).setInputFile(null);
    }
}
