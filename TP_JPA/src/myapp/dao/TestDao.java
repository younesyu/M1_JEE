package myapp.dao;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

import java.util.Date;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import myapp.model.Person;

public class TestDao {

   static Dao dao;

   @BeforeClass
   public static void beforeAll() {
      dao = new Dao();
      dao.init();
      
   }

   @AfterClass
   public static void afterAll() {
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
	   Person p = new Person();
	   p.setFirstName("Jay");
	   p.setBirthDay(new Date());
	   
	   dao.addPerson(p);
	   assertNotEquals(null, p.getId());
   }
   
   @Test(expected=Exception.class)
   public void testAddSamePerson() {
	   Person p1 = new Person("Anthony", new Date());
	   Person p2 = new Person("Anthony", new Date());
	   
	   dao.addPerson(p1);
	   dao.addPerson(p2);
   }
   
   @Test
   public void testFindPerson() {
	   assertNotEquals(null, dao.findPerson(1));
   }   
   
   @Test
   public void testUpdatePerson() {
	   Person p = dao.findPerson(1);
	   p.setFirstName("Joan");
	   
	   dao.updatePerson(p);
	   assertEquals("Joan", dao.findPerson(p.getId()).getFirstName());
   } 


}