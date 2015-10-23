package insertdoi.xml.build.issues.articles;

import insertdoi.event.sections.Section;
import insertdoi.paper.PaperData;
import insertdoi.xml.build.BuildXml;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

public class BuildXmlArticles extends BuildXml {

    private static final String ROOT_NAME = "articles";
    
    private Section section = null;

    public void setSection(Section section) {
        this.section = section;
    }
    
    @Override
    protected void setRootProperties(Element rootElement, Document doc) {
    }

    @Override
    protected void addElementsToRoot(Document doc, Element rootElement) {
        BuildArticle buildArticle = new BuildArticle();
        
        for (PaperData paper : this.section.getPapers()) {
            buildArticle.addArticle(paper, doc, rootElement);
        }
    }

    @Override
    protected String getRootElementName() {
        return ROOT_NAME;
    }

    @Override
    protected void prebuildAlgorithms() {
    }

}
