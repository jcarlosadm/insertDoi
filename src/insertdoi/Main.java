package insertdoi;

import insertdoi.builddoi.BuildDoi;
import insertdoi.event.EventData;
import insertdoi.event.sections.Section;
import insertdoi.pdfs.download.DownloadPdfs;
import insertdoi.pdfs.writer.PdfWriter;
import insertdoi.readxlsx.XlsxReader;
import insertdoi.texfile.TexfileBuilder;
import insertdoi.util.PropertiesConfig;
import insertdoi.util.PropertiesGetter;
import insertdoi.util.getfilenames.GetFileNames;
import insertdoi.util.windows.finishWindow.FinishWindow;
import insertdoi.xml.build.issues.BuildXmlIssues;
import insertdoi.xml.build.issues.articles.BuildXmlArticles;

import java.io.File;
import java.util.List;
import java.util.Properties;

import org.apache.log4j.BasicConfigurator;

public class Main {
    
    public static void main(String[] args) {
        BasicConfigurator.configure();
        
        boolean noWritePdfs = false;
        for (int index = 0; index < args.length; index++) {
            if (args[index].equals("nowritepdfs")) {
                noWritePdfs = true;
            }
        }
        
        Properties prop = PropertiesGetter.getInstance();
        
        String resourcesFolderName = "./";
        if (PropertiesConfig.getResourcesFolderName() != "") {
            resourcesFolderName = PropertiesConfig.getResourcesFolderName() + File.separator;
        }
        
        String extension = PropertiesConfig.getExtensionXlsxFile();
        
        List<String> xlsxFilesName = GetFileNames.run(resourcesFolderName, extension);
        
        TexfileBuilder texfileBuilder = new TexfileBuilder();
        BuildXmlIssues buildXmlIssues = new BuildXmlIssues();
        
        int firstpageNextPaper = 1;
        
        boolean divideByXlsxFile = Boolean.valueOf(prop.getProperty(PropertiesConfig
                .getPropertySplitByFileName()));
        
        BuildXmlArticles buildXmlArticles = null;
        if (divideByXlsxFile) {
            buildXmlArticles = new BuildXmlArticles();
        }
        
        for (int index = 0; index < xlsxFilesName.size(); index++) {
            String fileName = xlsxFilesName.get(index);
            
            XlsxReader xlsxReader = new XlsxReader(resourcesFolderName+fileName);
            EventData eventData = xlsxReader.getEventData();
            
            DownloadPdfs downloadPdfs = new DownloadPdfs(eventData);
            firstpageNextPaper = downloadPdfs.run(firstpageNextPaper);
            
            BuildDoi buildDoi = new BuildDoi(eventData);
            buildDoi.run();
            
            if (!noWritePdfs) {
                PdfWriter pdfWriter = new PdfWriter(eventData);
                pdfWriter.run();
            }
            
            for (Section section : eventData.getSections()) {
                if (divideByXlsxFile && index > 0) {
                    String xmlname = fileName.substring(0, fileName.lastIndexOf('.'))
                            +"."+section.getAbbrev()+".xml";
                    buildXmlArticles.setSection(section);
                    buildXmlArticles.setSourceFilename(fileName);
                    buildXmlArticles.run(xmlname);
                } else {
                    buildXmlIssues.addSection(section);
                }
                
                texfileBuilder.addSection(section);
            }
            
            if (divideByXlsxFile && index == 0) {
                String xmlname = fileName.substring(0, fileName.lastIndexOf('.'))+".xml";
                buildXmlIssues.setSourceFilename(fileName);
                buildXmlIssues.run(xmlname);
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
