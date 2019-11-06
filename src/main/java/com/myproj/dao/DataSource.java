package com.myproj.dao;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class DataSource {
    private String url;
    private String user;
    private String password;
    private String driver;


    public DataSource() {
        InputStream inputStream = getClass().getClassLoader().getResourceAsStream("datasource.properties");

        Properties properties = new Properties();
        try {
            properties.load(inputStream);
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }

        url = properties.getProperty("db.url");
        user = properties.getProperty("db.user");
        password = properties.getProperty("db.password");
        driver = properties.getProperty("db.driver");
    }

    public String getUrl() {
        return url;
    }

    public String getUser() {
        return user;
    }

    public String getPassword() {
        return password;
    }

    public String getDriver() {
        return driver;
    }
}
