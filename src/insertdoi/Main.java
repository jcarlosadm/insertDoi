package insertdoi;

import insertdoi.builddoi.BuildDoi;
import insertdoi.event.EventData;
import insertdoi.event.sections.Section;
import insertdoi.pdfs.PdfMap;
import insertdoi.pdfs.download.DownloadPdfs;
import insertdoi.pdfs.writer.PdfWriter;
import insertdoi.readxlsx.XlsxReader;
import insertdoi.texfile.TexfileBuilder;
import insertdoi.util.PropertiesConfig;
import insertdoi.util.PropertiesGetter;
import insertdoi.util.getfilenames.GetFileNames;
import insertdoi.util.windows.finishWindow.FinishWindow;
import insertdoi.xml.build.issues.BuildXmlIssues;

import java.io.File;
import java.util.List;
import java.util.Properties;

import org.apache.log4j.BasicConfigurator;

public class Main {
    
    public static void main(String[] args) {
        BasicConfigurator.configure();
        
        Properties prop = PropertiesGetter.getInstance();
        
        String resourcesFolderName = "./";
        if (PropertiesConfig.getResourcesFolderName() != "") {
            resourcesFolderName = PropertiesConfig.getResourcesFolderName() + File.separator;
        }
        
        String extension = PropertiesConfig.getExtensionXlsxFile();
        
        List<String> xlsxFilesName = GetFileNames.run(resourcesFolderName, extension);
        
        TexfileBuilder texfileBuilder = new TexfileBuilder();
        BuildXmlIssues buildXmlIssues = new BuildXmlIssues();
        
        PdfMap pdfMap = new PdfMap();
        
        int firstpageNextPaper = 1;
        
        boolean divideByXlsxFile = Boolean.valueOf(prop.getProperty(PropertiesConfig
                .getPropertySplitByFileName()));
        
        for (String fileName : xlsxFilesName) {
            XlsxReader xlsxReader = new XlsxReader(resourcesFolderName+fileName);
            EventData eventData = xlsxReader.getEventData();
            
            DownloadPdfs downloadPdfs = new DownloadPdfs(eventData);
            firstpageNextPaper = downloadPdfs.run(pdfMap, firstpageNextPaper);
            
            BuildDoi buildDoi = new BuildDoi(eventData);
            buildDoi.run();
            
            PdfWriter pdfWriter = new PdfWriter(eventData);
            pdfWriter.run();
            
            for (Section section : eventData.getSections()) {
                buildXmlIssues.addSection(section);
                texfileBuilder.addSection(section);
            }
            
            if (divideByXlsxFile == true) {
                String xmlname = fileName.substring(0, fileName.lastIndexOf('.'))+".xml";
                buildXmlIssues.run(xmlname);
                buildXmlIssues = new BuildXmlIssues();
            }
        }
        
        if (divideByXlsxFile == false) {
            String xmlFinalFilename = prop.getProperty(PropertiesConfig
                    .getPropertyTitleName())+".xml";
            
            buildXmlIssues.run(xmlFinalFilename);
        }
        
        texfileBuilder.run();
        
        FinishWindow.run();
    }
}
