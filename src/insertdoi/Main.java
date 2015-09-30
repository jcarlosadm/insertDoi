package insertdoi;

import insertdoi.event.EventData;
import insertdoi.event.PaperData;
import insertdoi.pdfs.download.DownloadPdfs;
import insertdoi.readxlsx.XlsxReader;
import insertdoi.util.PropertiesConfig;
import insertdoi.util.PropertiesGetter;

import java.util.Properties;

public class Main {
    
    public static void main(String[] args) {
        Properties properties = PropertiesGetter.getInstance();
        
        XlsxReader xlsxReader = new XlsxReader(PropertiesConfig.getResourcesFolderName()
                +properties.getProperty(PropertiesConfig.getPropertyXlsxFilename()));
        EventData eventData = xlsxReader.getEventData();
        
        DownloadPdfs downloadPdfs = new DownloadPdfs(eventData);
        downloadPdfs.run();
        
        // TODO build doi
        // TODO build xml
        
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
            System.out.println("    pdfname: "+paper.getPdfInfo().getName());
            System.out.print("    firstpage: "+paper.getPdfInfo().getFirstPage());
            System.out.println(" ; numberofpages: "+paper.getPdfInfo().getNumberOfPages());
        }
    }
}
