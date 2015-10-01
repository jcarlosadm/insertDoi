package insertdoi.xml.articles;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.List;

public class ArticleInfo {
    private String journalTitle = "";
    private String journalAbbrevTitle = "";
    private List<IssnJournal> issn = new ArrayList<IssnJournal>();
    
    private Calendar publicationDate;
    private int issue;
    private int volume;
    
    private List<String> articleTitles = new ArrayList<String>();
    private List<Contributor> contributors = new ArrayList<Contributor>();
    private int firstPage;
    private String doi = "";
    private String resource = "";
    
    public String getJournalTitle() {
        return this.journalTitle;
    }
    public void setJournalTitle(String journalTitle) {
        this.journalTitle = journalTitle;
    }
    public String getJournalAbbrevTitle() {
        return this.journalAbbrevTitle;
    }
    public void setJournalAbbrevTitle(String journalAbbrevTitle) {
        this.journalAbbrevTitle = journalAbbrevTitle;
    }
    public Calendar getPublicationDate() {
        return this.publicationDate;
    }
    public void setPublicationDate(Calendar publicationDate) {
        this.publicationDate = publicationDate;
    }
    public int getIssue() {
        return this.issue;
    }
    public void setIssue(int issue) {
        this.issue = issue;
    }
    public int getVolume() {
        return this.volume;
    }
    public void setVolume(int volume) {
        this.volume = volume;
    }
    public int getFirstPage() {
        return this.firstPage;
    }
    public void setFirstPage(int firstPage) {
        this.firstPage = firstPage;
    }
    public String getDoi() {
        return this.doi;
    }
    public void setDoi(String doi) {
        this.doi = doi;
    }
    
    public boolean addIssn(IssnJournal issnJournal){
        return this.issn.add(issnJournal);
    }
    
    public List<IssnJournal> getIssnList(){
        return Collections.unmodifiableList(this.issn);
    }
    
    public boolean addArticleTitle(String title){
        return this.articleTitles.add(title);
    }
    
    public List<String> getArticleTitlesList(){
        return Collections.unmodifiableList(this.articleTitles);
    }
    
    public boolean addContributor(Contributor contributor){
        return this.contributors.add(contributor);
    }
    
    public List<Contributor> getContributorsList(){
        return Collections.unmodifiableList(this.contributors);
    }
    
    public String getResource() {
        return this.resource;
    }
    
    public void setResource(String resource) {
        this.resource = resource;
    }
}
