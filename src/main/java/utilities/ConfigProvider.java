package utilities;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class ConfigProvider {

    private static Properties properties = readProperties();

    public static String getConfigParameter(String key, String defaultValue) {
        String value = System.getProperty(key);
        if (value == null) {
            if (properties.getProperty(key) != null) {
                return properties.getProperty(key);
            } else if (defaultValue != null) {
                return defaultValue;
            }
            throw new RuntimeException("Configuration value not found for key '" + key + "'");
        }
        return value;
    }

    private static Properties readProperties() {
        try (InputStream inputStream = new FileInputStream("src/test/resources/project.properties")) {
            properties = new Properties();
            properties.load(inputStream);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return properties;
    }
}
