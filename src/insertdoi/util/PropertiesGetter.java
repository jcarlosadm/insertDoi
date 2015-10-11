package insertdoi.util;

import insertdoi.util.windows.errorwindow.ErrorWindow;

import java.io.File;
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
                String path = PropertiesConfig.getResourcesFolderName()+File.separator;
                path += PropertiesConfig.getPropertiesFileName();
                
                properties.load(new FileInputStream(path));
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
