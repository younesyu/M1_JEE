package myapp.model;

import java.io.Serializable;

import javax.persistence.NamedQuery;

@NamedQuery(
        name="getAllFirstNames",
        query="SELECT new myapp.model.FirstName(p.id,p.firstName)\n" + 
        		"FROM Person p"
)
public class FirstName implements Serializable {

    private static final long serialVersionUID = 1L;

    private long id;

    private String firstName;

    public FirstName() {
        super();
    }

    public FirstName(long id, String firstName) {
        super();
        this.id = id;
        this.firstName = firstName;
    }

    @Override
    public String toString() {
        return "FirstName [id=" + id + ", firstName=" + firstName + "]";
    }

    public long getId() {
        return id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

}