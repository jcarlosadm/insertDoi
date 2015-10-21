package insertdoi.event.sections.store;

import insertdoi.event.sections.Section;
import insertdoi.paper.PaperData;
import insertdoi.util.PropertiesConfig;
import insertdoi.util.PropertiesGetter;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class StoreSectionUnique implements StoreSections {

    private Section section = null;
    private List<Section> sections = new ArrayList<Section>();
    
    public StoreSectionUnique(String filename) {
        Properties prop = PropertiesGetter.getInstance();
        
        Section section = new Section();
        section.setTitle(prop.getProperty(PropertiesConfig
                .getPropertyBySectionTitleName(filename)));
        section.setAbbrev(prop.getProperty(PropertiesConfig
                .getPropertyBySectionAbbrevName(filename)));
        
        this.section = section;
        this.sections.add(section);
    }
    
    @Override
    public Section getSection(PaperData paper) {
        return this.section;
    }

    @Override
    public List<Section> getSections() {
        return this.sections;
    }

}
