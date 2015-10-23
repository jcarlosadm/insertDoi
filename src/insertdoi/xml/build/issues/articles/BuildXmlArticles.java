package insertdoi.xml.build.issues.articles;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import insertdoi.event.sections.Section;
import insertdoi.paper.PaperData;
import insertdoi.util.PropertiesConfig;
import insertdoi.util.PropertiesGetter;
import insertdoi.util.windows.errorwindow.ErrorWindow;
import insertdoi.xml.build.BuildXml;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

public class BuildXmlArticles extends BuildXml {

    private static final String ROOT_NAME = "articles";
    
    private Section section = null;
    
    private String sourceFilename = "";

    private boolean enableDivision = true;

    public void setSection(Section section) {
        this.section = section;
    }
    
    public void setSourceFilename(String filename){
        this.sourceFilename = filename;
    }
    
    public void disableDivision(){
        this.enableDivision  = false;
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
    protected void prebuildAlgorithms(String xmlFinalFilename) {
        if (this.enableDivision == false) {
            return;
        }
        
        Properties prop = PropertiesGetter.getInstance();
        
        String divisionProp = prop.getProperty(PropertiesConfig
                .getPropertySplitDivisionByFilename(this.sourceFilename));
        
        int division = 0;
        if (divisionProp != null && divisionProp != "") {
            division = Integer.parseInt(divisionProp);
        }
        
        if (division <= 1) {
            division = Integer.parseInt(prop.getProperty(PropertiesConfig
                    .getPropertySplitDivideName()));
        }
        
        if (division <= 1) {
            return;
        }
        
        int numberOfArticles = this.section.getPapers().size();
        int numberOfArticlesByFile = (int) Math
                .ceil(((double) numberOfArticles) / division);
        
        Section mainSection = null;
        try {
            mainSection = (Section) this.section.clone();
            mainSection.clearPapers();
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
            ErrorWindow.run("Error to divide xml file");
        }
        
        int auxSectionCount = 0;
        List<Section> auxSections = new ArrayList<Section>();
        Section auxCurrentSection = new Section();
        auxSections.add(auxCurrentSection);
        
        for (int index = 0; index < this.section.getPapers().size(); index++) {
            PaperData paper = this.section.getPapers().get(index);
            
            if (index < numberOfArticlesByFile) {
                mainSection.addPaper(paper);
            } else {
                if (auxSectionCount >= numberOfArticlesByFile) {
                    auxCurrentSection = new Section();
                    auxSections.add(auxCurrentSection);
                    auxSectionCount = 0;
                }
                
                auxCurrentSection.addPaper(paper);
                auxSectionCount++;
            }
        }
        
        BuildXmlArticles buildXmlArticles = new BuildXmlArticles();
        for (int index = 0; index < auxSections.size(); index++) {
            buildXmlArticles.disableDivision();
            buildXmlArticles.setSourceFilename(this.sourceFilename);
            buildXmlArticles.setSection(auxSections.get(index));
            buildXmlArticles.run(xmlFinalFilename+"."+(index+1)+".xml");
        }
        
        this.section = mainSection;
    }

}
