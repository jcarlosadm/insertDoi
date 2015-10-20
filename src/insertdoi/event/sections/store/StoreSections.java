package insertdoi.event.sections.store;

import java.util.List;

import insertdoi.event.sections.Section;
import insertdoi.paper.PaperData;

public interface StoreSections {
    public Section getSection(PaperData paper);
    public List<Section> getSections();
}
