package insertdoi.paper.author;

public class Author {
    private String name = "";
    private String nameWithAffiliation = "";
    private String email = "";

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNameWithAffiliation() {
        return this.nameWithAffiliation;
    }

    public void setNameWithAffiliation(String nameWithAffiliation) {
        this.nameWithAffiliation = nameWithAffiliation;
    }

    public String getEmail() {
        return this.email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
    
    @Override
    public String toString() {
        return this.name;
    }

}
