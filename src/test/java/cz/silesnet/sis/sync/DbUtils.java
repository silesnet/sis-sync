/*
 * Copyright 2008 the original author or authors.
 */
package cz.silesnet.sis.sync;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.dbunit.DataSourceDatabaseTester;
import org.dbunit.dataset.IDataSet;
import org.dbunit.dataset.xml.FlatXmlDataSet;
import org.springframework.core.io.Resource;
import org.springframework.jdbc.core.JdbcTemplate;

/**
 * Helper class providing database initialization and test data import.
 * 
 * @author sikorric
 * 
 */
public class DbUtils {

    private static final int BUFFER_SIZE = 1024;

    public static void initializeDatabase(DataSource dataSource, Resource resource) throws IOException {
        initializeDatabase(dataSource, new Resource[] { resource });
    }

    public static void initializeDatabase(DataSource dataSource, Resource[] resources) throws IOException {
        // read SQL commands from all resources
        List<String> sqlCommands = new ArrayList<String>();
        for (Resource resource : resources) {
            sqlCommands.addAll(readSqlCommands(resource));
        }
        // execute the commands against the data source
        JdbcTemplate template = new JdbcTemplate(dataSource);
        for (String sql : sqlCommands) {
            template.execute(sql);
        }
    }

    public static List<String> readSqlCommands(Resource resource) throws IOException {
        // read content of the resource to string buffer
        BufferedReader reader = new BufferedReader(new FileReader(resource.getFile()));
        StringBuffer fileData = new StringBuffer(BUFFER_SIZE);
        char[] buffer = new char[BUFFER_SIZE];
        int readCount = 0;
        while ((readCount = reader.read(buffer)) != -1) {
            fileData.append(buffer, 0, readCount);
        }
        // get SQL commands from buffer
        String[] commandLines = fileData.toString().split(";");
        ArrayList<String> sqlCommands = new ArrayList<String>();
        for (String command : commandLines) {
            String sql = command.trim();
            // skip empty lines and comments
            if ("".equals(sql) || sql.startsWith("#") || sql.startsWith("--"))
                continue;
            sqlCommands.add(sql);
        }
        return sqlCommands;
    }

    public static DataSourceDatabaseTester createAndInitializeDatabaseTester(DataSource dataSource, Resource resource)
            throws Exception {
        DataSourceDatabaseTester tester = new DataSourceDatabaseTester(dataSource);
        IDataSet dataSet = new FlatXmlDataSet(resource.getFile());
        tester.setDataSet(dataSet);
        tester.onSetup();
        return tester;
    }
}
