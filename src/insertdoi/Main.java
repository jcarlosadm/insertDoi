package insertdoi;

import insertdoi.readxlsx.EventData;
import insertdoi.readxlsx.PaperData;
import insertdoi.readxlsx.XlsxReader;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

public class Main {
    
    public static void main(String[] args) {
        Properties properties = getPropertiesFile();
        
        XlsxReader xlsxReader = new XlsxReader(PropertiesConfig.getResourcesFolderName()
                +properties.getProperty("xlsxfile"));
        EventData eventData = xlsxReader.getEventData();
        
        //testEventData(eventData);
        
        
    }

    private static Properties getPropertiesFile() {
        Properties properties = new Properties();
        try {
            FileInputStream fStream = new FileInputStream(PropertiesConfig.getResourcesFolderName()
                    +PropertiesConfig.getPropertiesFileName());
            properties.load(fStream);
        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return properties;
    }
    
    private static void testEventData(EventData eventData) {
        for (PaperData paper : eventData.getPapers()) {
            System.out.println(paper.getTitle());
            for (String author : paper.getAuthors()) {
                System.out.println("    "+author);
            }
            for (String url : paper.getUrls()) {
                System.out.println("    "+url);
            }
        }
    }
}
