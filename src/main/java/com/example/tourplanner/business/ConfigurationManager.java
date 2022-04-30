package com.example.tourplanner.business;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class ConfigurationManager {
    public static String getConfigProperty(String propertyName) {
        Properties properties = new Properties();
        String propertiesFileName = "config.properties";

        InputStream inputStream = ConfigurationManager.class.getClassLoader().getResourceAsStream(propertiesFileName);
        try {
            properties.load(inputStream);
            return properties.getProperty(propertyName);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "";
    }

}
