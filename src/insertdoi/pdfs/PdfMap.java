package insertdoi.pdfs;

import java.util.HashMap;
import java.util.Map;

public class PdfMap {
    private Map<String, PdfInfo> pdfMap = new HashMap<String, PdfInfo>();
    
    public void addPdf(String title, PdfInfo pdfInfo){
        this.pdfMap.put(title, pdfInfo);
    }
    
    public PdfInfo getPdfInfo(String title){
        return this.pdfMap.get(title);
    }
}
