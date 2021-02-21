package com.kaptsiug.project.util;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class DbUtil {

    private static final String PROPERTIES_FILE = "db-config.properties";
    private static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";
    private static Connection connection = null;

    public static Connection getConnection() {
        if (connection != null) {
            return connection;
        } else {
            try {
                Class.forName(JDBC_DRIVER);
                Properties prop = new Properties();
                InputStream inputStream = DbUtil.class.getClassLoader()
                        .getResourceAsStream(PROPERTIES_FILE);
                prop.load(inputStream);
                String url = prop.getProperty("url");
                String user = prop.getProperty("user");
                String password = prop.getProperty("password");
                connection = DriverManager.getConnection(url, user, password);
            } catch (SQLException | IOException | ClassNotFoundException e) {
                e.printStackTrace();
            }
            return connection;
        }
    }
}