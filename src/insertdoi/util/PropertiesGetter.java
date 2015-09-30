package insertdoi.util;

import insertdoi.windows.errorwindow.ErrorWindow;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

public class PropertiesGetter {
    
    private static Properties instance = null;
    
    private PropertiesGetter() {}
    
    public static synchronized Properties getInstance(){
        if (instance == null) {
            Properties properties = new Properties();
            try {
                properties.load(new FileInputStream(PropertiesConfig.getResourcesFolderName()
                        +PropertiesConfig.getPropertiesFileName()));
            } catch (FileNotFoundException e) {
                ErrorWindow.run("Properties File not found");
            } catch (IOException e) {
                ErrorWindow.run("Error to read properties file");
            }
            
            instance = properties;
        }
        
        return instance; 
    }
}
