package insertdoi.xml.doibatch.build;

import insertdoi.util.PropertiesConfig;
import insertdoi.util.PropertiesGetter;
import insertdoi.util.windows.errorwindow.ErrorWindow;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

import org.apache.commons.codec.binary.Base64;

public class EncodePdfFile {
    
    public static String getFileInBase64(String filename){
        Properties prop = PropertiesGetter.getInstance();
        
        String outputFolder = PropertiesConfig.getOutputFolderName();
        String pdfFolder = prop.getProperty(PropertiesConfig.getPropertyArticlesFolderName());
        File file = new File(outputFolder+File.separator+pdfFolder+File.separator+filename);
        
        String encodedFile = "";
        try {
            FileInputStream fStream = new FileInputStream(file);
            byte[] bytes = new byte[(int)file.length()];
            fStream.read(bytes);
            encodedFile = Base64.encodeBase64String(bytes);
            fStream.close();
        } catch (FileNotFoundException e) {
            ErrorWindow.run("pdf file not found");
        } catch (IOException e){
            ErrorWindow.run("Error to encode pdf file to base 64");
        }
        
        return encodedFile;
    }
}
