package myapp.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;



@Entity
public class Car implements Serializable {

   // default serial UID
   private static final long serialVersionUID = 1L;

   // properties
   @Id()
   private String immatriculation;

   @Column(length = 150, nullable = false)
   private String model;

   @ManyToOne(optional = true)
   @JoinColumn(name = "owner")
   private Person owner;

   public Car() {
      super();
   }

   public Car(String immatriculation, String model) {
      super();
      this.immatriculation = immatriculation;
      this.model = model;
   }

   @Override
	public String toString() {
		return "Car [immatriculation=" + immatriculation + ", model=" + model + ", owner=" + owner + "]";
	}

   public String getImmatriculation() {
      return immatriculation;
   }

   public void setImmatriculation(String immatriculation) {
      this.immatriculation = immatriculation;
   }

   public String getModel() {
      return model;
   }

   public void setModel(String model) {
      this.model = model;
   }

   public Person getOwner() {
      return owner;
   }

   public void setOwner(Person owner) {
      this.owner = owner;
   }

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Car other = (Car) obj;
		if (immatriculation == null) {
			if (other.immatriculation != null)
				return false;
		} else if (!immatriculation.equals(other.immatriculation))
			return false;
		return true;
	}
	   

}