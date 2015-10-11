package insertdoi;

import insertdoi.event.EventData;
import insertdoi.pdfs.download.DownloadPdfs;
import insertdoi.readxlsx.XlsxReader;
import insertdoi.util.PropertiesConfig;
import insertdoi.util.getfilenames.GetFileNames;
import insertdoi.util.windows.finishWindow.FinishWindow;

import java.io.File;
import java.util.List;

public class Main {
    
    public static void main(String[] args) {
        String resourcesFolderName = PropertiesConfig.getResourcesFolderName();
        String extension = PropertiesConfig.getExtensionXlsxFile();
        
        List<String> xlsxFilesName = GetFileNames.run(resourcesFolderName, extension);
        
        for (String fileName : xlsxFilesName) {
            XlsxReader xlsxReader = new XlsxReader(resourcesFolderName+File.separator+fileName);
            EventData eventData = xlsxReader.getEventData();
            
            DownloadPdfs downloadPdfs = new DownloadPdfs(eventData);
            downloadPdfs.run();
        }
        
        
        /*
        BuildDoi buildDoi = new BuildDoi(eventData);
        buildDoi.run();
        
        BuildXmlInfo buildXmlInfo = new BuildXmlInfo(eventData);
        buildXmlInfo.run();
        
        TexfileBuilder texfileBuilder = new TexfileBuilder(eventData);
        texfileBuilder.run();
        
        */
        FinishWindow.run();
    }
}
