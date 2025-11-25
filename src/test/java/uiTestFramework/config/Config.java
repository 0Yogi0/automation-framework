package uiTestFramework.config;

import java.io.IOException;
import java.io.InputStream;
import java.util.Locale;
import java.util.Properties;
import java.util.SplittableRandom;

public class Config {

    private static Config instance = null;
    private final Properties properties = new Properties();;

    private static final String DEFAULT_PROPERTIES_FILE_NAME = "config.properties";

    //private constructor
    private Config(){
       loadEnvironmentOrDefault();
    }

    private void loadEnvironmentOrDefault(){

        String runtimeEnv = System.getProperty("env");

        if(runtimeEnv == null){
            initProperties(DEFAULT_PROPERTIES_FILE_NAME);
            System.out.println("INFO: Default Configuration with "+DEFAULT_PROPERTIES_FILE_NAME+" created successfully.");
            return;
        }

        String envFile = String.format("config-%s.properties",runtimeEnv.toLowerCase());

        initProperties(envFile);

    }

    private void initProperties(String fileName){
        try(InputStream iS = getClass().getClassLoader().getResourceAsStream(fileName)){
            if(iS == null){
                System.err.println("Warn: config file: "+fileName+" not found or is empty. Using defaults for Config.");
                properties.load(getClass().getClassLoader().getResourceAsStream(DEFAULT_PROPERTIES_FILE_NAME));
                System.out.println("INFO: Default Configuration with "+DEFAULT_PROPERTIES_FILE_NAME+" created successfully.");
            }else {
                properties.load(iS);
                System.out.println("INFO: Base Configuration with "+fileName+" created successfully.");
            }

        } catch (IOException e) {
            System.err.println("Error: Failed to initialize "+fileName+" properties. "+e.getMessage());
        }
    }

    public static synchronized Config getConfigInstance(){
        if(instance == null){
            instance = new Config();
        }
        System.out.println("in get config instance");
        return instance;
    }

    public String getPropertyOrDefault(String key, String defaultValue){
        String systemValue = System.getProperty(key);
        if (systemValue != null){
            return systemValue;
        }
        return properties.getProperty(key,defaultValue);
    }

    // ---------------- GETTERS -------------------

    public BrowserType getBrowser() {
        String browser = getPropertyOrDefault("browser", "chrome").toUpperCase(Locale.ROOT);
        return BrowserType.valueOf(browser);
    }

    public EnvironmentType getEnvironment() {
        String env = getPropertyOrDefault("env", "dev").toUpperCase(Locale.ROOT);
        return EnvironmentType.valueOf(env);
    }

    public String getBaseUrl() {
        return getPropertyOrDefault("baseUrl", "");
    }

    public int getTimeout() {
        return Integer.parseInt(getPropertyOrDefault("timeout", "20"));
    }

}
