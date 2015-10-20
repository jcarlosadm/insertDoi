package insertdoi.util;


public class PropertiesConfig {
    
    private static final String RESOURCES_FOLDER_NAME = "resources";
    private static final String OUTPUT_FOLDER_NAME = "outputs";
    private static final String PROPERTIES_FILE_NAME = "conf.properties";
    private static final String XML_FILE_NAME = "doiBatch.xml";
    private static final String TEX_FILE_NAME = "articles.tex";
    
    private static final String EXTENSION_XLSX_FILE = "xlsx";
    private static final String PROPERTY_ARTICLES_FOLDER_NAME = "articles_folder";
    private static final String PROPERTY_USER_SUFFIX = "user";
    private static final String PROPERTY_PASSWORD_SUFFIX = "password";
    private static final String PROPERTY_DEFAULT_DOI_STRING = "default_doi_string";
    private static final String PROPERTY_EVENT_NAME = "event";
    private static final String PROPERTY_SUBEVENT_NAME = "subevent";
    private static final String PROPERTY_PDF_SUFFIX_NAME = "pdf_suffix";
    private static final String PROPERTY_YEAR = "year";
    
    private static final String PROPERTY_XML_DOIBATCH_VERSION = 
            "xml.doibatch.version";
    private static final String PROPERTY_XML_DOIBATCH_XSI_SCHEMALOCATION = 
            "xml.doibatch.xsi_schemalocation";
    private static final String PROPERTY_XML_DOIBATCH_HEAD_DOIBATCH_ID = 
            "xml.doibatch.head.doi_batch_id";
    private static final String PROPERTY_XML_DOIBATCH_HEAD_TIMESTAMP = 
            "xml.doibatch.head.timestamp";
    private static final String PROPERTY_XML_DOIBATCH_HEAD_DEPOSITOR_NAME = 
            "xml.doibatch.head.depositor.name";
    private static final String PROPERTY_XML_DOIBATCH_HEAD_EMAIL_ADDRESS = 
            "xml.doibatch.head.email_address";
    private static final String PROPERTY_XML_DOIBATCH_BODY_JOURNAL_METADATA_FULL_TITLE = 
            "xml.doibatch.body.journal_metadata.full_title";
    private static final String PROPERTY_XML_DOIBATCH_BODY_JOURNAL_METADATA_ABBREV_TITLE = 
            "xml.doibatch.body.journal_metadata.abbrev_title";
    private static final String PROPERTY_XML_DOIBATCH_BODY_JOURNAL_METADATA_ISSN_ELECTRONIC = 
            "xml.doibatch.body.journal_metadata.issn_electronic";
    private static final String PROPERTY_XML_DOIBATCH_BODY_JOURNAL_METADATA_ISSN_PRINT = 
            "xml.doibatch.body.journal_metadata.issn_print";
    private static final String 
    PROPERTY_XML_DOIBATCH_BODY_JOURNAL_ISSUE_PUBLICATION_DATE_MEDIA_TYPE = 
            "xml.doibatch.body.journal_issue.publication_date.media_type";
    private static final String PROPERTY_XML_DOIBATCH_BODY_JOURNAL_ISSUE_PUBLICATION_DATE_DAY =
            "xml.doibatch.body.journal_issue.publication_date.day";
    private static final String PROPERTY_XML_DOIBATCH_BODY_JOURNAL_ISSUE_PUBLICATION_DATE_MONTH =
            "xml.doibatch.body.journal_issue.publication_date.month";
    private static final String PROPERTY_XML_DOIBATCH_BODY_JOURNAL_ISSUE_PUBLICATION_DATE_YEAR =
            "xml.doibatch.body.journal_issue.publication_date.year";
    private static final String PROPERTY_XML_DOIBATCH_BODY_JOURNAL_ISSUE_JOURNAL_VOLUME =
            "xml.doibatch.body.journal_issue.journal_volume";
    private static final String PROPERTY_XML_DOIBATCH_BODY_JOURNAL_ISSUE_ISSUE =
            "xml.doibatch.body.journal_issue.issue";
    private static final String PROPERTY_XML_DOIBATCH_BODY_JOURNAL_ARTICLE_TYPE =
            "xml.doibatch.body.journal_article.type";
    
    private static final String PROPERTY_XML_ISSUE_VOLUME = "xml.issue.volume";
    private static final String PROPERTY_XML_ISSUE_NUMBER = "xml.issue.number";
    private static final String PROPERTY_XML_ISSUE_YEAR = "xml.issue.year";
    private static final String PROPERTY_XML_ISSUE_MONTH = "xml.issue.month";
    private static final String PROPERTY_XML_ISSUE_DAY = "xml.issue.day";
    private static final String PROPERTY_XML_ISSUE_POLICY_PT_BR = "xml.issue.policy.pt_br";
    private static final String PROPERTY_XML_ISSUE_POLICY_EN_US = "xml.issue.policy.en_us";
    
    public static String getResourcesFolderName(){
        return RESOURCES_FOLDER_NAME;
    }
    
    public static String getPropertiesFileName(){
        return PROPERTIES_FILE_NAME;
    }
    
    public static String getOutputFolderName(){
        return OUTPUT_FOLDER_NAME;
    }
    
    public static String getExtensionXlsxFile() {
        return EXTENSION_XLSX_FILE;
    }

    public static String getPropertyArticlesFolderName(){
        return PROPERTY_ARTICLES_FOLDER_NAME;
    }
    
    private static String parseFilename(String filename){
        String filenameParsed = filename.replace(" ", "_");
        filenameParsed = filenameParsed.substring(0, filenameParsed.lastIndexOf('.'));
        return filenameParsed;
    }
    
    public static String getUserPropertyName(String filename){
        filename = parseFilename(filename);
        return (filename+"."+PROPERTY_USER_SUFFIX);
    }
    
    public static String getPasswordPropertyName(String filename){
        filename = parseFilename(filename);
        return (filename+"."+PROPERTY_PASSWORD_SUFFIX);
    }
    
    public static String getXmlFileName(){
        return XML_FILE_NAME;
    }

    public static String getPropertyDefaultDoiString() {
        return PROPERTY_DEFAULT_DOI_STRING;
    }

    public static String getPropertyEventName(String filename) {
        filename = parseFilename(filename);
        return filename+"."+PROPERTY_EVENT_NAME;
    }

    public static String getPropertySubeventName(String filename) {
        filename = parseFilename(filename);
        return filename+"."+PROPERTY_SUBEVENT_NAME;
    }
    
    public static String getPropertyPdfSuffixName(String filename){
        filename = parseFilename(filename);
        return filename+"."+PROPERTY_PDF_SUFFIX_NAME;
    }

    public static String getPropertyYear() {
        return PROPERTY_YEAR;
    }

    public static String getPropertyXmlDoibatchVersion() {
        return PROPERTY_XML_DOIBATCH_VERSION;
    }

    public static String getPropertyXmlDoiBatchXsiSchemalocation() {
        return PROPERTY_XML_DOIBATCH_XSI_SCHEMALOCATION;
    }

    public static String getPropertyXmlDoiBatchHeadDoibatchId() {
        return PROPERTY_XML_DOIBATCH_HEAD_DOIBATCH_ID;
    }

    public static String getPropertyXmlDoiBatchHeadTimestamp() {
        return PROPERTY_XML_DOIBATCH_HEAD_TIMESTAMP;
    }

    public static String getPropertyXmlDoiBatchHeadDepositorName() {
        return PROPERTY_XML_DOIBATCH_HEAD_DEPOSITOR_NAME;
    }

    public static String getPropertyXmlDoiBatchHeadEmailAddress() {
        return PROPERTY_XML_DOIBATCH_HEAD_EMAIL_ADDRESS;
    }

    public static String getPropertyXmlDoiBatchBodyJournalMetadataFullTitle() {
        return PROPERTY_XML_DOIBATCH_BODY_JOURNAL_METADATA_FULL_TITLE;
    }

    public static String getPropertyXmlDoiBatchBodyJournalMetadataAbbrevTitle() {
        return PROPERTY_XML_DOIBATCH_BODY_JOURNAL_METADATA_ABBREV_TITLE;
    }

    public static String getPropertyXmlDoiBatchBodyJournalMetadataIssnElectronic() {
        return PROPERTY_XML_DOIBATCH_BODY_JOURNAL_METADATA_ISSN_ELECTRONIC;
    }

    public static String getPropertyXmlDoiBatchBodyJournalMetadataIssnPrint() {
        return PROPERTY_XML_DOIBATCH_BODY_JOURNAL_METADATA_ISSN_PRINT;
    }

    public static String getPropertyXmlDoiBatchBodyJournalIssuePublicationDateMediaType() {
        return PROPERTY_XML_DOIBATCH_BODY_JOURNAL_ISSUE_PUBLICATION_DATE_MEDIA_TYPE;
    }

    public static String getPropertyXmlDoiBatchBodyJournalIssuePublicationDateDay() {
        return PROPERTY_XML_DOIBATCH_BODY_JOURNAL_ISSUE_PUBLICATION_DATE_DAY;
    }

    public static String getPropertyXmlDoiBatchBodyJournalIssuePublicationDateMonth() {
        return PROPERTY_XML_DOIBATCH_BODY_JOURNAL_ISSUE_PUBLICATION_DATE_MONTH;
    }

    public static String getPropertyXmlDoiBatchBodyJournalIssuePublicationDateYear() {
        return PROPERTY_XML_DOIBATCH_BODY_JOURNAL_ISSUE_PUBLICATION_DATE_YEAR;
    }

    public static String getPropertyXmlDoiBatchBodyJournalIssueJournalVolume() {
        return PROPERTY_XML_DOIBATCH_BODY_JOURNAL_ISSUE_JOURNAL_VOLUME;
    }

    public static String getPropertyXmlDoiBatchBodyJournalIssueIssue() {
        return PROPERTY_XML_DOIBATCH_BODY_JOURNAL_ISSUE_ISSUE;
    }

    public static String getPropertyXmlDoiBatchBodyJournalArticleType() {
        return PROPERTY_XML_DOIBATCH_BODY_JOURNAL_ARTICLE_TYPE;
    }

    public static String getTexFileName() {
        return TEX_FILE_NAME;
    }

    public static String getPropertyXmlIssueVolume() {
        return PROPERTY_XML_ISSUE_VOLUME;
    }

    public static String getPropertyXmlIssueNumber() {
        return PROPERTY_XML_ISSUE_NUMBER;
    }

    public static String getPropertyXmlIssueYear() {
        return PROPERTY_XML_ISSUE_YEAR;
    }

    public static String getPropertyXmlIssueMonth() {
        return PROPERTY_XML_ISSUE_MONTH;
    }

    public static String getPropertyXmlIssueDay() {
        return PROPERTY_XML_ISSUE_DAY;
    }

    public static String getPropertyXmlIssuePolicyPtBr() {
        return PROPERTY_XML_ISSUE_POLICY_PT_BR;
    }

    public static String getPropertyXmlIssuePolicyEnUs() {
        return PROPERTY_XML_ISSUE_POLICY_EN_US;
    }
}
