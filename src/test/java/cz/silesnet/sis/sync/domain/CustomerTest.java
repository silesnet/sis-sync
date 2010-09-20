package cz.silesnet.sis.sync.domain;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class CustomerTest {

  private Customer customer;

  @Before
  public void setUp() throws Exception {
    customer = new Customer();
  }

  @After
  public void tearDown() throws Exception {
    customer = null;
  }

  @Test
  public void testGetSimpleSpsContract() {
    customer.setContract("12345/2008");
    assertEquals("123452008", customer.getSpsContract());
  }

  @Test
  public void testGetSimpleSpsContractWithSpaces() {
    customer.setContract(" 123 45 / 20 08 ");
    assertEquals("123452008", customer.getSpsContract());
  }

  @Test
  public void testGetMultimpleSpsContract() {
    customer.setContract("12344/2009, 12345/2008");
    assertEquals("123452008", customer.getSpsContract());
  }

  @Test
  public void testGetComplexSpsContract() {
    customer.setContract("123/2008, 12345/2008, 3045/2010");
    assertEquals("123452008", customer.getSpsContract());
  }

  @Test
  public void testGetNullSpsContract() {
    assertNull(customer.getSpsContract());
  }

  @Test
  public void testGetEmptySimpleSpsContract() {
    customer.setContract("");
    assertNull(customer.getSpsContract());
  }

  @Test
  public void testGetEmptySpsContract() {
    customer.setContract("/,/");
    assertNull(customer.getSpsContract());
  }

  @Test
  public void testGetTrickySpsConract() {
    customer.setContract("123/2008, 123/2009, 123/2010");
    assertEquals("1232010", customer.getSpsContract());
  }
}
