package uiTestFramework.config;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * Specialized Configuration class responsible ONLY for loading and providing
 * settings specific to local execution (e.g., maximizing, browser binary paths).
 *
 * This class adheres strictly to the Single Responsibility Principle (SRP).
 */
public class LocalDriverConfig {

    private final Properties properties;

    private static final String FILE_NAME = "config.properties";


    /**
     * Constructor loads the local configuration properties file.
     * Note: For simplicity, we are currently loading properties from the main config.properties file.
     * In a future enhancement, you could move local-specific properties here and load a new file.
     */
    public LocalDriverConfig(){
        properties = new Properties();

        try(InputStream inputStream = getClass().getClassLoader().getResourceAsStream(FILE_NAME)) {
            if(inputStream == null){
                System.err.println("Warn: Core config file: "+FILE_NAME+" not found or is empty. Using minimal defaults for LocalDriverConfig.");
                properties.setProperty("browser","chrome");
                properties.setProperty("implicitWaitTimeout","10");

            }
            else {
                properties.load(inputStream);
                System.out.println("Local config properties initialized.");
            }
        } catch (IOException e) {
            System.err.println("Error: Failed to initialize Local config properties. "+e.getMessage());
        }
    }

    public Properties getLocalProperties() {
        return this.properties;
    }

    /**
     * Helper method to retrieve a property, prioritizing command line (System Property).
     * @param key The property key.
     * @return The determined value.
     */
    private String getPropertyOrDefault(String key,String defaultValue){
        String systemValue = System.getProperty(key);
        if (systemValue != null){
            return systemValue;
        }
        return properties.getProperty(key,defaultValue);
    }

    /**
     * Gets the browser name (e.g., chrome, firefox).
     */
    public String getBrowser(){
        return getPropertyOrDefault("browser","chrome");
    }

    /**
     * Gets the implicit wait timeout in seconds.
     */
    public long getImplicitWaitTimeout(){
        String timeoutStr = getPropertyOrDefault("implicitWaitTimeout","10");

        try {
           return Long.parseLong(timeoutStr);
        } catch (NumberFormatException e) {
            return 10;
        }
    }

    /**
     * Gets the headless mode flag.
     */
    public boolean isHeadLessMode(){
        String isHeadless = getPropertyOrDefault("headless","false");

        if (isHeadless == null){
            return false;
        }
        return Boolean.parseBoolean(isHeadless);
    }


    public boolean getHeadlessMode() {
        return false;
    }




}
