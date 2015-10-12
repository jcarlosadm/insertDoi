package insertdoi.xml.doibatch.read.articles;

public class Contributor {
    private String role;
    private String sequence;
    private String givenName;
    private String surname;
    
    public String getRole() {
        return this.role;
    }
    public void setRole(String role) {
        this.role = role;
    }
    public String getSequence() {
        return this.sequence;
    }
    public void setSequence(String sequence) {
        this.sequence = sequence;
    }
    public String getGivenName() {
        return this.givenName;
    }
    public void setGivenName(String givenName) {
        this.givenName = givenName;
    }
    public String getSurname() {
        return this.surname;
    }
    public void setSurname(String surname) {
        this.surname = surname;
    }
}
