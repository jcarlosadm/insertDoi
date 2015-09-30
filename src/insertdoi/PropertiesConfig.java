package insertdoi;


public class PropertiesConfig {
    
    private static final String RESOURCES_FOLDER_NAME = "resources/";
    private static final String OUTPUT_FOLDER_NAME = "outputs/";
    private static final String PROPERTIES_FILE_NAME = "conf.properties";
    
    private static final String PROPERTY_XLSX_FILENAME = "xlsxfile";
    private static final String PROPERTY_ARTICLES_FOLDER_NAME = "articles_folder";
    private static final String PROPERTY_JEMS_USER = "jems_user";
    private static final String PROPERTY_JEMS_PASSWORD = "jems_password";
    
    public static String getResourcesFolderName(){
        return RESOURCES_FOLDER_NAME;
    }
    
    public static String getPropertiesFileName(){
        return PROPERTIES_FILE_NAME;
    }
    
    public static String getPropertyXlsxFilename(){
        return PROPERTY_XLSX_FILENAME;
    }
    
    public static String getOutputFolderName(){
        return OUTPUT_FOLDER_NAME;
    }
    
    public static String getPropertyArticlesFolderName(){
        return PROPERTY_ARTICLES_FOLDER_NAME;
    }
    
    public static String getPropertyJemsUser(){
        return PROPERTY_JEMS_USER;
    }
    
    public static String getPropertyJemsPassword(){
        return PROPERTY_JEMS_PASSWORD;
    }
}
