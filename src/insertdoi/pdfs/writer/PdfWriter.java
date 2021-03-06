package insertdoi.pdfs.writer;

import insertdoi.event.EventData;
import insertdoi.event.sections.Section;
import insertdoi.paper.PaperData;
import insertdoi.util.PropertiesConfig;
import insertdoi.util.PropertiesGetter;
import insertdoi.util.windows.errorwindow.ErrorWindow;

import java.io.File;
import java.util.Properties;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDFont;
import org.apache.pdfbox.pdmodel.font.PDType1Font;

public class PdfWriter {

    private static final float LIMIT_LEFT = 80f;
    private static final float LIMIT_DOWN = 30f;
    private EventData eventData = null;

    public PdfWriter(EventData eventData) {
        this.eventData = eventData;
    }

    public void run() {
        for (Section section : this.eventData.getSections()) {
            for (PaperData paper : section.getPapers()) {
                if (!paper.getPdfInfo().isWritten()) {
                    this.insertInformations(paper);
                }
            }
        }
    }

    private void insertInformations(PaperData paper) {

        String foldername = PropertiesGetter.getInstance().getProperty(
                PropertiesConfig.getPropertyArticlesFolderName());
        String path = PropertiesConfig.getOutputFolderName() + File.separator
                + foldername + File.separator + paper.getPdfInfo().getName();

        PDFont font = PDType1Font.TIMES_ROMAN;
        float fontSize = 12.0f;

        try {
            PDDocument pdfFile = PDDocument.load(new File(path));

            for (int index = 0; index < pdfFile.getNumberOfPages(); index++) {
                if (index == 0) {
                    this.insertInfoInFirstPage(paper, pdfFile,
                            pdfFile.getPage(index), font, fontSize);
                } else {
                    this.insertInfoOtherPages(paper, pdfFile,
                            pdfFile.getPage(index), font, fontSize, index);
                }
            }

            pdfFile.setAllSecurityToBeRemoved(true);
            pdfFile.save(new File(path));
            pdfFile.close();
            paper.getPdfInfo().setWritten();
        } catch (Exception e) {
            ErrorWindow.run("Error to read pdf file");
        }
    }

    private void insertInfoOtherPages(PaperData paper, PDDocument pdfFile,
            PDPage page, PDFont font, float fontSize, int index) {
        
        try {
            PDPageContentStream contentStream = new PDPageContentStream(
                    pdfFile, page, true, true, true);
            
            this.insertPage(contentStream, paper.getPdfInfo().getFirstPage() + index,
                    font, fontSize);
            
            float top = page.getMediaBox().getHeight();
            
            this.insertHeader(contentStream, font, fontSize, top);

            contentStream.close();
        } catch (Exception e) {
            e.printStackTrace();
            ErrorWindow.run("failed to write information in pdf");
        }

    }

    private void insertInfoInFirstPage(PaperData paper, PDDocument pdfFile,
            PDPage page, PDFont font, float fontSize) {

        try {
            PDPageContentStream contentStream = new PDPageContentStream(
                    pdfFile, page, true, true, true);

            if (paper.getDoiString() != null && paper.getDoiString() != "") {
                this.insertDoi(contentStream, paper.getDoiString(), font, fontSize);
            }

            this.insertPage(contentStream, paper.getPdfInfo().getFirstPage(), font, fontSize);
            
            float top = page.getMediaBox().getHeight();
            
            this.insertHeader(contentStream, font, fontSize, top);

            contentStream.close();
        } catch (Exception e) {
            ErrorWindow.run("failed to write information in pdf");
        }
    }

    private void insertHeader(PDPageContentStream contentStream, PDFont font,
            float fontSize, float top) throws Exception {
        Properties prop = PropertiesGetter.getInstance();
        
        contentStream.beginText();
        contentStream.setFont(font, fontSize);
        
        contentStream.newLineAtOffset(LIMIT_LEFT, top - 40f);
        contentStream.showText(prop.getProperty(PropertiesConfig
                .getPropertyPdfHeaderSecondlineName()));
        
        contentStream.newLineAtOffset(0f,15f);
        contentStream.showText(prop.getProperty(PropertiesConfig
                .getPropertyPdfHeaderFirstlineName()));
        
        contentStream.endText();
    }

    private void insertPage(PDPageContentStream contentStream, int page,
            PDFont font, float fontSize) throws Exception {

        contentStream.beginText();
        contentStream.setFont(font, fontSize);
        
        contentStream.newLineAtOffset(500f, LIMIT_DOWN);
        contentStream.showText(page + "");
        
        contentStream.endText();
    }

    private void insertDoi(PDPageContentStream contentStream, String doiString,
            PDFont font, float fontSize) throws Exception {

        contentStream.beginText();
        contentStream.setFont(font, fontSize);
        
        contentStream.newLineAtOffset(LIMIT_LEFT, LIMIT_DOWN);
        contentStream.showText("DOI: "+doiString);
        
        contentStream.endText();
    }

}
