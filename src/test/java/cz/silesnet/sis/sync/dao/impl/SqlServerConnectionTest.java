package cz.silesnet.sis.sync.dao.impl;

import static org.junit.Assert.assertNotNull;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.apache.commons.dbcp.BasicDataSource;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class SqlServerConnectionTest {

    @Before
    public void setUp() throws Exception {
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void testConnection() throws ClassNotFoundException, SQLException {
        Class.forName("sun.jdbc.odbc.JdbcOdbcDriver");
        Connection connection = DriverManager.getConnection("jdbc:odbc:SQL_SERVER", "", "");
        Statement statement = connection.createStatement();
        statement.execute("SELECT * FROM Sheet1");
        ResultSet resultSet = statement.getResultSet();
        assertNotNull(resultSet);
    }

    @Test
    public void testDataSource() throws SQLException {
        BasicDataSource ds = new BasicDataSource();
        ds.setDriverClassName("sun.jdbc.odbc.JdbcOdbcDriver");
        ds.setUrl("jdbc:odbc:SQL_SERVER");
        Connection connection = ds.getConnection();
        Statement statement = connection.createStatement();
        statement.execute("SELECT * FROM Sheet1");
        ResultSet resultSet = statement.getResultSet();
        assertNotNull(resultSet);
    }
}
