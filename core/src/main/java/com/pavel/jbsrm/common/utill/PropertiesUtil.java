package com.pavel.jbsrm.common.utill;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

public class PropertiesUtil {

    public static Map<String, String> getPropertiesMap(String path, String propPrefix) {
        Map<String, String> propertiesMap = new HashMap<>();
        Properties properties = new Properties();

        try( FileInputStream fileInputStream = new FileInputStream(new File(path))) {

            properties.load(fileInputStream);
            properties.stringPropertyNames().stream()
                    .filter(s -> s.contains(propPrefix))
                    .forEach(key -> propertiesMap.put(key.replace(propPrefix, ""),
                            properties.getProperty(key)));
        } catch (IOException e) {
            e.printStackTrace();
        }

        return propertiesMap;
    }

    public static Properties mapToProperties(Map<String, String> propertiesMap) {
        Properties properties = new Properties();
        propertiesMap.keySet().forEach(key -> properties.setProperty(key, propertiesMap.get(key)));
        return properties;
    }

    public static Properties getSubProperties(String path, String propPrefix) {
        Map<String, String> propertiesMap = getPropertiesMap(path, propPrefix);
        return mapToProperties(propertiesMap);
    }
}
