package fi.jperala.worktime.util;

import fi.jperala.worktime.jpa.domain.Department;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

/**
 * Settings utility
 */
public class Settings {

    public static final String PROPERTY_FILE = "worktime.properties";
    private static final String PROPERTY_DEPARTMENT = "department";

    private static Settings singleton = null;

    private final Properties properties;

    public static synchronized Settings getInstance() throws IOException {
        if(singleton == null) {
            singleton = new Settings();
        }
        return singleton;
    }

    private Settings() throws IOException {
        properties = new Properties();
        properties.load(new FileInputStream(PROPERTY_FILE));
    }

    public Department getDepartment() {
        String depString = properties.getProperty(PROPERTY_DEPARTMENT);
        return Department.valueOf(depString);
    }

}
