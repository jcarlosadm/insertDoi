package insertdoi.xml.build.issues.section;

import insertdoi.event.sections.Section;
import insertdoi.paper.PaperData;
import insertdoi.util.PropertiesConfig;
import insertdoi.util.PropertiesGetter;
import insertdoi.xml.build.XmlElementsBuilder;
import insertdoi.xml.build.issues.articles.BuildArticle;

import java.util.Properties;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

public class BuildSection {

    private static final String LOCALE_NAME = "locale";
    private static final String LOCALE_VALUE_PT_BR = "pt_BR";

    public void build(Document doc, Element rootElement, Section section) {
        Element sectionElement = createSection(doc, rootElement);
        this.addElementsToSection(doc, section, sectionElement);
    }

    private Element createSection(Document doc, Element rootElement) {
        return XmlElementsBuilder.addElement("section", rootElement, doc);
    }

    private void addElementsToSection(Document doc, Section section,
            Element sectionElement) {
        this.addPreSectionElements(doc, section, sectionElement);
        this.addArticles(doc, section, sectionElement);
    }

    private void addPreSectionElements(Document doc, Section section,
            Element sectionElement) {
        Element tempElement = null;
        Properties prop = PropertiesGetter.getInstance();

        tempElement = XmlElementsBuilder.addElement("title", sectionElement,
                doc);
        XmlElementsBuilder.addAttributeToElement(tempElement, doc, LOCALE_NAME,
                LOCALE_VALUE_PT_BR);
        XmlElementsBuilder.addTextNodeToElement(tempElement, doc,
                section.getTitle());

        /*
         * tempElement = this.addElement("title", section, doc);
         * XmlElementsBuilder.addAttributeToElement(tempElement, doc,
         * LOCALE_NAME, LOCALE_VALUE_EN_US);
         * XmlElementsBuilder.addTextNodeToElement(tempElement, doc,
         * "Articles");
         */

        tempElement = XmlElementsBuilder.addElement("abbrev", sectionElement,
                doc);
        XmlElementsBuilder.addAttributeToElement(tempElement, doc, LOCALE_NAME,
                LOCALE_VALUE_PT_BR);
        XmlElementsBuilder.addTextNodeToElement(tempElement, doc,
                section.getAbbrev());

        /*
         * tempElement = this.addElement("abbrev", section, doc);
         * XmlElementsBuilder.addAttributeToElement(tempElement, doc,
         * LOCALE_NAME, LOCALE_VALUE_EN_US);
         * XmlElementsBuilder.addTextNodeToElement(tempElement, doc, "ART");
         */

        tempElement = XmlElementsBuilder.addElement("policy", sectionElement,
                doc);
        XmlElementsBuilder.addAttributeToElement(tempElement, doc, LOCALE_NAME,
                LOCALE_VALUE_PT_BR);
        XmlElementsBuilder.addTextNodeToElement(tempElement, doc, prop
                .getProperty(PropertiesConfig.getPropertyXmlIssuePolicyPtBr()));

        /*
         * tempElement = this.addElement("policy", section, doc);
         * XmlElementsBuilder.addAttributeToElement(tempElement, doc,
         * LOCALE_NAME, LOCALE_VALUE_EN_US);
         * XmlElementsBuilder.addTextNodeToElement(tempElement, doc,
         * prop.getProperty( PropertiesConfig.getPropertyXmlIssuePolicyEnUs()));
         */
    }

    private void addArticles(Document doc, Section section,
            Element sectionElement) {
        
        BuildArticle buildArticle = new BuildArticle();
        for (PaperData paper : section.getPapers()) {
            buildArticle.addArticle(paper, doc, sectionElement);
        }
    }
}
