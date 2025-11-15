package uiTestFramework.config;

import java.util.Properties;

public class BaseConfig {

    // 1. Static instance of the BaseConfig class
    private static BaseConfig instance = null;

    private static Properties baseConfigProps;

    // 2. Specialized Configuration Objects (Composition)
    private final RemoteDriverConfig remoteDriverConfig;
    private final LocalDriverConfig localDriverConfig;

    /**
     * Private Constructor: This prevents external classes from creating instances.
     * It only initializes the specialized configuration objects; it does NOT load properties itself.
     */
    private BaseConfig() {
        // Core properties (like baseUrl) are now loaded by LocalDriverConfig
        this.remoteDriverConfig = new RemoteDriverConfig();
        this.localDriverConfig = new LocalDriverConfig();
        System.out.println("INFO: Base Configuration (Facade) created successfully.");
    }

    /**
     * Public access method to get the single instance of Configuration.
     * @return The single BaseConfig instance.
     */
    public static BaseConfig getInstance() {
        if (instance == null) {
            instance = new BaseConfig();
            if(instance.remoteDriverConfig.isRemoteExecution()){
                baseConfigProps = instance.remoteDriverConfig.getRemoteProperties();
            }
            else{
                baseConfigProps = instance.localDriverConfig.getLocalProperties();
            }
        }
        return instance;
    }

    /**
     * Private Helper Method: Handles System Property override check for core, non-driver-specific properties.
     * This centralizes the command-line override check for BaseConfig's delegated getters.
     * @param key The property key (e.g., "baseUrl").
     * @return The value from System Properties if present, otherwise null.
     */
    private String getConfigProperty(String key) {
        String systemValue = System.getProperty(key);
        if (systemValue != null) {
            System.out.println("CONFIG OVERRIDE: Using System Property for '" + key + "': " + systemValue);
            return systemValue;
        }
        return null;
    }


    // --- Delegated Getter Methods (The core delegation logic) ---

    /**
     * Delegates the browser setting request to the currently active configuration.
     * This is the only core property whose value source changes based on 'runOnRemote'.
     */
    public String getBrowser() {
        if (remoteDriverConfig.isRemoteExecution()) {
            // When remote, the browser is pulled from RemoteDriverConfig,
            // which in turn gets it from the remote properties file or system property.
            return remoteDriverConfig.getBrowser();
        }
        return localDriverConfig.getBrowser();
    }

    /**
     * Delegates the implicit wait timeout request.
     * This value is typically consistent whether local or remote, so it relies on LocalDriverConfig.
     */
    public long getImplicitWaitTimeout() {
        return localDriverConfig.getImplicitWaitTimeout();
    }

    /**
     * Delegates the headless mode request. This is only applicable locally.
     */
    public boolean getHeadlessMode() {
        if (remoteDriverConfig.isRemoteExecution()) {
            return false;
        }
        return localDriverConfig.getHeadlessMode();
    }




    // --- Getter Methods for Composed Objects (The Facade Access) ---

    public RemoteDriverConfig getRemoteDriverConfig() {
        return remoteDriverConfig;
    }

    public LocalDriverConfig getLocalDriverConfig() {
        return localDriverConfig;
    }


}
