package insertdoi.readxlsx;

import insertdoi.event.EventData;
import insertdoi.paper.PaperData;
import insertdoi.paper.author.Author;

public class EventParameter {
    private EventData event = null;
    private PaperData paper = null;
    private Author authorTemp = null;

    public EventData getEvent() {
        return this.event;
    }

    public void setEvent(EventData event) {
        this.event = event;
    }

    public PaperData getPaper() {
        return this.paper;
    }

    public void setPaper(PaperData paper) {
        this.paper = paper;
    }

    public Author getAuthorTemp() {
        return this.authorTemp;
    }

    public void setAuthorTemp(Author authorTemp) {
        this.authorTemp = authorTemp;
    }
}
