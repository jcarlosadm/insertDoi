package insertdoi.texfile;

import insertdoi.pdfs.PdfMap;
import insertdoi.util.PropertiesConfig;
import insertdoi.util.windows.errorwindow.ErrorWindow;
import insertdoi.xml.doibatch.read.ReadXml;
import insertdoi.xml.doibatch.read.articles.ArticleInfo;
import insertdoi.xml.doibatch.read.articles.Contributor;

import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.io.output.FileWriterWithEncoding;

public class TexfileBuilder {
    
    public void run(PdfMap pdfMap) {
        File file = new File(PropertiesConfig.getOutputFolderName()+File.separator
                +PropertiesConfig.getTexFileName());
        
        BufferedWriter output = null;
        try {
            output = new BufferedWriter(new FileWriterWithEncoding(file, "ISO-8859-1"));
        } catch (IOException e) {
            ErrorWindow.run("Fail to create file articles.tex");
        }
        
        this.insertArticles(output, pdfMap);
        
        try {
            output.close();
        } catch (IOException e) {}
    }

    private void insertArticles(BufferedWriter output, PdfMap pdfMap) {
        ReadXml readXml = new ReadXml(PropertiesConfig.getOutputFolderName()+File.separator
                +PropertiesConfig.getXmlFileName());
        readXml.readAllArticles();
        List<ArticleInfo> articleInfoList = readXml.getArticleInfoList();
        writeHeader(output);
        
        for (ArticleInfo articleInfo : articleInfoList) {
            String fileName = getPdfFilename(articleInfo, pdfMap);
            writeArticle(articleInfo, fileName, output);
        }
    }
    
    private String getPdfFilename(ArticleInfo articleInfo, PdfMap pdfMap) {
        String title = articleInfo.getArticleTitlesList().get(0);
        String pdfName = pdfMap.getPdfInfo(title).getName();
        pdfName = pdfName.substring(0, pdfName.lastIndexOf("."));
        
        return pdfName;
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
