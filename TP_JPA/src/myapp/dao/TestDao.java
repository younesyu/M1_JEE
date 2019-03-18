package myapp.dao;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.util.Date;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import myapp.model.Address;
import myapp.model.Car;
import myapp.model.Movie;
import myapp.model.Person;

public class TestDao {

   static Dao dao;
   static Person john;
   static Person jane;
   static Person jay;
   static Person joan;
   static Person adam;
   static Person harry;
   static Person hermione;
   static Car car1;
   static Car car2;
   static Car car3;

   @BeforeClass
   public static void beforeAll() {
	  
      dao = new Dao();
      dao.init();
      
      john = new Person();
      john.setFirstName("John");
      john.setBirthDay(new Date());
      john.setAddress(new Address("Sesame Street", "NYC", "USA"));
      
      jane = new Person();
      jane.setFirstName("Jane");
      jane.setBirthDay(new Date());
      jane.setAddress(new Address("Oxford Street", "London", "UK"));
      
      car1 = new Car("AZERTY", "307");
      car2 = new Car("UIOPQS", "Cooper");
      car3 = new Car("DFGHJK", "Clio");
      
      dao.addPerson(john);
      dao.addPerson(jane);
   }

   @AfterClass
   public static void afterAll() {
//	  dao.removePerson(john.getId());
//	  dao.removePerson(jane.getId());
//	  dao.removePerson(jay.getId());
//	  dao.removePerson(adam.getId());
//	  
      dao.close();
   }

   @Before
   public void setUp() {
      // pour plus tard
   }

   @After
   public void tearDown() {
      // pour plus tard
   }

   @Test
   public void testVide() {
   }
   
   @Test
   public void testAddPerson() {
	   jay = new Person();
	   jay.setFirstName("Jay");
	   jay.setBirthDay(new Date());
	   
	   dao.addPerson(jay);
	   assertNotEquals(null, jay.getId());
   }
   
   @Test(expected=Exception.class)
   public void testAddSamePerson() {
	   Person p1 = new Person("Antony", new Date());
	   Person p2 = new Person("Antony", new Date());
	   
	   dao.addPerson(p1);
	   dao.addPerson(p2);
	   
	   dao.removePerson(p1.getId());
	   dao.removePerson(p2.getId());
   }
   
   @Test
   public void testFindPerson() {
	   assertEquals(john.getFirstName(), dao.findPerson(john.getId()).getFirstName());
   }
   
   @Test
   public void testUpdatePerson() {
	   john.setFirstName("Joan");
	   
	   dao.updatePerson(john);
	   assertEquals("Joan", dao.findPerson(john.getId()).getFirstName());
   } 
   
   @Test
   public void testRemovePerson() {
	   Person ryan = new Person("Ryan", new Date());
	   dao.addPerson(ryan);
	   
	   assertNotNull(dao.findPerson(ryan.getId()));
	   dao.removePerson(ryan.getId());
	   assertNull(dao.findPerson(ryan.getId()));

   }



   @Test
   public void testAddPersonCar() {
	   adam = new Person("Adam", new Date());
	   adam.addCar(car1);
	   
	   dao.addPerson(adam);
	   
	   assertTrue(dao.findPerson(adam.getId()).getCars().size() == 1);
	   
   }
   
   @Test
   public void testUpdatePersonCar() {
	   jane.addCar(car2);
	   dao.updatePerson(jane);
	   
	   assertTrue(jane.getCars().contains(car2));
	   
   }
   

   @Test
   public void testAddPersonMovie() {
	   harry = new Person("Harry", null);
	   
	   Movie harryPotter = new Movie("Harry Potter");
	   Movie lotr = new Movie("LoTR");
	   
	   harry.addMovie(harryPotter);
	   harry.addMovie(lotr);
	   
	   dao.addPerson(harry);
	   assertNotNull(dao.findPerson(harry.getId()).getMovies());
	   
	   dao.removePerson(harry.getId());
	   assertNull(dao.findPerson(harry.getId()));
   }   
   
}