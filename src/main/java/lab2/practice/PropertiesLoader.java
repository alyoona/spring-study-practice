package lab2.practice;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class PropertiesLoader {

    private String path;

    public Properties get() {
        try (InputStream inputStream = PropertiesLoader.class.getResourceAsStream(path)) {
            Properties properties = new Properties();
            properties.load(inputStream);
            return properties;
        } catch (IOException e) {
            throw new RuntimeException("error while loading properties file", e);
        }
    }

    public void setPath(String path) {
        this.path = path;
    }



}
