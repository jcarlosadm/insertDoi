package insertdoi.texfile;

import insertdoi.event.sections.Section;
import insertdoi.paper.PaperData;
import insertdoi.paper.author.Author;
import insertdoi.util.PropertiesConfig;
import insertdoi.util.windows.errorwindow.ErrorWindow;

import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.io.output.FileWriterWithEncoding;

public class TexfileBuilder {
    
    private List<Section> sections = new ArrayList<Section>();
    
    public void addSection(Section section){
        if (!this.sections.contains(section)) {
            this.sections.add(section);
        }
    }
    
    public void run() {
        File file = new File(PropertiesConfig.getOutputFolderName()+File.separator
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
        writeHeader(output);
        
        for (Section section : this.sections) {
            this.writeSection(section, output);
        }
    }
    
    private void writeSection(Section section, BufferedWriter output) {
        try {
            output.write("\n    \\session{"+section.getTitle()+"}\n");
        } catch (IOException e) {
            ErrorWindow.run("Error to write in file");
        }
        
        for (PaperData paper : section.getPapers()) {
            this.writeArticle(paper, output);
        }
    }

    /*private String getPdfFilename(PaperData paper, BufferedWriter output) {
        String title = articleInfo.getArticleTitlesList().get(0);
        String pdfName = pdfMap.getPdfInfo(title).getName();
        pdfName = pdfName.substring(0, pdfName.lastIndexOf("."));
        
        return pdfName;
    }*/

    private void writeHeader(BufferedWriter output) {
        try {
            /*output.write("    \\def \\doinumber{}"+"\n");
            output.write("    \\renewcommand{\\proccfoot}"+
                    "{ \\thepage \\linebreak doi: \\doinumber}"+"\n");*/
            output.write("    \\renewcommand{\\proccfoot}{}\n");
            output.write("    \\renewcommand{\\proclhead}{}\n");
        } catch (IOException e) {
            ErrorWindow.run("Error to write in file");
        }
    }
    
    private void writeArticle(PaperData paper, BufferedWriter output) {
        
        //String doi = paper.getDoiString();
        String title = paper.getTitle();
        title = title.replace("&", "\\&");
        String authors = makeAuthors(paper);
        String index = makeIndex(paper);
        String filename = paper.getPdfInfo().getName();
        filename = filename.substring(0, filename.lastIndexOf('.'));
        
        try {
            output.write("\n");
            //output.write("    \\def \\doinumber{"+doi+"}"+"\n");
            output.write("    \\procpaper[%OK"+"\n");
            output.write("    title={"+title+"},%"+"\n");
            output.write("    author={"+authors+"},%"+"\n");
            output.write("    index={"+index+"}%"+"\n");
            output.write("    ]{"+filename+"}"+"\n");
            
        } catch (IOException e) {
            ErrorWindow.run("Error to write in file");
        }
        
    }
    
    private String makeIndex(PaperData paper) {
        
        List<String> authorList = new ArrayList<String>();
        
        String name = "";
        String givenName = "";
        String surname = "";
        for (Author author : paper.getAuthors()) {
            name = author.getName();
            givenName = name.substring(0, name.lastIndexOf(' '));
            surname = name.substring(name.lastIndexOf(' ')+1);
            authorList.add("\\index{"+surname+", "+givenName+"}");
        }
        
        String index = "";
        for (String author : authorList) {
            index += author;
        }
        
        return index;
    }

    private String makeAuthors(PaperData paper) {
        
        String authors = "";
        
        for (Iterator<Author> iterator = paper.getAuthors().iterator(); iterator.hasNext();) {
            Author author = (Author) iterator.next();
            authors += author.getName();
            
            if(iterator.hasNext()){
                authors += ", ";
            }
        }
        
        return authors;
    }


}
