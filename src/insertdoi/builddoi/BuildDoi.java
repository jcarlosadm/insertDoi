package insertdoi.builddoi;

import java.util.List;
import java.util.Properties;

import insertdoi.event.EventData;
import insertdoi.paper.PaperData;
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
        
        String filename = this.eventData.getXlsxFileName().replace(" ", "_");
        filename = filename.substring(0, filename.lastIndexOf('.'));
        
        doiString += prop.getProperty(PropertiesConfig.getPropertyEventName(filename))+".";
        doiString += prop.getProperty(PropertiesConfig.getPropertySubeventName(filename))+".";
        doiString += prop.getProperty(PropertiesConfig.getPropertyYear())+".";
        doiString += paper.getPdfInfo().getFirstPage();
        
        paper.setDoiString(doiString);
    }
    
}
