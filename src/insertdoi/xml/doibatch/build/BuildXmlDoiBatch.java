package insertdoi.xml.doibatch.build;

import insertdoi.paper.PaperData;
import insertdoi.paper.author.Author;
import insertdoi.util.PropertiesConfig;
import insertdoi.util.PropertiesGetter;
import insertdoi.util.windows.errorwindow.ErrorWindow;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
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
import org.w3c.dom.Node;

public class BuildXmlDoiBatch {

    private static final String RESOURCE_ELEMENT_NAME = "resource";
    private static final String DOI_ELEMENT_NAME = "doi";
    private static final String DOI_DATA_ELEMENT_NAME = "doi_data";
    private static final String OTHER_PAGES_ELEMENT_NAME = "other_pages";
    private static final String FIRST_PAGE_ELEMENT_NAME = "first_page";
    private static final String PAGES_ELEMENT_NAME = "pages";
    private static final String SURNAME_ELEMENT_NAME = "surname";
    private static final String GIVEN_NAME_ELEMENT_NAME = "given_name";
    private static final String FIRST_VALUE_FOR_SEQUENCE_ATTRIBUTE = "first";
    private static final String ADDITIONAL_VALUE_FOR_SEQUENCE_ATTRIBUTE = "additional";
    private static final String SEQUENCE_ATTRIBUTE_NAME = "sequence";
    private static final String AUTHOR_VALUE_FOR_CONTRIBUTOR_ROLE_ATTRIBUTE = "author";
    private static final String CONTRIBUTOR_ROLE_ATTRIBUTE_NAME = "contributor_role";
    private static final String PERSON_NAME_ELEMENT_NAME = "person_name";
    private static final String CONTRIBUTORS_ELEMENT_NAME = "contributors";
    private static final String TITLE_ELEMENT_NAME = "title";
    private static final String TITLES_ELEMENT_NAME = "titles";
    private static final String JOURNAL_ARTICLE_ELEMENT_NAME = "journal_article";
    private static final String VOLUME_ELEMENT_NAME = "volume";
    private static final String JOURNAL_VOLUME_ELEMENT_NAME = "journal_volume";
    private static final String JOURNAL_ISSUE_ELEMENT_NAME = "journal_issue";
    private static final String PRINT_VALUE_FOR_MEDIA_TYPE_ATTRIBUTE = "print";
    private static final String ELECTRONIC_VALUE_FOR_MEDIA_TYPE_ATTRIBUTE = "electronic";
    private static final String MEDIA_TYPE_ATTRINUTE_NAME_FOR_ISSN_ELEMENT = "media_type";
    private static final String ISSN_ELEMENT_NAME = "issn";
    private static final String JOURNAL_METADATA_ELEMENT_NAME = "journal_metadata";
    private static final String JOURNAL_ELEMENT_NAME = "journal";
    private static final String HEAD_REGISTRANT_ELEMENT_NAME = "registrant";
    private static final String HEAD_DEPOSITOR_ELEMENT_NAME = "depositor";
    private static final String XSI_SCHEMALOCATION_ATTRIBUTE_NAME = "xsi:schemaLocation";
    private static final String BODY_ELEMENT_NAME = "body";
    private static final String HEAD_ELEMENT_NAME = "head";
    private static final String ROOT_ELEMENT_NAME = "doi_batch";
    
    private List<PaperData> papers = new ArrayList<PaperData>();
    
    public void run() {
        DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
        
        try {
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.newDocument();
            
            Element rootElement = doc.createElement(ROOT_ELEMENT_NAME);
            this.setRootProperties(rootElement, doc);
            doc.appendChild(rootElement);
            
            Element head = doc.createElement(HEAD_ELEMENT_NAME);
            this.setHeadElements(head, doc);
            rootElement.appendChild(head);
            
            Element body = doc.createElement(BODY_ELEMENT_NAME);
            this.setBodyElements(body, doc);
            rootElement.appendChild(body);
            
            this.saveXmlFile(doc);
            
        } catch (ParserConfigurationException e) {
            ErrorWindow.run("Error to create xml file");
        }
    }

    private void saveXmlFile(Document doc) {
        TransformerFactory transFactory = TransformerFactory.newInstance();
        try {
            Transformer transformer = transFactory.newTransformer();
            DOMSource source = new DOMSource(doc);
            StreamResult result = new StreamResult(new File(PropertiesConfig.getOutputFolderName()
                    +File.separator+PropertiesConfig.getXmlFileName()));
            transformer.transform(source, result);
        } catch (TransformerConfigurationException e) {
            ErrorWindow.run("Error to configure xml to save");
        } catch (TransformerException e) {
            ErrorWindow.run("Error to save xml");
        }
    }

    private void setBodyElements(Element body, Document doc) {
        for (PaperData paper : this.papers) {
            Element journal= this.createJournalElement(paper, doc);
            body.appendChild(journal);
        }
    }
    
    public void addPaper(PaperData paper){
        this.papers.add(paper);
    }

    private Element createJournalElement(PaperData paper, Document doc) {
        Element journal = doc.createElement(JOURNAL_ELEMENT_NAME);
        
        journal.appendChild(this.createMetadata(doc));
        journal.appendChild(this.createIssue(doc));
        journal.appendChild(this.createArticle(paper, doc));
        
        return journal;
    }

    private Element createMetadata(Document doc) {
        Properties prop = PropertiesGetter.getInstance();
        Element metadata = doc.createElement(JOURNAL_METADATA_ELEMENT_NAME);
        
        this.appendNewElement(metadata, doc, prop, 
                PropertiesConfig.getPropertyXmlDoiBatchBodyJournalMetadataFullTitle(), true);
        this.appendNewElement(metadata, doc, prop, 
                PropertiesConfig.getPropertyXmlDoiBatchBodyJournalMetadataAbbrevTitle(), true);
        
        Element issnElectronic = doc.createElement(ISSN_ELEMENT_NAME);
        issnElectronic.setAttribute(MEDIA_TYPE_ATTRINUTE_NAME_FOR_ISSN_ELEMENT,
                ELECTRONIC_VALUE_FOR_MEDIA_TYPE_ATTRIBUTE);
        issnElectronic.appendChild(doc.createTextNode(prop.getProperty(PropertiesConfig
                .getPropertyXmlDoiBatchBodyJournalMetadataIssnElectronic())));
        metadata.appendChild(issnElectronic);
        
        Element issnPrint = doc.createElement(ISSN_ELEMENT_NAME);
        issnPrint.setAttribute(MEDIA_TYPE_ATTRINUTE_NAME_FOR_ISSN_ELEMENT, 
                PRINT_VALUE_FOR_MEDIA_TYPE_ATTRIBUTE);
        issnPrint.appendChild(doc.createTextNode(prop.getProperty(PropertiesConfig
                .getPropertyXmlDoiBatchBodyJournalMetadataIssnPrint())));
        metadata.appendChild(issnPrint);
        
        return metadata;
    }

    private Element createIssue(Document doc) {
        Element issue_group = doc.createElement(JOURNAL_ISSUE_ELEMENT_NAME);
        Properties prop = PropertiesGetter.getInstance();
        
        issue_group.appendChild(this.createPublicationDate(doc));
        issue_group.appendChild(this.createJournalVolume(doc));
        this.appendNewElement(issue_group, doc, prop, 
                PropertiesConfig.getPropertyXmlDoiBatchBodyJournalIssueIssue(), true);
        
        return issue_group;
    }

    private Element createPublicationDate(Document doc) {
        Element publication_date = doc.createElement("publication_date");
        Properties prop = PropertiesGetter.getInstance();
        
        publication_date.setAttribute(MEDIA_TYPE_ATTRINUTE_NAME_FOR_ISSN_ELEMENT, prop
                .getProperty(PropertiesConfig
                        .getPropertyXmlDoiBatchBodyJournalIssuePublicationDateMediaType()));
        this.appendNewElement(publication_date, doc, prop, 
                PropertiesConfig.getPropertyXmlDoiBatchBodyJournalIssuePublicationDateMonth(), true);
        this.appendNewElement(publication_date, doc, prop, 
                PropertiesConfig.getPropertyXmlDoiBatchBodyJournalIssuePublicationDateDay(), true);
        this.appendNewElement(publication_date, doc, prop, 
                PropertiesConfig.getPropertyXmlDoiBatchBodyJournalIssuePublicationDateYear(), true);
        
        return publication_date;
    }

    private Element createJournalVolume(Document doc) {
        Element journal_volume = doc.createElement(JOURNAL_VOLUME_ELEMENT_NAME);
        Properties prop = PropertiesGetter.getInstance();
        
        Element volume = doc.createElement(VOLUME_ELEMENT_NAME);
        volume.appendChild(doc.createTextNode(prop.getProperty(
                PropertiesConfig.getPropertyXmlDoiBatchBodyJournalIssueJournalVolume())));
        journal_volume.appendChild(volume);
        
        return journal_volume;
    }

    private Element createArticle(PaperData paper, Document doc) {
        Element journal_article = doc.createElement(JOURNAL_ARTICLE_ELEMENT_NAME);
        
        journal_article.appendChild(this.createTitlesElement(paper, doc));
        journal_article.appendChild(this.createContributorsElement(paper, doc));
        journal_article.appendChild(this.createPublicationDate(doc));
        journal_article.appendChild(this.createPagesElement(paper, doc));
        journal_article.appendChild(this.createDoiDataElement(paper, doc));
        
        return journal_article;
    }

    private Node createTitlesElement(PaperData paper, Document doc) {
        Element titles = doc.createElement(TITLES_ELEMENT_NAME);
        Element title = doc.createElement(TITLE_ELEMENT_NAME);
        
        title.appendChild(doc.createTextNode(paper.getTitle()));
        titles.appendChild(title);
        
        return titles;
    }

    private Node createContributorsElement(PaperData paper, Document doc) {
        Element contributors = doc.createElement(CONTRIBUTORS_ELEMENT_NAME);
        
        int sequence = 1;
        for (Author author : paper.getAuthors()) {
            contributors.appendChild(this.addPersonElement(author.getName(), sequence, doc));
            sequence++;
        }
        
        return contributors;
    }

    private Element addPersonElement(String author, int sequence, Document doc) {
        Element personName = doc.createElement(PERSON_NAME_ELEMENT_NAME);
        
        personName.setAttribute(CONTRIBUTOR_ROLE_ATTRIBUTE_NAME, 
                AUTHOR_VALUE_FOR_CONTRIBUTOR_ROLE_ATTRIBUTE);
        
        String sequenceValue = ADDITIONAL_VALUE_FOR_SEQUENCE_ATTRIBUTE;
        if (sequence == 1) {
            sequenceValue = FIRST_VALUE_FOR_SEQUENCE_ATTRIBUTE;
        }
        personName.setAttribute(SEQUENCE_ATTRIBUTE_NAME, sequenceValue);
        
        Element given_name = doc.createElement(GIVEN_NAME_ELEMENT_NAME);
        Element surname = doc.createElement(SURNAME_ELEMENT_NAME);
        surname.appendChild(doc.createTextNode(author.substring(author.lastIndexOf(' ')+1)));
        given_name.appendChild(doc.createTextNode(author.substring(0, 
                author.lastIndexOf(' '))));
        
        personName.appendChild(given_name);
        personName.appendChild(surname);
        
        return personName;
    }

    private Node createPagesElement(PaperData paper, Document doc) {
        Element pages = doc.createElement(PAGES_ELEMENT_NAME);
        
        int firstPageNumber = paper.getPdfInfo().getFirstPage();
        int lastPageNumber = firstPageNumber+paper.getPdfInfo().getNumberOfPages()-1;
        Element first_page = doc.createElement(FIRST_PAGE_ELEMENT_NAME);
        first_page.appendChild(doc.createTextNode(firstPageNumber+""));
        Element other_pages = doc.createElement(OTHER_PAGES_ELEMENT_NAME);
        other_pages.appendChild(doc.createTextNode(lastPageNumber+""));
        
        pages.appendChild(first_page);
        pages.appendChild(other_pages);
        
        return pages;
    }

    private Node createDoiDataElement(PaperData paper, Document doc) {
        Element doi_data = doc.createElement(DOI_DATA_ELEMENT_NAME);
        
        Element doi = doc.createElement(DOI_ELEMENT_NAME);
        doi.appendChild(doc.createTextNode(paper.getDoiString()));
        Element resource = doc.createElement(RESOURCE_ELEMENT_NAME);
        resource.appendChild(doc.createTextNode(""));
        
        doi_data.appendChild(doi);
        doi_data.appendChild(resource);
        
        return doi_data;
    }

    private void setHeadElements(Element head, Document doc) {
        Properties prop = PropertiesGetter.getInstance();
        Element element = null;
        
        element = this.appendNewElement(head, doc, prop, 
                PropertiesConfig.getPropertyXmlDoiBatchHeadDoibatchId(), true);
        
        element = this.appendNewElement(head, doc, prop, 
                PropertiesConfig.getPropertyXmlDoiBatchHeadTimestamp(), true);
        
        element = doc.createElement(HEAD_DEPOSITOR_ELEMENT_NAME);
        head.appendChild(element);
        
        Element depositor = element;
        element = this.appendNewElement(depositor, doc, prop, 
                PropertiesConfig.getPropertyXmlDoiBatchHeadDepositorName(), true);
        element = this.appendNewElement(depositor, doc, prop, 
                PropertiesConfig.getPropertyXmlDoiBatchHeadEmailAddress(), true);
        
        element = doc.createElement(HEAD_REGISTRANT_ELEMENT_NAME);
        head.appendChild(element);
    }

    private Element appendNewElement(Element head, Document doc, Properties prop, 
            String propertyName, boolean appendTextNode) {
        
        String elementName = propertyName.substring(propertyName.lastIndexOf('.')+1);
        Element element = doc.createElement(elementName);
        if (appendTextNode == true) {
            element.appendChild(doc.createTextNode(prop.getProperty(propertyName)));
        }
        head.appendChild(element);
        
        return element;
    }
    
    private void setRootProperties(Element rootElement, Document doc) {
        Properties prop = PropertiesGetter.getInstance();
        
        String attributeName = PropertiesConfig.getPropertyXmlDoibatchVersion().substring(
                PropertiesConfig.getPropertyXmlDoibatchVersion().lastIndexOf('.')+1);
        String attributeValue =prop.getProperty(PropertiesConfig.getPropertyXmlDoibatchVersion());
        this.addAttributeToElement(rootElement, doc, attributeName, attributeValue);
        
        attributeName = XSI_SCHEMALOCATION_ATTRIBUTE_NAME;
        attributeValue = prop.getProperty(PropertiesConfig.getPropertyXmlDoiBatchXsiSchemalocation());
        this.addAttributeNsToElement(rootElement, doc, attributeName, attributeValue);
    }

    private void addAttributeToElement(Element element, Document doc,
            String attributeName, String attributeValue) {
        
        Attr attr = doc.createAttribute(attributeName);
        attr.setValue(attributeValue);
        element.setAttributeNode(attr);
    }
    
    private void addAttributeNsToElement(Element element, Document doc,
            String attributeName, String attributeValue){
        Attr attr = doc.createAttributeNS("xsi", attributeName);
        attr.setValue(attributeValue);
        element.setAttributeNodeNS(attr);
    }
}
