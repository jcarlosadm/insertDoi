package insertdoi;

public class PropertiesConfig {
    
    private static final String RESOURCES_FOLDER_NAME = "resources/";
    private static final String PROPERTIES_FILE_NAME = "conf.properties";
    
    public static String getResourcesFolderName(){
        return RESOURCES_FOLDER_NAME;
    }
    
    public static String getPropertiesFileName(){
        return PROPERTIES_FILE_NAME;
    }
}
