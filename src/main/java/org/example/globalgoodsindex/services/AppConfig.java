package org.example.globalgoodsindex.services;

import java.io.InputStream;
import java.util.Properties;

public class AppConfig {
    private static Properties properties = new Properties();

    static {
        try (InputStream input = AppConfig.class.getResourceAsStream("/config/app.properties")) {
            if (input != null) {
                properties.load(input);
            } else {
                throw new RuntimeException("Properties file not found");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static String get(String key) {
        return properties.getProperty(key);
    }
}
