package insertdoi.xml;

import insertdoi.util.windows.errorwindow.ErrorWindow;
import insertdoi.xml.articles.ArticleInfo;
import insertdoi.xml.articles.Contributor;
import insertdoi.xml.articles.IssnJournal;

import java.io.File;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.GregorianCalendar;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class ReadXml {
    
    private Document xmlDoc = null;
    private List<ArticleInfo> articleInfoList = new ArrayList<ArticleInfo>();
    
    public ReadXml(String fileName) {
        File input = new File(fileName);
        
        try {
            DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
            this.xmlDoc = docBuilder.parse(input);
            this.xmlDoc.getDocumentElement().normalize();
            
        } catch (Exception e) {
            ErrorWindow.run("Error to read xml file");
        }
    }
    
    public boolean readAllArticles(){
        if(this.xmlDoc == null){
            return false;
        }
        
        Node body = this.xmlDoc.getElementsByTagName("body").item(0);
        NodeList articlesNodeList = body.getChildNodes();
        
        for (int articleIndex = 0; articleIndex < articlesNodeList.getLength();
                articleIndex++) {
            addArticleInfo(articlesNodeList.item(articleIndex));
        }
        
        return true;
    }
    
    private void addArticleInfo(Node articleNode) {
        
        ArticleInfo articleInfo = new ArticleInfo();
        
        getMetadata(articleNode, articleInfo);
        getIssue(articleNode, articleInfo);
        getArticle(articleNode, articleInfo);
        
        this.articleInfoList.add(articleInfo);
    }
    
    private void getMetadata(Node articleNode, ArticleInfo articleInfo) {
        Node metadataNode = ((Element) articleNode)
                .getElementsByTagName("journal_metadata").item(0);
        
        NodeList childNodes = metadataNode.getChildNodes();
        
        for (int nodeIndex = 0; nodeIndex < childNodes.getLength(); nodeIndex++) {
            Node node = childNodes.item(nodeIndex);
            Element element = null;
            if(node.getNodeType() == Node.ELEMENT_NODE){
                element = (Element) node;
            }
            String nodeName = node.getNodeName();
            if(nodeName.equals("full_title")){
                articleInfo.setJournalTitle(node.getTextContent());
            } else if(nodeName.equals("abbrev_title")){
                articleInfo.setJournalAbbrevTitle(node.getTextContent());
            } else if(nodeName.equals("issn")){
                IssnJournal issnJournal = new IssnJournal();
                issnJournal.setIssn(node.getTextContent());
                issnJournal.setMediaType(element.getAttribute("media_type"));
                articleInfo.addIssn(issnJournal);
            }
        }
    }

    private void getIssue(Node articleNode, ArticleInfo articleInfo) {
        Node issueNode = ((Element) articleNode)
                .getElementsByTagName("journal_issue").item(0);
        NodeList childNodes = issueNode.getChildNodes();
        
        for (int nodeIndex = 0; nodeIndex < childNodes.getLength(); nodeIndex++) {
            Node node = childNodes.item(nodeIndex);
            Element element = null;
            if (node.getNodeType() == Node.ELEMENT_NODE) {
                element = (Element) node;
            }
            String nodeName = node.getNodeName();
            
            if(nodeName.equals("publication_date")){
                extractPublicationDate(articleInfo, node);
            } else if (nodeName.equals("journal_volume")){
                Node volumeNode = element.getElementsByTagName("volume").item(0);
                int volume = Integer.parseInt(volumeNode.getTextContent());
                articleInfo.setVolume(volume);
            }
        }
    }

    private void extractPublicationDate(ArticleInfo articleInfo, Node node) {
        Calendar calendar = GregorianCalendar.getInstance();
        NodeList dateInfoList = node.getChildNodes();
        int day=0, month=0, year=0;
        for (int dateIndex = 0; dateIndex < dateInfoList.getLength();
                dateIndex++) {
            Node dateInfo = dateInfoList.item(dateIndex);
            String dateName = dateInfo.getNodeName();
            if(dateName.equals("month")){
                month = Integer.parseInt(dateInfo.getTextContent());
            } else if (dateName.equals("day")){
                day = Integer.parseInt(dateInfo.getTextContent());
            } else if (dateName.equals("year")){
                year = Integer.parseInt(dateInfo.getTextContent());
            }
        }
        calendar.set(year, month, day);
        articleInfo.setPublicationDate(calendar);
    }

    private void getArticle(Node articleNode, ArticleInfo articleInfo) {
        Node issueNode = ((Element) articleNode)
                .getElementsByTagName("journal_article").item(0);
        NodeList childNodes = issueNode.getChildNodes();
        
        for (int nodeIndex = 0; nodeIndex < childNodes.getLength();
                nodeIndex++) {
            Node node = childNodes.item(nodeIndex);
            Element element = null;
            if(node.getNodeType() == Node.ELEMENT_NODE){
                element = (Element) node;
            }
            String nodeName = node.getNodeName();
            
            if(nodeName.equals("titles")){
                NodeList titlesNodeList = node.getChildNodes();
                for (int titleIndex = 0; titleIndex < titlesNodeList.getLength();
                        titleIndex++) {
                    Node nodeTitle = titlesNodeList.item(titleIndex);
                    articleInfo.addArticleTitle(nodeTitle.getTextContent());
                }
            } else if(nodeName.equals("contributors")){
                extractContributors(articleInfo, node);
            } else if(nodeName.equals("pages")){
                Node pageNode = element.getElementsByTagName("first_page").item(0);
                int firstPage = Integer.parseInt(pageNode.getTextContent());
                articleInfo.setFirstPage(firstPage);
            } else if(nodeName.equals("doi_data")){
                NodeList doiChilds = node.getChildNodes();
                
                for (int doiIndex = 0; doiIndex < doiChilds.getLength(); doiIndex++) {
                    Node doiNode = (Element) doiChilds.item(doiIndex);
                    if(doiNode.getNodeName().equals("doi")){
                        articleInfo.setDoi(doiNode.getTextContent());
                    } else if(doiNode.getNodeName().equals("resource")){
                        articleInfo.setResource(doiNode.getTextContent());
                    }
                }
            }
        }
    }

    private void extractContributors(ArticleInfo articleInfo, Node node) {
        NodeList contributorsNodeList = node.getChildNodes();
        for (int contributorsIndex = 0; contributorsIndex < 
                contributorsNodeList.getLength(); contributorsIndex++) {
            Element contributorNode = (Element) contributorsNodeList
                    .item(contributorsIndex);
            String role = contributorNode.getAttribute("contributor_role");
            String sequence = contributorNode.getAttribute("sequence");
            String givenName = contributorNode.
                    getElementsByTagName("given_name").item(0).getTextContent();
            String surname = contributorNode
                    .getElementsByTagName("surname").item(0).getTextContent();
            
            Contributor contributor = new Contributor();
            
            contributor.setRole(role);
            contributor.setSequence(sequence);
            contributor.setGivenName(givenName);
            contributor.setSurname(surname);
            
            articleInfo.addContributor(contributor);
        }
    }
    
    public List<ArticleInfo> getArticleInfoList(){
        return Collections.unmodifiableList(this.articleInfoList);
    }
}
