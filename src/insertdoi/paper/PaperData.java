package insertdoi.paper;

import insertdoi.paper.author.Author;
import insertdoi.pdfs.PdfInfo;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class PaperData {

    private String title = "";
    private String track = "";
    private String status = "";
    private String resume = "";
    private List<Author> authors = new ArrayList<Author>();
    private List<String> urls = new ArrayList<String>();
    private PdfInfo pdfInfo = null;
    private String doiString = "";

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTrack() {
        return this.track;
    }

    public void setTrack(String track) {
        this.track = track;
    }

    public String getStatus() {
        return this.status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getResume() {
        return this.resume;
    }

    public void setResume(String resume) {
        this.resume = resume;
    }

    public void addAuthor(Author author){
        this.authors.add(author);
    }
    
    public List<Author> getAuthors(){
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
    
    public void setDoiString(String doiString){
        this.doiString = doiString;
    }
    
    public String getDoiString(){
        return this.doiString;
    }

}
