package insertdoi.readxlsx;

import insertdoi.pdfs.PdfInfo;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class PaperData {

    private String title = "";
    private List<String> authors = new ArrayList<String>();
    private List<String> urls = new ArrayList<String>();
    private PdfInfo pdfInfo = null;

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void addAuthor(String author){
        this.authors.add(author);
    }
    
    public List<String> getAuthors(){
        return Collections.unmodifiableList(this.authors);
    }
    
    public void addUrl(String url){
        this.urls.add(url);
    }
    
    public List<String> getUrls(){
        return Collections.unmodifiableList(this.urls);
    }
    
    public void setPdfInfo(PdfInfo pdfInfo){
        this.pdfInfo = pdfInfo;
    }
    
    public PdfInfo getPdfInfo(){
        return this.pdfInfo;
    }

}
