package myapp.model;

import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.PostUpdate;
import javax.persistence.PreUpdate;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;
import javax.persistence.UniqueConstraint;
import javax.persistence.Version;

@Entity(name = "Person")
@Table(name = "TPerson",
   uniqueConstraints = {
      @UniqueConstraint(columnNames = {
         "first_name", "birth_day"
      })
   })

@NamedQuery(
        name="findPersonsByFirstName",
        query="SELECT p FROM Person p WHERE p.firstName LIKE :pattern"
)
//TODO ça
@NamedQuery(
        name="findPersonsByCarModel",
        query="SELECT p FROM Person p WHERE p.firstName LIKE :pattern"
)
public class Person implements Serializable {

   private static final long serialVersionUID = 1L;

   @Id()
   @GeneratedValue(strategy = GenerationType.AUTO)
   private long id;

   @Basic(optional = false)
   @Column(name = "first_name", length = 200,
      nullable = false)
   private String firstName;

   @Basic()
   @Temporal(TemporalType.DATE)
   @Column(name = "birth_day")
   private Date birthDay;
   
   @Embedded
   private Address address;
   
   @OneToMany(cascade = { CascadeType.MERGE, CascadeType.PERSIST },
		      fetch = FetchType.LAZY, mappedBy = "owner")
   private Set<Car> cars;

   @Version()
   private long version = 0;

   @Transient
   public static long updateCounter = 0;

   public Person() {
      super();
   }

   public Person(String firstName, Date birthDay) {
      super();
      this.firstName = firstName;
      this.birthDay = birthDay;
   }

   @PreUpdate
   public void beforeUpdate() {
      System.err.println("PreUpdate of " + this);
   }

   @PostUpdate
   public void afterUpdate() {
      System.err.println("PostUpdate of " + this);
      updateCounter++;
   }

   @Override
   public String toString() {
      return "Person(id=" + getId() + "," + firstName + "," + birthDay + ","
            + ",v" + getVersion() + ")";
   }

   public long getId() {
      return id;
   }

   public void setId(long id) {
      this.id = id;
   }

   public String getFirstName() {
      return firstName;
   }

   public void setFirstName(String firstName) {
      this.firstName = firstName;
   }

   public Date getBirthDay() {
      return birthDay;
   }

   public void setBirthDay(Date birthDay) {
      this.birthDay = birthDay;
   }
   
   public Address getAddress() {
      return address;
   }

   public void setAddress(Address address) {
      this.address = address;
   }
   
   public Set<Car> getCars() {
      return cars;
   }

   public void setCars(Set<Car> cars) {
      this.cars = cars;
   }

   public void addCar(Car c) {
      if (cars == null) {
         cars = new HashSet<>();
      }
      cars.add(c);
      c.setOwner(this);
   }


   public long getVersion() {
      return version;
   }

   public void setVersion(long version) {
      this.version = version;
   }

}