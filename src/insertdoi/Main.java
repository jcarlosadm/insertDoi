package insertdoi;

import insertdoi.builddoi.BuildDoi;
import insertdoi.event.EventData;
import insertdoi.paper.PaperData;
import insertdoi.pdfs.PdfMap;
import insertdoi.pdfs.download.DownloadPdfs;
import insertdoi.readxlsx.XlsxReader;
import insertdoi.texfile.TexfileBuilder;
import insertdoi.util.PropertiesConfig;
import insertdoi.util.getfilenames.GetFileNames;
import insertdoi.util.windows.finishWindow.FinishWindow;
import insertdoi.xml.doibatch.build.BuildXmlDoiBatch;
import insertdoi.xml.issues.build.BuildXmlIssues;

import java.io.File;
import java.util.List;

public class Main {
    
    public static void main(String[] args) {
        String resourcesFolderName = PropertiesConfig.getResourcesFolderName();
        String extension = PropertiesConfig.getExtensionXlsxFile();
        
        List<String> xlsxFilesName = GetFileNames.run(resourcesFolderName, extension);
        
        BuildXmlDoiBatch buildXmlDoiBatch = new BuildXmlDoiBatch();
        BuildXmlIssues buildXmlIssues = new BuildXmlIssues();
        
        PdfMap pdfMap = new PdfMap();
        
        for (String fileName : xlsxFilesName) {
            XlsxReader xlsxReader = new XlsxReader(resourcesFolderName+File.separator+fileName);
            EventData eventData = xlsxReader.getEventData();
            
            DownloadPdfs downloadPdfs = new DownloadPdfs(eventData);
            downloadPdfs.run(pdfMap);
            
            BuildDoi buildDoi = new BuildDoi(eventData);
            buildDoi.run();
            
            for (PaperData paper : eventData.getPapers()) {
                buildXmlDoiBatch.addPaper(paper);
                buildXmlIssues.addPaper(paper);
            }
        }
        
        buildXmlDoiBatch.run();
        buildXmlIssues.run();
        
        TexfileBuilder texfileBuilder = new TexfileBuilder();
        texfileBuilder.run(pdfMap);
        
        FinishWindow.run();
    }
}
