package insertdoi;

import insertdoi.builddoi.BuildDoi;
import insertdoi.event.EventData;
import insertdoi.paper.PaperData;
import insertdoi.paper.author.Author;
import insertdoi.pdfs.download.DownloadPdfs;
import insertdoi.readxlsx.XlsxReader;
import insertdoi.texfile.TexfileBuilder;
import insertdoi.util.PropertiesConfig;
import insertdoi.util.PropertiesGetter;
import insertdoi.util.getfilenames.GetFileNames;
import insertdoi.util.windows.finishWindow.FinishWindow;
import insertdoi.xml.BuildXmlInfo;

import java.io.File;
import java.util.List;
import java.util.Properties;

public class Main {
    
    public static void main(String[] args) {
        Properties properties = PropertiesGetter.getInstance();
        String resourcesFolderName = PropertiesConfig.getResourcesFolderName();
        
        List<String> xlsxFilesName = GetFileNames.run(resourcesFolderName, "xlsx");
        
        for (String fileName : xlsxFilesName) {
            XlsxReader xlsxReader = new XlsxReader(resourcesFolderName+File.separator+fileName);
            EventData eventData = xlsxReader.getEventData();
            
            testEventData(eventData);
        }
        
        
        /*
        DownloadPdfs downloadPdfs = new DownloadPdfs(eventData);
        downloadPdfs.run();
        
        BuildDoi buildDoi = new BuildDoi(eventData);
        buildDoi.run();
        
        BuildXmlInfo buildXmlInfo = new BuildXmlInfo(eventData);
        buildXmlInfo.run();
        
        TexfileBuilder texfileBuilder = new TexfileBuilder(eventData);
        texfileBuilder.run();
        
        */
        FinishWindow.run();
    }

    private static void testEventData(EventData eventData) {
        for (PaperData paper : eventData.getPapers()) {
            System.out.println("\n"+paper.getTitle());
            System.out.println(">>track:"+paper.getTrack());
            System.out.println(">>status:"+paper.getStatus());
            for (Author author : paper.getAuthors()) {
                System.out.print(">>author:"+author.getName());
                System.out.print(":"+author.getEmail());
                System.out.println(":"+author.getNameWithAffiliation());
            }
            for (String url : paper.getUrls()) {
                System.out.println(">>url:"+url);
            }
            System.out.println(">>abstract:"+paper.getResume());
        }
    }
}
