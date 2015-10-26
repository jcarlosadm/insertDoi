package insertdoi.event.sections;

import insertdoi.paper.PaperData;

import java.util.ArrayList;
import java.util.List;

public class Section implements Cloneable {
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
        if (!this.papers.contains(paper)) {
            this.papers.add(paper);
        }
    }
    
    public void clearPapers(){
        this.papers = new ArrayList<PaperData>();
    }

    public String getSuffix() {
        return this.suffix;
    }

    public void setSuffix(String suffix) {
        this.suffix = suffix;
    }
    
    @Override
    public boolean equals(Object obj) {
        boolean equal = false;
        
        if (obj != null && obj instanceof Section) {
            equal = (obj.hashCode() == this.hashCode());
        }
        
        return equal;
    }
    
    @Override
    public Object clone() throws CloneNotSupportedException {
        return super.clone();
    }
}
