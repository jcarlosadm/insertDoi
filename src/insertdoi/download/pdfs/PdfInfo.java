package insertdoi.download.pdfs;

public class PdfInfo {

    private String name = "";

    private int firstPage = 0;
    private int numberOfPages = 0;

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getFirstPage() {
        return this.firstPage;
    }

    public void setFirstPage(int firstPage) {
        if (firstPage > 0) {
            this.firstPage = firstPage;
        }
    }

    public int getNumberOfPages() {
        return this.numberOfPages;
    }

    public void setNumberOfPages(int numberOfPages) {
        if (numberOfPages > 0) {
            this.numberOfPages = numberOfPages;
        }
    }

}
