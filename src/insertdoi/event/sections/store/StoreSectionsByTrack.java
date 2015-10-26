package insertdoi.event.sections.store;

import insertdoi.event.sections.Section;
import insertdoi.paper.PaperData;
import insertdoi.util.PropertiesConfig;
import insertdoi.util.PropertiesGetter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

public class StoreSectionsByTrack implements StoreSections {

    private Map<String, Section> mapSections = new HashMap<String, Section>();
    private List<Section> sections = new ArrayList<Section>();
    
    public StoreSectionsByTrack(String filename) {
        Properties prop = PropertiesGetter.getInstance();
        
        Section section = null;
        int order = 1;
        
        String trackName = prop.getProperty(PropertiesConfig
                .getPropertyByTrackTrackName(filename, order));
        
        while (trackName != null) {
            section = new Section();
            section.setTitle(prop.getProperty(PropertiesConfig
                    .getPropertyByTrackTitleName(filename, order)));
            section.setAbbrev(prop.getProperty(PropertiesConfig
                    .getPropertyByTrackAbbrevName(filename, order)));
            section.setSuffix(prop.getProperty(PropertiesConfig.
                    getPropertyByTrackSuffixName(filename, order)));
            
            this.mapSections.put(trackName, section);
            this.sections.add(section);
            
            order+=1;
            trackName = prop.getProperty(PropertiesConfig
                    .getPropertyByTrackTrackName(filename, order));
        }
    }
    
    @Override
    public Section getSection(PaperData paper) {
        return this.mapSections.get(paper.getTrack());
    }

    @Override
    public List<Section> getSections() {
        return this.sections;
    }
}
