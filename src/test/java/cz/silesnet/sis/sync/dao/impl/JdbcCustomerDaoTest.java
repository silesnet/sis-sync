package cz.silesnet.sis.sync.dao.impl;

import javax.sql.DataSource;

import org.dbunit.IDatabaseTester;
import org.junit.Test;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.AbstractDependencyInjectionSpringContextTests;

import cz.silesnet.sis.sync.SisCustomerFunctionalTest;
import cz.silesnet.sis.sync.domain.Customer;

public class JdbcCustomerDaoTest extends AbstractDependencyInjectionSpringContextTests {
  private static final long ID = 5L;
  private JdbcCustomerDao dao;
  private JdbcTemplate template;
  private DataSource dataSource;
  private IDatabaseTester dbTester;

  @Override
  protected String[] getConfigLocations() {
    return new String[]{"classpath:sisCustomerJob.xml"};
  }

  @Override
  protected void onSetUp() throws Exception {
    super.onSetUp();
    dao = new JdbcCustomerDao();
    template = (JdbcTemplate) applicationContext.getBean("jdbcTemplate");
    dao.setJdbcTemplate(template);
    dataSource = (DataSource) applicationContext.getBean("dataSource");
    dbTester = SisCustomerFunctionalTest.initializeDatabase(dataSource);
  }

  @Override
  protected void onTearDown() throws Exception {
    super.onTearDown();
    dbTester.onTearDown();
  }

  @Test
  public void testFind() {
    Customer customer = dao.find(ID);
    assertEquals(ID, customer.getId());
    assertEquals("Test Customer5", customer.getName());
    assertEquals("Supplementary Name5", customer.getSupplementaryName());
    assertEquals("Contact Name5", customer.getContactName());
    assertEquals("Test City", customer.getCity());
    assertEquals("Street 5", customer.getStreet());
    assertEquals("12345", customer.getZip());
    assertEquals("12345675", customer.getIco());
    assertEquals("CZ12345675", customer.getDic());
    assertEquals("+420123456785", customer.getPhone());
    assertEquals("contact@customer5.cz", customer.getEmail());
    assertEquals("5", customer.getContract());
    assertEquals("1234-567895", customer.getAccountNo());
    assertEquals("2405", customer.getBankCode());
  }

  @Test
  public void testFailWhenInvoiceDoesNotExist() {
    try {
      dao.find(0L);
    } catch (IllegalArgumentException e) {
      // expected
    }
  }
}
