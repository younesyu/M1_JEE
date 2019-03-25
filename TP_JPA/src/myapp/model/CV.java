package myapp.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

@Entity
public class CV implements Serializable {
	// default serial UID
	private static final long serialVersionUID = 1L;

   @Id()
   private String immatriculation;
	
	@Column(length = 1500, nullable = false)
	private String body;
	
	
	@OneToOne(optional = true)
	@JoinColumn(name = "owner")
	private Person owner;

	public CV(String body, Person owner) {
		super();
		this.body = body;
		this.owner = owner;
	}

	public String getBody() {
		return body;
	}

	public void setBody(String body) {
		this.body = body;
	}

	public Person getOwner() {
		return owner;
	}

	public void setOwner(Person owner) {
		this.owner = owner;
	}
}
