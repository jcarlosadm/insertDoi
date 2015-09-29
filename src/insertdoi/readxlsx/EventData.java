package insertdoi.readxlsx;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class EventData {
    private List<PaperData> papers = new ArrayList<PaperData>();
    
    public void addPaper(PaperData paper){
        this.papers.add(paper);
    }
    
    public List<PaperData> getPapers(){
        return Collections.unmodifiableList(this.papers);
    }
}
