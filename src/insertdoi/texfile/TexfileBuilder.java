package insertdoi.texfile;

import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.io.output.FileWriterWithEncoding;

import insertdoi.event.EventData;
import insertdoi.paper.PaperData;
import insertdoi.util.PropertiesConfig;
import insertdoi.util.windows.errorwindow.ErrorWindow;
import insertdoi.xml.ReadXml;
import insertdoi.xml.articles.ArticleInfo;
import insertdoi.xml.articles.Contributor;

public class TexfileBuilder {
    
    private EventData eventData = null;
    
    public TexfileBuilder(EventData eventData) {
        this.eventData = eventData;
    }
    
    public void run() {
        File file = new File(PropertiesConfig.getOutputFolderName()
                +PropertiesConfig.getTexFileName());
        
        BufferedWriter output = null;
        try {
            output = new BufferedWriter(new FileWriterWithEncoding(file, "ISO-8859-1"));
        } catch (IOException e) {
            ErrorWindow.run("Fail to create file articles.tex");
        }
        
        this.insertArticles(output);
        
        try {
            output.close();
        } catch (IOException e) {}
    }

    private void insertArticles(BufferedWriter output) {
        ReadXml readXml = new ReadXml(PropertiesConfig.getOutputFolderName() 
                +PropertiesConfig.getXmlFileName());
        readXml.readAllArticles();
        List<ArticleInfo> articleInfoList = readXml.getArticleInfoList();
        writeHeader(output);
        
        for (ArticleInfo articleInfo : articleInfoList) {
            String fileName = getPdfFilename(articleInfo);
            writeArticle(articleInfo, fileName, output);
        }
    }
    
    private String getPdfFilename(ArticleInfo articleInfo) {
        String title1 = articleInfo.getArticleTitlesList().get(0);
        String title2 = "";
        for (PaperData paper : this.eventData.getPapers()) {
            title2 = paper.getTitle();
            if (title1.equals(title2)) {
                String filename = paper.getPdfInfo().getName();
                filename = filename.substring(0, filename.lastIndexOf('.'));
                return filename;
            }
        }
        
        return "";
    }

    private void writeHeader(BufferedWriter output) {
        try {
            output.write("    \\def \\doinumber{}"+"\n");
            output.write("    \\renewcommand{\\proccfoot}"+
                    "{ \\thepage \\linebreak doi: \\doinumber}"+"\n");
        } catch (IOException e) {
            ErrorWindow.run("Error to write in file");
        }
    }
    
    private void writeArticle(ArticleInfo articleInfo, String fileName,
            BufferedWriter output) {
        
        String doi = articleInfo.getDoi();
        String title = articleInfo.getArticleTitlesList().get(0);
        String authors = makeAuthors(articleInfo);
        String index = makeIndex(articleInfo);
        
        try {
            output.write("\n");
            output.write("    \\def \\doinumber{"+doi+"}"+"\n");
            output.write("    \\procpaper[%OK"+"\n");
            output.write("    title={"+title+"},%"+"\n");
            output.write("    author={"+authors+"},%"+"\n");
            output.write("    index={"+index+"}%"+"\n");
            output.write("    ]{"+fileName+"}"+"\n");
            
        } catch (IOException e) {
            ErrorWindow.run("Error to write in file");
        }
        
    }
    
    private String makeIndex(ArticleInfo articleInfo) {
        
        List<String> authorList = new ArrayList<String>();
        
        String givenName = "";
        String surname = "";
        for (Contributor contributor : articleInfo.getContributorsList()) {
            givenName = contributor.getGivenName();
            surname = contributor.getSurname();
            authorList.add("\\index{"+surname+", "+givenName+"}");
        }
        
        String index = "";
        for (String author : authorList) {
            index += author;
        }
        
        return index;
    }

    private String makeAuthors(ArticleInfo articleInfo) {
        
        List<String> authorList = new ArrayList<String>();
        String authors = "";
        
        String givenName = "";
        String surname = "";
        for (Contributor contributor : articleInfo.getContributorsList()) {
            givenName = contributor.getGivenName();
            surname = contributor.getSurname();
            authorList.add(givenName+" "+surname);
        }
        
        for (Iterator<String> iterator = authorList.iterator(); iterator.hasNext();) {
            String author = (String) iterator.next();
            authors += author;
            if(iterator.hasNext()){
                authors += ", ";
            }
        }
        
        return authors;
    }


}
