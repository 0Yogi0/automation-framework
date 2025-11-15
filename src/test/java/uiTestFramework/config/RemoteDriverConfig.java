package uiTestFramework.config;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class RemoteDriverConfig {

    private final Properties properties;

    private static final String FILE_NAME = "config-remote.properties";

    /**
     * Constructor loads the remote configuration properties file.
     */
    public RemoteDriverConfig(){
        properties = new Properties();

        try(InputStream inputStream = getClass().getClassLoader().getResourceAsStream(FILE_NAME)) {
            if(inputStream == null){

                System.err.println("Remote config file "+ FILE_NAME +" is empty or not found: ");
                properties.setProperty("runOnRemote","false");
                properties.setProperty("gridUrl","");
                properties.setProperty("platform","ANY");//check this out later

            }
            else {

                properties.load(inputStream);
                System.out.println("Info: Remote Configuration loaded successfully");

            }
        }
        catch (IOException e){
            System.err.println("Failed to load "+FILE_NAME+": "+e.getMessage());
        }

    }

    public Properties getRemoteProperties() {
        return this.properties;
    }


    /**
     * Helper method to retrieve a property, prioritizing command line (System Property)
     * over the loaded properties file value.
     * @param key The property key.
     * @param defaultValue The default value to return if the key is not found.
     * @return The determined value.
     */
    private String getPropertyOrDefault(String key, String defaultValue){

        String systemValue = System.getProperty(key);

        if(systemValue != null){
            System.out.println("CONFIG OVERRIDE: Using System Property for '" + key + "': " + systemValue);
            return systemValue;
        }

        return properties.getProperty(key,defaultValue);

    }

    /**
     * Checks if remote execution is enabled.
     * Prioritizes System Property 'runOnRemote'.
     * @return true if 'runOnRemote' is set to "true" (case-insensitive). Defaults to false.
     */
    public boolean isRemoteExecution(){

        String remoteFlag = getPropertyOrDefault("runOnRemote","false");

        return Boolean.parseBoolean(remoteFlag);

    }

    /**
     * Gets the Selenium Grid/Cloud Hub URL.
     * Prioritizes System Property 'gridUrl'.
     * @return The URL string.
     */
    public String getGridUrl(){

        return getPropertyOrDefault("gridUrl","");

    }

    /**
     * Gets the target platform (e.g., "WINDOWS", "LINUX", "ANY").
     * Prioritizes System Property 'platform'.
     * @return The platform string. Defaults to "ANY".
     */
    public String getPlatform(){
        return getPropertyOrDefault("platform","ANY");
    }

    /**
     * Gets the browser name (e.g., chrome, firefox).
     */
    public String getBrowser(){
        return getPropertyOrDefault("browser","chrome");
    }



}
