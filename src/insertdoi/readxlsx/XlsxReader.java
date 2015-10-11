package insertdoi.readxlsx;

import insertdoi.event.EventData;
import insertdoi.paper.PaperData;
import insertdoi.paper.author.Author;
import insertdoi.util.windows.errorwindow.ErrorWindow;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class XlsxReader {
    
    private static final String COLUMN_PAPER_ID_NAME = "Paper#";
    private static final String COLUMN_TITLE_NAME = "Title";
    private static final String COLUMN_TRACK_NAME = "Track";
    private static final String COLUMN_AUTHORS_NAME = "Authors";
    private static final String COLUMN_EMAIL_NAME = "Emails";
    private static final String COLUMN_AUTHORS_AFFILIATION_NAME = "Authors (Affiliation)";
    private static final String COLUMN_STATUS_NAME = "Status";
    private static final String COLUMN_EXTRA_FILES_NAME = "Extra files";
    private static final String COLUMN_ABSTRACT_NAME = "Abstract";
    
    private String fileName = "";
    
    private Map<Integer, String> columnMap = new HashMap<Integer, String>();
    
    public XlsxReader(String fileName) {
        this.fileName = fileName;
    }
    
    public EventData getEventData(){
        EventData eventData = new EventData();
        eventData.setXlsxFileName(this.fileName);
        
        File file = new File(this.fileName);
        XSSFWorkbook xssfWorkbook = null;
        
        try {
            FileInputStream fStream = new FileInputStream(file);
            xssfWorkbook = new XSSFWorkbook(fStream);
            XSSFSheet sheet = xssfWorkbook.getSheetAt(0);
            
            Iterator<Row> rowIterator = sheet.rowIterator();
            if (rowIterator.hasNext()) {
                Row row = (Row) rowIterator.next();
                this.fillColumnMap(row);
            }
            
            EventParameter eventParameter = new EventParameter();
            eventParameter.setEvent(eventData);
            
            readRows(rowIterator, eventParameter);
            
            xssfWorkbook.close();
            
        } catch (IOException e) {
            ErrorWindow.run("Error to read xlsx file");
        }
        
        return eventData;
    }

    private void fillColumnMap(Row row) {
        Iterator<Cell> cellIterator = row.cellIterator();
        while (cellIterator.hasNext()) {
            Cell cell = (Cell) cellIterator.next();
            if (!cell.getStringCellValue().isEmpty()) {
                this.columnMap.put(cell.getColumnIndex(), cell.getStringCellValue());
            }
        }
        
    }

    private void readRows(Iterator<Row> rowIterator, EventParameter eventParameter) {
        if (rowIterator.hasNext()) {
            Row row = (Row) rowIterator.next();
            
            this.addAuthorTempToPaper(eventParameter);
            
            Iterator<Cell> cellIterator = row.cellIterator();
            
            this.readCells(cellIterator, eventParameter);
            this.readRows(rowIterator, eventParameter);
        }
        
        // add last author
        this.addAuthorTempToPaper(eventParameter);
        
        // add last paper
        if (eventParameter.getPaper() != null) {
            eventParameter.getEvent().addPaper(eventParameter.getPaper());
            eventParameter.setPaper(null);
        }
    }

    private void addAuthorTempToPaper(EventParameter eventParameter) {
        if (eventParameter.getAuthorTemp() != null) {
            eventParameter.getPaper().addAuthor(eventParameter.getAuthorTemp());
            eventParameter.setAuthorTemp(null);
        }
    }

    private void readCells(Iterator<Cell> cellIterator, EventParameter eventParameter) {
        if (cellIterator.hasNext()) {
            Cell cell = (Cell) cellIterator.next();
            
            this.extractXlsxData(cell, eventParameter);
            this.readCells(cellIterator, eventParameter);
        }
    }

    private void extractXlsxData(Cell cell, EventParameter eventParameter) {
        
        if (cell == null || cell.getCellType() == Cell.CELL_TYPE_BLANK) {
            return;
        }
        
        String cellName = this.columnMap.get(cell.getColumnIndex());
        
        switch (cellName) {
        case COLUMN_PAPER_ID_NAME:
            if (eventParameter.getPaper() != null) {
                eventParameter.getEvent().addPaper(eventParameter.getPaper());
            }
            eventParameter.setPaper(new PaperData());
            break;
        case COLUMN_TITLE_NAME:
            if (!cell.getStringCellValue().isEmpty()) {
                eventParameter.getPaper().setTitle(cell.getStringCellValue());
            }
            break;
        case COLUMN_TRACK_NAME:
            if (!cell.getStringCellValue().isEmpty()) {
                eventParameter.getPaper().setTrack(cell.getStringCellValue());
            }
            break;
        case COLUMN_STATUS_NAME:
            if (!cell.getStringCellValue().isEmpty()) {
                eventParameter.getPaper().setStatus(cell.getStringCellValue());
            }
            break;
        case COLUMN_ABSTRACT_NAME:
            if (!cell.getStringCellValue().isEmpty()) {
                eventParameter.getPaper().setResume(cell.getStringCellValue());
            }
            break;
        case COLUMN_AUTHORS_NAME:
            if (!cell.getStringCellValue().isEmpty()) {
                this.setAuthorTemp(eventParameter);
                eventParameter.getAuthorTemp().setName(cell
                        .getStringCellValue().replace(",", ""));
            }
            break;
        case COLUMN_AUTHORS_AFFILIATION_NAME:
            if (!cell.getStringCellValue().isEmpty()) {
                this.setAuthorTemp(eventParameter);
                eventParameter.getAuthorTemp().setNameWithAffiliation(cell
                        .getStringCellValue().replace(",", ""));
            }
            break;
        case COLUMN_EMAIL_NAME:
            if (!cell.getStringCellValue().isEmpty()) {
                this.setAuthorTemp(eventParameter);
                eventParameter.getAuthorTemp().setEmail(cell
                        .getStringCellValue().replace(",", ""));
            }
            break;
        case COLUMN_EXTRA_FILES_NAME:
            if (cell.getHyperlink() != null) {
                eventParameter.getPaper().addUrl(cell.getHyperlink().getAddress());
            }
            break;
        }
    }

    private void setAuthorTemp(EventParameter eventParameter) {
        if (eventParameter.getAuthorTemp() == null) {
            eventParameter.setAuthorTemp(new Author());
        }
    }
}
