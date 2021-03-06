package insertdoi.pdfs.download;

import insertdoi.event.EventData;
import insertdoi.event.sections.Section;
import insertdoi.paper.PaperData;
import insertdoi.pdfs.PdfInfo;
import insertdoi.util.PropertiesConfig;
import insertdoi.util.PropertiesGetter;
import insertdoi.util.windows.errorwindow.ErrorWindow;
import insertdoi.util.windows.progressbar.ProgressBar;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Properties;

import org.apache.commons.io.FileUtils;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;

public class DownloadPdfs {
    
    private static final boolean EVEN_PAGES_ONLY = false;

    // TODO test in latex
    private static final boolean ADJUST_SINGLE_PAGE = true;
    
    private EventData eventData = null;
    
    public DownloadPdfs(EventData eventData) {
        this.eventData = eventData;
    }
    
    public int run(int firstpageNextPaper) {
        System.setProperty("jsse.enableSNIExtension", "false");
        
        int totalOperations = 0;
        
        for (Section section : this.eventData.getSections()) {
            totalOperations += section.getPapers().size();
        }
        
        ProgressBar progressBar = ProgressBar.getInstance("Articles", "Downloading pdfs...");
        progressBar.clearOperations();
        progressBar.defineTotalOperations(totalOperations);
        
        this.createDownloadFolder();
        
        for (Section section : this.eventData.getSections()) {
            for (PaperData paper : section.getPapers()) {
                if (paper.getPdfInfo() == null 
                        || paper.getPdfInfo().getFirstPage() == 0) {
                    firstpageNextPaper = 
                            this.downloadPdf(paper, progressBar, firstpageNextPaper);
                }
            }
        }
        
        progressBar.closeProgressBar();
        
        return firstpageNextPaper;
    }
    
    private void createDownloadFolder() {
        File folder = new File(PropertiesConfig.getOutputFolderName());
        if (!folder.exists()) {
            folder.mkdir();
        }
        
        folder = new File(PropertiesConfig.getOutputFolderName() + File.separator
                + PropertiesGetter.getInstance().getProperty(
                        PropertiesConfig.getPropertyArticlesFolderName()));
        if (!folder.exists()) {
            folder.mkdir();
        }
    }

    private int downloadPdf(PaperData paper, ProgressBar progressBar, int firstPage) {
        
        String urlString = this.insertUserAndPassword(paper.getPdfUrlFinal());
        String pdfname = this.getFilenameFromUrl(urlString);
        
        int totalPages = this.getfile(urlString, pdfname);
        
        PdfInfo pdfInfo = new PdfInfo();
        pdfInfo.setName(pdfname);
        pdfInfo.setFirstPage(firstPage);
        pdfInfo.setNumberOfPages(totalPages);
        paper.setPdfInfo(pdfInfo);
        
        progressBar.finishOneOperation();
        return firstPage + totalPages;
    }

    private int getfile(String urlString, String filename) {
        
        String foldername = PropertiesGetter.getInstance().getProperty(
                PropertiesConfig.getPropertyArticlesFolderName());
        filename = PropertiesConfig.getOutputFolderName()+File.separator
                + foldername +File.separator+ filename;
        
        if ((new File(filename)).exists()) {
            return getTotalPages(filename);
        }
        
        try {
            FileUtils.copyURLToFile(new URL(urlString), new File(filename));
        } catch (MalformedURLException e) {
            ErrorWindow.run("Article url error: "+urlString);
        } catch (IOException e) {
            ErrorWindow.run("Error to download pdf file");
        }
        
        return getTotalPages(filename);
    }

    @SuppressWarnings("unused")
    private int getTotalPages(String filename) {
        int numberOfPages = 0;
        
        try {
            PDDocument pdfFile = PDDocument.load(new File(filename));
            numberOfPages = pdfFile.getNumberOfPages();
            
            if (ADJUST_SINGLE_PAGE && numberOfPages == 1) {
                pdfFile.addPage(new PDPage());
                pdfFile.save(filename);
                numberOfPages = 2;
            } else if (EVEN_PAGES_ONLY && numberOfPages % 2 != 0){
                pdfFile.addPage(new PDPage());
                pdfFile.save(filename);
                numberOfPages += 1;
            }
            
            pdfFile.close();
        } catch (IOException e) {
            ErrorWindow.run("Error to read pdf file");
        }
        
        return numberOfPages;
    }

    private String insertUserAndPassword(String urlString) {
        Properties prop = PropertiesGetter.getInstance();
        
        String filename = this.eventData.getXlsxFileName();
        
        String user = prop.getProperty(PropertiesConfig.getUserPropertyName(filename));
        String password = prop.getProperty(PropertiesConfig.getPasswordPropertyName(filename));
        
        int index = urlString.indexOf("PS.cgi?") + "PS.cgi?".length();
        
        String base = urlString.substring(0, index);
        String end = urlString.substring(index, urlString.length());
        String middle = "user="+user+"&pw="+password+"&";
        
        return base+middle+end;
    }

    private String getFilenameFromUrl(String urlString) {
        return urlString.substring(urlString.lastIndexOf('=') + 1);
    }
}
