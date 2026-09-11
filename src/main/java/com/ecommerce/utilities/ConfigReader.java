package com.ecommerce.utilities;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class ConfigReader {
    
    private static Properties properties = new Properties();

    static {
        try {
            
            File file = new File("src/test/resources/config/config.properties");
            FileInputStream fis = new FileInputStream(file);
            properties.load(fis);
            fis.close();
            System.out.println("Config file successfully loaded!");
        } catch (IOException e) {
            throw new RuntimeException("Config file nahi mili is path par: " + e.getMessage());
        }
    }

    public static String getProperty(String key) {
        return properties.getProperty(key);
    }
}
