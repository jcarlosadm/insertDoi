package insertdoi.builddoi;

import java.util.List;
import java.util.Properties;

import insertdoi.event.EventData;
import insertdoi.event.PaperData;
import insertdoi.util.PropertiesConfig;
import insertdoi.util.PropertiesGetter;

public class BuildDoi {
    
    private EventData eventData = null;
    
    public BuildDoi(EventData eventData) {
        this.eventData = eventData;
    }
    
    public void run() {
        List<PaperData> papers = this.eventData.getPapers();
        
        for (PaperData paper : papers) {
            this.insertDoi(paper);
        }
    }

    private void insertDoi(PaperData paper) {
        Properties prop = PropertiesGetter.getInstance();
        String doiString = prop.getProperty(PropertiesConfig.getPropertyDefaultDoiString());
        
        doiString += prop.getProperty(PropertiesConfig.getPropertyEventName())+".";
        doiString += prop.getProperty(PropertiesConfig.getPropertySubeventName())+".";
        doiString += prop.getProperty(PropertiesConfig.getPropertyYear())+".";
        doiString += paper.getPdfInfo().getFirstPage();
        
        paper.setDoiString(doiString);
    }
    
}
