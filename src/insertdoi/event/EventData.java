package insertdoi.event;

import insertdoi.event.sections.Section;
import insertdoi.paper.PaperData;

import java.util.ArrayList;
import java.util.List;

public class EventData {
    private List<PaperData> papers = new ArrayList<PaperData>();
    private List<Section> sections = new ArrayList<Section>();
    private String xlsxFileName = "";
    
    public void setXlsxFileName(String xlsxFileName){
        this.xlsxFileName = xlsxFileName;
    }
    
    public String getXlsxFileName(){
        return this.xlsxFileName;
    }
    
    public void addPaper(PaperData paper){
        this.papers.add(paper);
    }
    
    public List<PaperData> getPapers(){
        return this.papers;
    }
    
    public List<Section> getSections(){
        return this.sections;
    }
    
    public void setSections(List<Section> sections){
        this.sections = sections;
    }
}
