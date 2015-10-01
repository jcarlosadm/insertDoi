package insertdoi;

import insertdoi.builddoi.BuildDoi;
import insertdoi.event.EventData;
import insertdoi.pdfs.download.DownloadPdfs;
import insertdoi.readxlsx.XlsxReader;
import insertdoi.texfile.TexfileBuilder;
import insertdoi.util.PropertiesConfig;
import insertdoi.util.PropertiesGetter;
import insertdoi.xml.BuildXmlInfo;

import java.util.Properties;

public class Main {
    
    public static void main(String[] args) {
        Properties properties = PropertiesGetter.getInstance();
        
        XlsxReader xlsxReader = new XlsxReader(PropertiesConfig.getResourcesFolderName()
                +properties.getProperty(PropertiesConfig.getPropertyXlsxFilename()));
        EventData eventData = xlsxReader.getEventData();
        
        DownloadPdfs downloadPdfs = new DownloadPdfs(eventData);
        downloadPdfs.run();
        
        BuildDoi buildDoi = new BuildDoi(eventData);
        buildDoi.run();
        
        BuildXmlInfo buildXmlInfo = new BuildXmlInfo(eventData);
        buildXmlInfo.run();
        
        TexfileBuilder texfileBuilder = new TexfileBuilder(eventData);
        texfileBuilder.run();
        
    }
}
