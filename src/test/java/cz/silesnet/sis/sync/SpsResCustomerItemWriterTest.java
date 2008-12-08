/*
 * Copyright 2008 the original author or authors.
 */
package cz.silesnet.sis.sync;

import static org.junit.Assert.*;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.batch.item.ItemWriter;
import org.springframework.core.io.FileSystemResource;

import cz.silesnet.sis.sync.domain.Customer;

public class SpsResCustomerItemWriterTest {

    // FIXME target/test-outputs/ has to exist! do it somehow
    private static final String RESOURCE_NAME = "target/test-outputs/20081206_sps_res_customers_small.txt";
    private File output;
    private ItemWriter writer;

    @Before
    public void setUp() throws Exception {
        output = (new FileSystemResource(RESOURCE_NAME)).getFile();
        writer = new SpsResCustomerItemWriter();
        ((SpsResCustomerItemWriter) writer).setOutputFile(output);
    }

    @After
    public void tearDown() throws Exception {
        writer = null;
        output = null;
    }

    @Test
    public void testWriteCustomer() throws Exception {
        ((SpsResCustomerItemWriter) writer).open(null);
        Customer customer = new Customer();
        customer.setName("Test Writer Customer");
        customer.setSymbol("123");
        writer.write(customer);
        ((SpsResCustomerItemWriter) writer).close(null);
        File result = (new FileSystemResource(RESOURCE_NAME).getFile());
        BufferedReader reader = new BufferedReader(new FileReader(result));
        String customerLine = reader.readLine();
        assertEquals("Test Writer Customer|123", customerLine);
        reader.close();
    }

}
