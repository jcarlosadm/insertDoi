package insertdoi.builddoi;

import insertdoi.event.EventData;
import insertdoi.paper.PaperData;
import insertdoi.util.PropertiesConfig;
import insertdoi.util.PropertiesGetter;

import java.util.List;
import java.util.Properties;

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
        String filename = this.eventData.getXlsxFileName();
        boolean value = Boolean.valueOf(prop.getProperty(PropertiesConfig
                .getPropertyBuildDoiName(filename)));
        
        if (value == false) {
            paper.setDoiString(null);
        } else {
            String doiString = prop.getProperty(PropertiesConfig.getPropertyDefaultDoiString());
            
            doiString += prop.getProperty(PropertiesConfig.getPropertyEventName(filename))+".";
            doiString += prop.getProperty(PropertiesConfig.getPropertySubeventName(filename))+".";
            doiString += prop.getProperty(PropertiesConfig.getPropertyYear())+".";
            doiString += paper.getPdfInfo().getFirstPage();
            
            paper.setDoiString(doiString);
        }
    }
    
}
