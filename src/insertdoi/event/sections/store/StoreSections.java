package insertdoi.event.sections.store;

import insertdoi.event.sections.Section;
import insertdoi.paper.PaperData;

import java.util.List;

public interface StoreSections {
    public Section getSection(PaperData paper);
    public List<Section> getSections();
}
