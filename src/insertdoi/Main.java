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

import org.apache.log4j.BasicConfigurator;

public class Main {
    
    public static void main(String[] args) {
        BasicConfigurator.configure();
        
        String resourcesFolderName = "./";
        if (PropertiesConfig.getResourcesFolderName() != "") {
            resourcesFolderName = PropertiesConfig.getResourcesFolderName() + File.separator;
        }
        
        String extension = PropertiesConfig.getExtensionXlsxFile();
        
        List<String> xlsxFilesName = GetFileNames.run(resourcesFolderName, extension);
        
        BuildXmlDoiBatch buildXmlDoiBatch = new BuildXmlDoiBatch();
        BuildXmlIssues buildXmlIssues = new BuildXmlIssues();
        
        PdfMap pdfMap = new PdfMap();
        
        int firstpageNextPaper = 1;
        
        for (String fileName : xlsxFilesName) {
            XlsxReader xlsxReader = new XlsxReader(resourcesFolderName+fileName);
            EventData eventData = xlsxReader.getEventData();
            
            DownloadPdfs downloadPdfs = new DownloadPdfs(eventData);
            firstpageNextPaper = downloadPdfs.run(pdfMap, firstpageNextPaper);
            
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
