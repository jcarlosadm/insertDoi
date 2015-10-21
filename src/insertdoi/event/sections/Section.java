package insertdoi.event.sections;

import insertdoi.paper.PaperData;

import java.util.ArrayList;
import java.util.List;

public class Section {
    private String title = "";
    private String abbrev = "";
    private String suffix = "";
    private List<PaperData> papers = new ArrayList<PaperData>();

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAbbrev() {
        return this.abbrev;
    }

    public void setAbbrev(String abbrev) {
        this.abbrev = abbrev;
    }

    public List<PaperData> getPapers() {
        return this.papers;
    }

    public void addPaper(PaperData paper){
        this.papers.add(paper);
    }

    public String getSuffix() {
        return this.suffix;
    }

    public void setSuffix(String suffix) {
        this.suffix = suffix;
    }
}
