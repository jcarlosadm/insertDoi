package insertdoi.download.pdfs;

import insertdoi.readxlsx.EventData;

public class DownloadPdfs {
    
    private static final String DOWNLOAD_FOLDER = "artigos";
    
    private static final boolean EVEN_PAGES_ONLY = true;
    
    private EventData eventData = null;
    
    public DownloadPdfs(EventData eventData) {
        this.eventData = eventData;
    }
    
    public void run() {
        // TODO Auto-generated method stub
        
    }
    
    public String getFolderName(){
        return DOWNLOAD_FOLDER;
    }
}
