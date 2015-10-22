package insertdoi.xml.issues.build;

import insertdoi.event.sections.Section;
import insertdoi.paper.PaperData;
import insertdoi.paper.author.Author;
import insertdoi.util.PropertiesConfig;
import insertdoi.util.PropertiesGetter;
import insertdoi.util.windows.errorwindow.ErrorWindow;
import insertdoi.xml.doibatch.build.EncodePdfFile;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Properties;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

public class BuildXmlIssues {

    private static final String ROOT_ELEMENT_NAME = "issue";
    
    private static final String LOCALE_NAME = "locale";
    private static final String LOCALE_VALUE_PT_BR = "pt_BR";
    private static final String LOCALE_VALUE_EN_US = "en_US";
    private static final String LOCALE_VALUE_SPAIN = "es_ES";
    
    private static final String LANGUAGE_NAME = "language";
    private static final String LANGUAGE_VALUE_PT = "pt";
    
    private List<Section> sections = new ArrayList<Section>();
    
    public void addSection(Section section){
        if (!this.sections.contains(section)) {
            this.sections.add(section);
        }
    }
    
    public void run(){
        DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
        
        try {
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.newDocument();
            
            Element rootElement = doc.createElement(ROOT_ELEMENT_NAME);
            this.setRootProperties(rootElement, doc);
            doc.appendChild(rootElement);
            
            this.addElementsToRoot(doc, rootElement);
            
            for (Section section : sections) {
                Element sectionElement = createSection(doc, rootElement);
                this.addElementsToSection(doc, section, sectionElement);
            }
            
            this.saveXmlFile(doc);
            
        } catch (ParserConfigurationException e) {
            ErrorWindow.run("Error to create xml file");
        }
    }

    private Element createSection(Document doc, Element rootElement) {
        return this.addElement("section", rootElement, doc);
    }

    private void addElementsToSection(Document doc, Section section, Element sectionElement) {
        this.addPreSectionElements(doc, section, sectionElement);
        this.addArticles(doc, section, sectionElement);
    }

    private void addPreSectionElements(Document doc, Section section, Element sectionElement) {
        Element tempElement = null;
        Properties prop = PropertiesGetter.getInstance();
        
        tempElement = this.addElement("title", sectionElement, doc);
        this.addAttributeToElement(tempElement, doc, LOCALE_NAME, LOCALE_VALUE_PT_BR);
        this.addTextNodeToElement(tempElement, doc, section.getTitle());
        
        /*tempElement = this.addElement("title", section, doc);
        this.addAttributeToElement(tempElement, doc, LOCALE_NAME, LOCALE_VALUE_EN_US);
        this.addTextNodeToElement(tempElement, doc, "Articles");*/
        
        tempElement = this.addElement("abbrev", sectionElement, doc);
        this.addAttributeToElement(tempElement, doc, LOCALE_NAME, LOCALE_VALUE_PT_BR);
        this.addTextNodeToElement(tempElement, doc, section.getAbbrev());
        
        /*tempElement = this.addElement("abbrev", section, doc);
        this.addAttributeToElement(tempElement, doc, LOCALE_NAME, LOCALE_VALUE_EN_US);
        this.addTextNodeToElement(tempElement, doc, "ART");*/
        
        tempElement = this.addElement("policy", sectionElement, doc);
        this.addAttributeToElement(tempElement, doc, LOCALE_NAME, LOCALE_VALUE_PT_BR);
        this.addTextNodeToElement(tempElement, doc, prop.getProperty(
                PropertiesConfig.getPropertyXmlIssuePolicyPtBr()));
        
        /*tempElement = this.addElement("policy", section, doc);
        this.addAttributeToElement(tempElement, doc, LOCALE_NAME, LOCALE_VALUE_EN_US);
        this.addTextNodeToElement(tempElement, doc, prop.getProperty(
                PropertiesConfig.getPropertyXmlIssuePolicyEnUs()));*/
    }

    private void addArticles(Document doc, Section section, Element sectionElement) {
        for (PaperData paper : section.getPapers()) {
            this.addArticle(paper, doc, sectionElement);
        }
    }

    private void addArticle(PaperData paper, Document doc, Element section) {
        Element article = this.addElement("article", section, doc);
        this.addAttributeToElement(article, doc, LOCALE_NAME, LOCALE_VALUE_PT_BR);
        this.addAttributeToElement(article, doc, LANGUAGE_NAME, LANGUAGE_VALUE_PT);
        
        if (paper.getDoiString() != null && paper.getDoiString() != "") {
            this.addDoiElement(article, paper, doc);
        }
        this.addTitle(article, paper, doc);
        this.addAbstract(article, paper, doc);
        this.addIndexing(article, paper, doc);
        this.addAuthors(article, paper, doc);
        this.addPages(article, paper.getPdfInfo().getFirstPage(), doc);
        this.addPublishiedDate(article, doc);
        this.addPdf(article, paper, doc);
    }

    private void addPdf(Element article, PaperData paper, Document doc) {
        Element galley = this.addElement("galley", article, doc);
        this.addAttributeToElement(galley, doc, LOCALE_NAME, LOCALE_VALUE_PT_BR);
        
        Element label = this.addElement("label", galley, doc);
        this.addTextNodeToElement(label, doc, "PDF");
        
        Element file = this.addElement("file", galley, doc);
        Element embed = this.addElement("embed", file, doc);
        this.addAttributeToElement(embed, doc, "filename", paper.getPdfInfo().getName());
        this.addAttributeToElement(embed, doc, "encoding", "base64");
        this.addAttributeToElement(embed, doc, "mime_type", "application/pdf");
        this.addTextNodeToElement(embed, doc, EncodePdfFile.getFileInBase64(paper
                .getPdfInfo().getName()));
    }

    private void addPublishiedDate(Element article, Document doc) {
        Properties prop = PropertiesGetter.getInstance();
        
        String date = prop.getProperty(PropertiesConfig.getPropertyXmlIssueYear());
        date += "-"+prop.getProperty(PropertiesConfig.getPropertyXmlIssueMonth());
        date += "-"+prop.getProperty(PropertiesConfig.getPropertyXmlIssueDay());
        
        Element publishied = this.addElement("date_published", article, doc);
        this.addTextNodeToElement(publishied, doc, date);
    }

    private void addPages(Element article, int firstPage, Document doc) {
        Element pages = this.addElement("pages", article, doc);
        this.addTextNodeToElement(pages, doc, firstPage+"");
    }

    private void addAuthors(Element article, PaperData paper, Document doc) {
        boolean primaryContact = true;
        for (Author author : paper.getAuthors()) {
            this.addAuthor(article, author, doc, primaryContact);
            primaryContact = false;
        }
    }

    private void addAuthor(Element article, Author author, Document doc,
            boolean primaryContact) {
        Element authorElement = this.addElement("author", article, doc);
        if (primaryContact) {
            this.addAttributeToElement(authorElement, doc, "primary_contact", "true");
        }
        
        String[] nameParts = author.getName().split(" ");
        String middleName = this.buildMiddleName(nameParts);
        
        this.addContactNamePart(authorElement, "firstname", nameParts[0], doc);
        if (middleName != null) {
            this.addContactNamePart(authorElement, "middlename", middleName, doc);
        }
        this.addContactNamePart(authorElement, "lastname", nameParts[nameParts.length-1], doc);
        
        String affiliation = this.buildAffiliationName(author.getNameWithAffiliation());
        String affiliationLocale = this.extractAffiliationLocale(author
                .getNameWithAffiliation());
        this.addContactAffiliation(authorElement, affiliation, affiliationLocale, doc);
        
        this.addContactCountry(authorElement, affiliationLocale, doc);
        this.addContactEmail(authorElement, author.getEmail(), doc);
        
        // TODO add biography?
    }

    private void addContactEmail(Element authorElement, String email,
            Document doc) {
        Element emailElement = this.addElement("email", authorElement, doc);
        this.addTextNodeToElement(emailElement, doc, email);
    }

    private void addContactCountry(Element authorElement,
            String affiliationLocale, Document doc) {
        String country = affiliationLocale.substring(affiliationLocale.lastIndexOf("_")+1);
        Element countryElement = this.addElement("country", authorElement, doc);
        this.addTextNodeToElement(countryElement, doc, country);
    }

    private String extractAffiliationLocale(String nameWithAffiliation) {
        int firstIndex = nameWithAffiliation.lastIndexOf("-")+2;
        int secondIndex = nameWithAffiliation.lastIndexOf(")");
        
        try {
            for (Locale locale : Locale.getAvailableLocales()) {
                
                if (locale.getDisplayCountry() == nameWithAffiliation
                        .substring(firstIndex, secondIndex)) {
                    return locale.toString();
                }
            }
            
            switch (nameWithAffiliation.substring(firstIndex, secondIndex)) {
            case "Brazil":
                return LOCALE_VALUE_PT_BR;
            case "US":
                return LOCALE_VALUE_EN_US;
            case "Spain":
                return LOCALE_VALUE_SPAIN;
            }
        } catch (Exception e) {
            
        }
        
        return LOCALE_VALUE_PT_BR;
    }

    private void addContactAffiliation(Element authorElement,String affiliation, 
            String affiliationLocale, Document doc) {
        Element affiliationElement = this.addElement("affiliation", authorElement, doc);
        this.addAttributeToElement(affiliationElement, doc, LOCALE_NAME, affiliationLocale);
        this.addTextNodeToElement(affiliationElement, doc, affiliation);
    }

    private String buildAffiliationName(String nameWithAffiliation) {
        int firstIndex = nameWithAffiliation.indexOf("(")+1;
        int secondIndex = nameWithAffiliation.lastIndexOf(" -");
        
        return nameWithAffiliation.substring(firstIndex, secondIndex);
    }

    private void addContactNamePart(Element authorElement, String elementName, String namePart,
            Document doc) {
        Element element = this.addElement(elementName, authorElement, doc);
        this.addTextNodeToElement(element, doc, namePart);
    }

    private String buildMiddleName(String[] nameParts) {
        if (nameParts.length <= 2) {
            return null;
        }
        String middleName = "";
        
        for (int i = 1; i < nameParts.length - 1; i++) {
            middleName += nameParts[i];
            if (i < nameParts.length - 2) {
                middleName += " ";
            }
        }
        
        return middleName;
    }

    private void addIndexing(Element article, PaperData paper, Document doc) {
        Element indexing = this.addElement("indexing", article, doc);
        
        this.addDiscipline(indexing, paper, doc);
        this.addSubject(indexing, paper, doc);
    }

    private void addDiscipline(Element indexing, PaperData paper, Document doc) {
        Element discipline = this.addElement("discipline", indexing, doc);
        this.addAttributeToElement(discipline, doc, LOCALE_NAME, LOCALE_VALUE_PT_BR);
        // TODO ?
        this.addTextNodeToElement(discipline, doc, "");
    }

    private void addSubject(Element indexing, PaperData paper, Document doc) {
        Element subject = this.addElement("subject", indexing, doc);
        this.addAttributeToElement(subject, doc, LOCALE_NAME, LOCALE_VALUE_PT_BR);
        // TODO ?
        this.addTextNodeToElement(subject, doc, "");
    }

    private void addAbstract(Element article, PaperData paper, Document doc) {
        Element resume = this.addElement("abstract", article, doc);
        this.addAttributeToElement(resume, doc, LOCALE_NAME, LOCALE_VALUE_PT_BR);
        this.addTextNodeToElement(resume, doc, paper.getResume());
    }

    private void addTitle(Element article, PaperData paper, Document doc) {
        Element title = this.addElement("title", article, doc);
        this.addAttributeToElement(title, doc, LOCALE_NAME, LOCALE_VALUE_PT_BR);
        this.addTextNodeToElement(title, doc, paper.getTitle());
    }

    private void addDoiElement(Element article, PaperData paper, Document doc) {
        Element doiElement = this.addElement("id", article, doc);
        this.addAttributeToElement(doiElement, doc, "type", "doi");
        this.addTextNodeToElement(doiElement, doc, paper.getDoiString());
    }

    private void addElementsToRoot(Document doc, Element rootElement) {
        Element tempElement = null;
        
        Properties prop = PropertiesGetter.getInstance();
        
        tempElement = this.addElement("title", rootElement, doc);
        this.addAttributeToElement(tempElement, doc, LOCALE_NAME, LOCALE_VALUE_PT_BR);
        this.addTextNodeToElement(tempElement, doc, prop.getProperty(
                PropertiesConfig.getPropertyTitleName()));
        
        tempElement = this.addElement("description", rootElement, doc);
        this.addAttributeToElement(tempElement, doc, LOCALE_NAME, LOCALE_VALUE_PT_BR);
        this.addTextNodeToElement(tempElement, doc, prop.getProperty(
                PropertiesConfig.getPropertyDescriptionName()));
        
        tempElement = this.addElement("volume", rootElement, doc);
        this.addTextNodeToElement(tempElement, doc, prop.getProperty(
                PropertiesConfig.getPropertyXmlIssueVolume()));
        tempElement = this.addElement("number", rootElement, doc);
        this.addTextNodeToElement(tempElement, doc, prop.getProperty(
                PropertiesConfig.getPropertyXmlIssueNumber()));
        tempElement = this.addElement("year", rootElement, doc);
        this.addTextNodeToElement(tempElement, doc,prop.getProperty(
                PropertiesConfig.getPropertyXmlIssueYear()));
        this.addElement("open_access", rootElement, doc);
    }
    
    private Element addElement(String elementName, Element parent, Document doc){
        Element element = doc.createElement(elementName);
        parent.appendChild(element);
        
        return element;
    }
    
    private void addTextNodeToElement(Element element, Document doc, String textNodeData){
        if (textNodeData != null) {
            element.appendChild(doc.createTextNode(textNodeData));
        }
    }
    
    private void saveXmlFile(Document doc) {
        Properties prop = PropertiesGetter.getInstance();
        
        TransformerFactory transFactory = TransformerFactory.newInstance();
        try {
            Transformer transformer = transFactory.newTransformer();
            DOMSource source = new DOMSource(doc);
            StreamResult result = new StreamResult(new File(PropertiesConfig.getOutputFolderName()
                    +File.separator+prop.getProperty(PropertiesConfig
                            .getPropertyTitleName())+".xml"));
            transformer.transform(source, result);
        } catch (TransformerConfigurationException e) {
            ErrorWindow.run("Error to configure xml to save");
        } catch (TransformerException e) {
            ErrorWindow.run("Error to save xml");
        }
    }
    
    private void setRootProperties(Element rootElement, Document doc) {
        // Properties prop = PropertiesGetter.getInstance();
        
        String attributeName = "published";
        String attributeValue = "true";
        this.addAttributeToElement(rootElement, doc, attributeName, attributeValue);
        
        attributeName = "identification";
        attributeValue = "title";
        this.addAttributeToElement(rootElement, doc, attributeName, attributeValue);
        
        attributeName = "current";
        attributeValue = "true";
        this.addAttributeToElement(rootElement, doc, attributeName, attributeValue);
    }
    
    private void addAttributeToElement(Element element, Document doc,
            String attributeName, String attributeValue) {
        
        Attr attr = doc.createAttribute(attributeName);
        attr.setValue(attributeValue);
        element.setAttributeNode(attr);
    }
}
