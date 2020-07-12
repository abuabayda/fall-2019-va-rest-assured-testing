package com.DBUtil;

import java.io.FileInputStream;

import java.io.IOException;
import java.util.Properties;

public class ConfigReader {

    private static final Properties properties;

    static {
        try {
            try (FileInputStream fileInputStream = new FileInputStream("config.properties")) {
                properties = new Properties();
                properties.load(fileInputStream);
            }
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException("Failed to load properties file!");
        }
    }

    public static String getProperty(String propertyName) {
        return properties.getProperty(propertyName);
    }
}
