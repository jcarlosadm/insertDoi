package insertdoi;

import insertdoi.download.pdfs.DownloadPdfs;
import insertdoi.readxlsx.EventData;
import insertdoi.readxlsx.PaperData;
import insertdoi.readxlsx.XlsxReader;

import java.util.Properties;

public class Main {
    
    public static void main(String[] args) {
        Properties properties = PropertiesGetter.getInstance();
        
        XlsxReader xlsxReader = new XlsxReader(PropertiesConfig.getResourcesFolderName()
                +properties.getProperty(PropertiesConfig.getPropertyXlsxFilename()));
        EventData eventData = xlsxReader.getEventData();
        
        DownloadPdfs downloadPdfs = new DownloadPdfs(eventData);
        downloadPdfs.run();
        
        testEventData(eventData);
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
            System.out.println("   "+paper.getPdfInfo().getName());
            System.out.print("   "+paper.getPdfInfo().getFirstPage());
            System.out.println(" - "+paper.getPdfInfo().getNumberOfPages());
        }
    }
}
