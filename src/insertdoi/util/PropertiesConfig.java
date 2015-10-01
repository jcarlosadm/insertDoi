package insertdoi.util;


public class PropertiesConfig {
    
    private static final String RESOURCES_FOLDER_NAME = "resources/";
    private static final String OUTPUT_FOLDER_NAME = "outputs/";
    private static final String PROPERTIES_FILE_NAME = "conf.properties";
    private static final String XML_FILE_NAME = "info.xml";
    private static final String TEX_FILE_NAME = "articles.tex";
    
    private static final String PROPERTY_XLSX_FILENAME = "xlsxfile";
    private static final String PROPERTY_ARTICLES_FOLDER_NAME = "articles_folder";
    private static final String PROPERTY_JEMS_USER = "jems_user";
    private static final String PROPERTY_JEMS_PASSWORD = "jems_password";
    private static final String PROPERTY_DEFAULT_DOI_STRING = "default_doi_string";
    private static final String PROPERTY_EVENT_NAME = "event";
    private static final String PROPERTY_SUBEVENT_NAME = "subevent";
    private static final String PROPERTY_YEAR = "year";
    
    private static final String PROPERTY_XML_DOIBATCH_VERSION = "xml.doibatch.version";
    private static final String PROPERTY_XML_XSI_SCHEMALOCATION = "xml.xsi_schemalocation";
    private static final String PROPERTY_XML_HEAD_DOIBATCH_ID = "xml.head.doi_batch_id";
    private static final String PROPERTY_XML_HEAD_TIMESTAMP = "xml.head.timestamp";
    private static final String PROPERTY_XML_HEAD_DEPOSITOR_NAME = "xml.head.depositor.name";
    private static final String PROPERTY_XML_HEAD_EMAIL_ADDRESS = "xml.head.email_address";
    private static final String PROPERTY_XML_BODY_JOURNAL_METADATA_FULL_TITLE = 
            "xml.body.journal_metadata.full_title";
    private static final String PROPERTY_XML_BODY_JOURNAL_METADATA_ABBREV_TITLE = 
            "xml.body.journal_metadata.abbrev_title";
    private static final String PROPERTY_XML_BODY_JOURNAL_METADATA_ISSN_ELECTRONIC = 
            "xml.body.journal_metadata.issn_electronic";
    private static final String PROPERTY_XML_BODY_JOURNAL_METADATA_ISSN_PRINT = 
            "xml.body.journal_metadata.issn_print";
    private static final String PROPERTY_XML_BODY_JOURNAL_ISSUE_PUBLICATION_DATE_MEDIA_TYPE = 
            "xml.body.journal_issue.publication_date.media_type";
    private static final String PROPERTY_XML_BODY_JOURNAL_ISSUE_PUBLICATION_DATE_DAY =
            "xml.body.journal_issue.publication_date.day";
    private static final String PROPERTY_XML_BODY_JOURNAL_ISSUE_PUBLICATION_DATE_MONTH =
            "xml.body.journal_issue.publication_date.month";
    private static final String PROPERTY_XML_BODY_JOURNAL_ISSUE_PUBLICATION_DATE_YEAR =
            "xml.body.journal_issue.publication_date.year";
    private static final String PROPERTY_XML_BODY_JOURNAL_ISSUE_JOURNAL_VOLUME =
            "xml.body.journal_issue.journal_volume";
    private static final String PROPERTY_XML_BODY_JOURNAL_ISSUE_ISSUE =
            "xml.body.journal_issue.issue";
    private static final String PROPERTY_XML_BODY_JOURNAL_ARTICLE_TYPE =
            "xml.body.journal_article.type";
    
    public static String getResourcesFolderName(){
        return RESOURCES_FOLDER_NAME;
    }
    
    public static String getPropertiesFileName(){
        return PROPERTIES_FILE_NAME;
    }
    
    public static String getPropertyXlsxFilename(){
        return PROPERTY_XLSX_FILENAME;
    }
    
    public static String getOutputFolderName(){
        return OUTPUT_FOLDER_NAME;
    }
    
    public static String getPropertyArticlesFolderName(){
        return PROPERTY_ARTICLES_FOLDER_NAME;
    }
    
    public static String getPropertyJemsUser(){
        return PROPERTY_JEMS_USER;
    }
    
    public static String getPropertyJemsPassword(){
        return PROPERTY_JEMS_PASSWORD;
    }
    
    public static String getXmlFileName(){
        return XML_FILE_NAME;
    }

    public static String getPropertyDefaultDoiString() {
        return PROPERTY_DEFAULT_DOI_STRING;
    }

    public static String getPropertyEventName() {
        return PROPERTY_EVENT_NAME;
    }

    public static String getPropertySubeventName() {
        return PROPERTY_SUBEVENT_NAME;
    }

    public static String getPropertyYear() {
        return PROPERTY_YEAR;
    }

    public static String getPropertyXmlDoibatchVersion() {
        return PROPERTY_XML_DOIBATCH_VERSION;
    }

    public static String getPropertyXmlXsiSchemalocation() {
        return PROPERTY_XML_XSI_SCHEMALOCATION;
    }

    public static String getPropertyXmlHeadDoibatchId() {
        return PROPERTY_XML_HEAD_DOIBATCH_ID;
    }

    public static String getPropertyXmlHeadTimestamp() {
        return PROPERTY_XML_HEAD_TIMESTAMP;
    }

    public static String getPropertyXmlHeadDepositorName() {
        return PROPERTY_XML_HEAD_DEPOSITOR_NAME;
    }

    public static String getPropertyXmlHeadEmailAddress() {
        return PROPERTY_XML_HEAD_EMAIL_ADDRESS;
    }

    public static String getPropertyXmlBodyJournalMetadataFullTitle() {
        return PROPERTY_XML_BODY_JOURNAL_METADATA_FULL_TITLE;
    }

    public static String getPropertyXmlBodyJournalMetadataAbbrevTitle() {
        return PROPERTY_XML_BODY_JOURNAL_METADATA_ABBREV_TITLE;
    }

    public static String getPropertyXmlBodyJournalMetadataIssnElectronic() {
        return PROPERTY_XML_BODY_JOURNAL_METADATA_ISSN_ELECTRONIC;
    }

    public static String getPropertyXmlBodyJournalMetadataIssnPrint() {
        return PROPERTY_XML_BODY_JOURNAL_METADATA_ISSN_PRINT;
    }

    public static String getPropertyXmlBodyJournalIssuePublicationDateMediaType() {
        return PROPERTY_XML_BODY_JOURNAL_ISSUE_PUBLICATION_DATE_MEDIA_TYPE;
    }

    public static String getPropertyXmlBodyJournalIssuePublicationDateDay() {
        return PROPERTY_XML_BODY_JOURNAL_ISSUE_PUBLICATION_DATE_DAY;
    }

    public static String getPropertyXmlBodyJournalIssuePublicationDateMonth() {
        return PROPERTY_XML_BODY_JOURNAL_ISSUE_PUBLICATION_DATE_MONTH;
    }

    public static String getPropertyXmlBodyJournalIssuePublicationDateYear() {
        return PROPERTY_XML_BODY_JOURNAL_ISSUE_PUBLICATION_DATE_YEAR;
    }

    public static String getPropertyXmlBodyJournalIssueJournalVolume() {
        return PROPERTY_XML_BODY_JOURNAL_ISSUE_JOURNAL_VOLUME;
    }

    public static String getPropertyXmlBodyJournalIssueIssue() {
        return PROPERTY_XML_BODY_JOURNAL_ISSUE_ISSUE;
    }

    public static String getPropertyXmlBodyJournalArticleType() {
        return PROPERTY_XML_BODY_JOURNAL_ARTICLE_TYPE;
    }

    public static String getTexFileName() {
        return TEX_FILE_NAME;
    }
}
