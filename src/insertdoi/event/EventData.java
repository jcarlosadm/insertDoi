package insertdoi.event;

import insertdoi.paper.PaperData;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class EventData {
    private List<PaperData> papers = new ArrayList<PaperData>();
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
        return Collections.unmodifiableList(this.papers);
    }
}
