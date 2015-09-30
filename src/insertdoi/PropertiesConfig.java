package insertdoi;

public class PropertiesConfig {
    
    private static final String RESOURCES_FOLDER_NAME = "resources/";
    private static final String PROPERTIES_FILE_NAME = "conf.properties";
    
    private static final String PROPERTY_XLSX_FILENAME = "xlsxfile";
    
    public static String getResourcesFolderName(){
        return RESOURCES_FOLDER_NAME;
    }
    
    public static String getPropertiesFileName(){
        return PROPERTIES_FILE_NAME;
    }
    
    public static String getPropertyXlsxFilename(){
        return PROPERTY_XLSX_FILENAME;
    }
}
