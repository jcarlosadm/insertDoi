package insertdoi.xml.build.issues;

import insertdoi.event.sections.Section;
import insertdoi.paper.PaperData;
import insertdoi.util.PropertiesConfig;
import insertdoi.util.PropertiesGetter;
import insertdoi.util.windows.errorwindow.ErrorWindow;
import insertdoi.xml.build.BuildXml;
import insertdoi.xml.build.XmlElementsBuilder;
import insertdoi.xml.build.issues.articles.BuildXmlArticles;
import insertdoi.xml.build.issues.section.BuildSection;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

public class BuildXmlIssues extends BuildXml {

    private static final String ROOT_ELEMENT_NAME = "issue";

    private static final String LOCALE_NAME = "locale";
    private static final String LOCALE_VALUE_PT_BR = "pt_BR";

    private List<Section> sections = new ArrayList<Section>();
    
    private String sourceFilename = "";

    public void addSection(Section section) {
        if (!this.sections.contains(section)) {
            this.sections.add(section);
        }
    }
    
    public void setSourceFilename(String filename){
        this.sourceFilename = filename;
    }

    @Override
    protected void setRootProperties(Element rootElement, Document doc) {
        String attributeName = "published";
        String attributeValue = "true";
        XmlElementsBuilder.addAttributeToElement(rootElement, doc,
                attributeName, attributeValue);

        attributeName = "identification";
        attributeValue = "title";
        XmlElementsBuilder.addAttributeToElement(rootElement, doc,
                attributeName, attributeValue);

        attributeName = "current";
        attributeValue = "true";
        XmlElementsBuilder.addAttributeToElement(rootElement, doc,
                attributeName, attributeValue);
    }

    @Override
    protected void addElementsToRoot(Document doc, Element rootElement) {
        Element tempElement = null;

        Properties prop = PropertiesGetter.getInstance();

        tempElement = XmlElementsBuilder.addElement("title", rootElement, doc);
        XmlElementsBuilder.addAttributeToElement(tempElement, doc, LOCALE_NAME,
                LOCALE_VALUE_PT_BR);
        XmlElementsBuilder.addTextNodeToElement(tempElement, doc,
                prop.getProperty(PropertiesConfig.getPropertyTitleName()));

        tempElement = XmlElementsBuilder.addElement("description", rootElement,
                doc);
        XmlElementsBuilder.addAttributeToElement(tempElement, doc, LOCALE_NAME,
                LOCALE_VALUE_PT_BR);
        XmlElementsBuilder
                .addTextNodeToElement(tempElement, doc, prop
                        .getProperty(PropertiesConfig
                                .getPropertyDescriptionName()));

        tempElement = XmlElementsBuilder.addElement("volume", rootElement, doc);
        XmlElementsBuilder.addTextNodeToElement(tempElement, doc,
                prop.getProperty(PropertiesConfig.getPropertyXmlIssueVolume()));
        tempElement = XmlElementsBuilder.addElement("number", rootElement, doc);
        XmlElementsBuilder.addTextNodeToElement(tempElement, doc,
                prop.getProperty(PropertiesConfig.getPropertyXmlIssueNumber()));
        tempElement = XmlElementsBuilder.addElement("year", rootElement, doc);
        XmlElementsBuilder.addTextNodeToElement(tempElement, doc,
                prop.getProperty(PropertiesConfig.getPropertyXmlIssueYear()));
        XmlElementsBuilder.addElement("open_access", rootElement, doc);

        BuildSection buildSection = new BuildSection();
        for (Section section : sections) {
            buildSection.build(doc, rootElement, section);
        }
    }

    @Override
    protected String getRootElementName() {
        return ROOT_ELEMENT_NAME;
    }

    @Override
    protected void prebuildAlgorithms(String xmlFinalFilename) {
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

        List<Section> mainSectionList = new ArrayList<Section>();
        
        for (Section section : sections) {
            int numberOfArticles = section.getPapers().size();
            int numberOfArticlesByFile = (int) Math
                    .ceil(((double) numberOfArticles) / division);
            
            Section mainSection = null;
            try {
                mainSection = (Section) section.clone();
                mainSection.clearPapers();
                mainSectionList.add(mainSection);
            } catch (CloneNotSupportedException e) {
                e.printStackTrace();
                ErrorWindow.run("Error to divide xml file");
            }
            
            int auxSectionCount = 0;
            List<Section> auxSections = new ArrayList<Section>();
            Section auxCurrentSection = new Section();
            auxSections.add(auxCurrentSection);
            
            for (int index = 0; index < section.getPapers().size(); index++) {
                PaperData paper = section.getPapers().get(index);
                
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
        }
        
        this.sections = mainSectionList;
    }
}
