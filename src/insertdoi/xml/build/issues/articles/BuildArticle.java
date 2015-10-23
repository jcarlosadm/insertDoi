package insertdoi.xml.build.issues.articles;

import insertdoi.paper.PaperData;
import insertdoi.paper.author.Author;
import insertdoi.util.PropertiesConfig;
import insertdoi.util.PropertiesGetter;
import insertdoi.xml.build.XmlElementsBuilder;
import insertdoi.xml.doibatch.build.EncodePdfFile;

import java.util.Locale;
import java.util.Properties;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

public class BuildArticle {
    
    private static final String LOCALE_NAME = "locale";
    private static final String LOCALE_VALUE_PT_BR = "pt_BR";
    private static final String LOCALE_VALUE_EN_US = "en_US";
    private static final String LOCALE_VALUE_SPAIN = "es_ES";

    private static final String LANGUAGE_NAME = "language";
    private static final String LANGUAGE_VALUE_PT = "pt";
    
    public void addArticle(PaperData paper, Document doc, Element section) {
        Element article = XmlElementsBuilder
                .addElement("article", section, doc);
        XmlElementsBuilder.addAttributeToElement(article, doc, LOCALE_NAME,
                LOCALE_VALUE_PT_BR);
        XmlElementsBuilder.addAttributeToElement(article, doc, LANGUAGE_NAME,
                LANGUAGE_VALUE_PT);

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

    private void addDoiElement(Element article, PaperData paper, Document doc) {
        Element doiElement = XmlElementsBuilder.addElement("id", article, doc);
        XmlElementsBuilder
                .addAttributeToElement(doiElement, doc, "type", "doi");
        XmlElementsBuilder.addTextNodeToElement(doiElement, doc,
                paper.getDoiString());
    }

    private void addTitle(Element article, PaperData paper, Document doc) {
        Element title = XmlElementsBuilder.addElement("title", article, doc);
        XmlElementsBuilder.addAttributeToElement(title, doc, LOCALE_NAME,
                LOCALE_VALUE_PT_BR);
        XmlElementsBuilder.addTextNodeToElement(title, doc, paper.getTitle());
    }

    private void addAbstract(Element article, PaperData paper, Document doc) {
        Element resume = XmlElementsBuilder
                .addElement("abstract", article, doc);
        XmlElementsBuilder.addAttributeToElement(resume, doc, LOCALE_NAME,
                LOCALE_VALUE_PT_BR);
        XmlElementsBuilder.addTextNodeToElement(resume, doc, paper.getResume());
    }

    private void addIndexing(Element article, PaperData paper, Document doc) {
        Element indexing = XmlElementsBuilder.addElement("indexing", article,
                doc);

        this.addDiscipline(indexing, paper, doc);
        this.addSubject(indexing, paper, doc);
    }

    private void addDiscipline(Element indexing, PaperData paper, Document doc) {
        Element discipline = XmlElementsBuilder.addElement("discipline",
                indexing, doc);
        XmlElementsBuilder.addAttributeToElement(discipline, doc, LOCALE_NAME,
                LOCALE_VALUE_PT_BR);
        // TODO ?
        XmlElementsBuilder.addTextNodeToElement(discipline, doc, "");
    }

    private void addSubject(Element indexing, PaperData paper, Document doc) {
        Element subject = XmlElementsBuilder.addElement("subject", indexing,
                doc);
        XmlElementsBuilder.addAttributeToElement(subject, doc, LOCALE_NAME,
                LOCALE_VALUE_PT_BR);
        // TODO ?
        XmlElementsBuilder.addTextNodeToElement(subject, doc, "");
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
        Element authorElement = XmlElementsBuilder.addElement("author",
                article, doc);
        if (primaryContact) {
            XmlElementsBuilder.addAttributeToElement(authorElement, doc,
                    "primary_contact", "true");
        }

        String[] nameParts = author.getName().split(" ");
        String middleName = this.buildMiddleName(nameParts);

        this.addContactNamePart(authorElement, "firstname", nameParts[0], doc);
        if (middleName != null) {
            this.addContactNamePart(authorElement, "middlename", middleName,
                    doc);
        }
        this.addContactNamePart(authorElement, "lastname",
                nameParts[nameParts.length - 1], doc);

        String affiliation = this.buildAffiliationName(author
                .getNameWithAffiliation());
        String affiliationLocale = this.extractAffiliationLocale(author
                .getNameWithAffiliation());
        this.addContactAffiliation(authorElement, affiliation,
                affiliationLocale, doc);

        this.addContactCountry(authorElement, affiliationLocale, doc);
        this.addContactEmail(authorElement, author.getEmail(), doc);

        // TODO add biography?
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

    private void addContactNamePart(Element authorElement, String elementName,
            String namePart, Document doc) {
        Element element = XmlElementsBuilder.addElement(elementName,
                authorElement, doc);
        XmlElementsBuilder.addTextNodeToElement(element, doc, namePart);
    }

    private String buildAffiliationName(String nameWithAffiliation) {
        int firstIndex = nameWithAffiliation.indexOf("(") + 1;
        int secondIndex = nameWithAffiliation.lastIndexOf(" -");

        return nameWithAffiliation.substring(firstIndex, secondIndex);
    }

    private String extractAffiliationLocale(String nameWithAffiliation) {
        int firstIndex = nameWithAffiliation.lastIndexOf("-") + 2;
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

    private void addContactAffiliation(Element authorElement,
            String affiliation, String affiliationLocale, Document doc) {
        Element affiliationElement = XmlElementsBuilder.addElement(
                "affiliation", authorElement, doc);
        XmlElementsBuilder.addAttributeToElement(affiliationElement, doc,
                LOCALE_NAME, affiliationLocale);
        XmlElementsBuilder.addTextNodeToElement(affiliationElement, doc,
                affiliation);
    }

    private void addContactCountry(Element authorElement,
            String affiliationLocale, Document doc) {
        String country = affiliationLocale.substring(affiliationLocale
                .lastIndexOf("_") + 1);
        Element countryElement = XmlElementsBuilder.addElement("country",
                authorElement, doc);
        XmlElementsBuilder.addTextNodeToElement(countryElement, doc, country);
    }

    private void addContactEmail(Element authorElement, String email,
            Document doc) {
        Element emailElement = XmlElementsBuilder.addElement("email",
                authorElement, doc);
        XmlElementsBuilder.addTextNodeToElement(emailElement, doc, email);
    }

    private void addPages(Element article, int firstPage, Document doc) {
        Element pages = XmlElementsBuilder.addElement("pages", article, doc);
        XmlElementsBuilder.addTextNodeToElement(pages, doc, firstPage + "");
    }

    private void addPublishiedDate(Element article, Document doc) {
        Properties prop = PropertiesGetter.getInstance();

        String date = prop.getProperty(PropertiesConfig
                .getPropertyXmlIssueYear());
        date += "-"
                + prop.getProperty(PropertiesConfig.getPropertyXmlIssueMonth());
        date += "-"
                + prop.getProperty(PropertiesConfig.getPropertyXmlIssueDay());

        Element publishied = XmlElementsBuilder.addElement("date_published",
                article, doc);
        XmlElementsBuilder.addTextNodeToElement(publishied, doc, date);
    }

    private void addPdf(Element article, PaperData paper, Document doc) {
        Element galley = XmlElementsBuilder.addElement("galley", article, doc);
        XmlElementsBuilder.addAttributeToElement(galley, doc, LOCALE_NAME,
                LOCALE_VALUE_PT_BR);

        Element label = XmlElementsBuilder.addElement("label", galley, doc);
        XmlElementsBuilder.addTextNodeToElement(label, doc, "PDF");

        Element file = XmlElementsBuilder.addElement("file", galley, doc);
        Element embed = XmlElementsBuilder.addElement("embed", file, doc);
        XmlElementsBuilder.addAttributeToElement(embed, doc, "filename", paper
                .getPdfInfo().getName());
        XmlElementsBuilder.addAttributeToElement(embed, doc, "encoding",
                "base64");
        XmlElementsBuilder.addAttributeToElement(embed, doc, "mime_type",
                "application/pdf");
        XmlElementsBuilder.addTextNodeToElement(embed, doc,
                EncodePdfFile.getFileInBase64(paper.getPdfInfo().getName()));
    }
    
}
