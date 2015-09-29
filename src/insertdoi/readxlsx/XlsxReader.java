package insertdoi.readxlsx;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Iterator;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class XlsxReader {
    
    private String fileName = "";
    
    public XlsxReader(String fileName) {
        this.fileName = fileName;
    }
    
    public EventData getEventData(){
        EventData eventData = new EventData();
        
        File file = new File(this.fileName);
        XSSFWorkbook xssfWorkbook = null;
        try {
            FileInputStream fStream = new FileInputStream(file);
            xssfWorkbook = new XSSFWorkbook(fStream);
            XSSFSheet sheet = xssfWorkbook.getSheetAt(0);
            
            Iterator<Row> rowIterator = sheet.rowIterator();
            if (rowIterator.hasNext()) {
                rowIterator.next();
            }
            
            PaperData paper = null;
            while (rowIterator.hasNext()) {
                Row row = (Row) rowIterator.next();
                
                Iterator<Cell> cellIterator = row.cellIterator();
                
                while (cellIterator.hasNext()) {
                    Cell cell = (Cell) cellIterator.next();
                    
                    if (cell.getColumnIndex() == 1 && !cell.getStringCellValue().isEmpty()) {
                        this.addPaperToEventData(paper, eventData);
                        paper = new PaperData();
                        paper.setTitle(cell.getStringCellValue());
                    } else if(cell.getColumnIndex() == 2 && !cell.getStringCellValue().isEmpty()){
                        paper.addAuthor(cell.getStringCellValue().replace(",", ""));
                    } else if(cell.getColumnIndex() == 3 && cell.getHyperlink() != null){
                        paper.addUrl(cell.getHyperlink().getAddress());
                    }
                }
            }
            
            this.addPaperToEventData(paper, eventData);
            
            xssfWorkbook.close();
            
        } catch (IOException e) {
            // TODO: handle exception
        }
        
        return eventData;
    }

    private void addPaperToEventData(PaperData paper, EventData eventData) {
        if (paper == null) {
            return;
        }
        
        eventData.addPaper(paper);
    }
}
