package insertdoi.event.sections.store;

import insertdoi.event.sections.Section;
import insertdoi.paper.PaperData;

import java.util.ArrayList;
import java.util.List;

public class StoreSectionsSingleton implements StoreSections {

    private static Section instance = null;
    
    private static List<Section> listInstance = new ArrayList<Section>();
    
    @Override
    public Section getSection(PaperData paper) {
        initializeInstance();
        return instance;
    }

    @Override
    public List<Section> getSections() {
        initializeInstance();
        return listInstance;
    }
    
    private static void initializeInstance(){
        if (instance == null) {
            instance = new Section();
            instance.setTitle("Artigos");
            instance.setAbbrev("ART");
            listInstance.add(instance);
        }
    }

}
