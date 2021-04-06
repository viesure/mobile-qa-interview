package org.viesure.utils;

import java.io.FileInputStream;
import java.util.Properties;

public class PropertyFileLoader {

    /**
     * Loads a property file from the resources package with the given name
     * @param device the name of the property file
     * @return the loaded properties for the given device
     */
    public static Properties loadDeviceProperty(String device) {
        FileInputStream inputStream;
        Properties properties = new Properties();

        String propertiesFilePath = String.format(System.getProperty("user.dir")+"/src/main/resources/properties/%s.properties", device);

        try {
            inputStream = new FileInputStream(propertiesFilePath);
            properties.load(inputStream);

        } catch (Exception e) {
            String errorMessage = "Property file not found or invalid for path: " + propertiesFilePath + "\n" + e.getLocalizedMessage();

            AllureLogger.saveTextLog(errorMessage);
            System.out.println(errorMessage);
        }
        return properties;
    }
}
