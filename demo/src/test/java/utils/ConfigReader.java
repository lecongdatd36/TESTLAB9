package utils;

import java.io.FileInputStream;
import java.util.Properties;

public class ConfigReader {

    private static ConfigReader instance;
    private Properties properties;

    private ConfigReader() {
        try {
            String env = System.getProperty("env", "dev");

            System.out.println("Dang dung moi truong: " + env);

            String filePath = System.getProperty("user.dir")
                    + "/src/test/resources/config-" + env + ".properties";

            properties = new Properties();
            properties.load(new FileInputStream(filePath));

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Singleton
    public static ConfigReader getInstance() {
        if (instance == null) {
            instance = new ConfigReader();
        }
        return instance;
    }

    public String get(String key) {
        return properties.getProperty(key);
    }

    public int getInt(String key) {
        return Integer.parseInt(properties.getProperty(key));
    }
}