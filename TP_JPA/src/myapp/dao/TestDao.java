package myapp.dao;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.util.Date;

import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import myapp.model.Address;
import myapp.model.CV;
import myapp.model.Car;
import myapp.model.Movie;
import myapp.model.Person;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = SpringConfiguration.class)
public class TestDao {

	@Autowired
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

	@Test
	public void testAddPerson() {
		jay = new Person();
		jay.setFirstName("Jay");
		jay.setBirthDay(new Date());

		dao.add(jay);
		assertNotEquals(null, jay.getId());
	}

	@Test(expected = Exception.class)
	public void testAddSamePerson() {
		Person p1 = new Person("Antony", new Date());
		Person p2 = new Person("Antony", new Date());

		dao.add(p1);
		dao.add(p2);

		dao.remove(Person.class, p1.getId());
		dao.remove(Person.class, p2.getId());
	}

	@Test
	public void testFindPerson() {
		assertEquals(john.getFirstName(), dao.find(Person.class, john.getId()).getFirstName());
	}

	@Test
	public void testUpdatePerson() {
		john.setFirstName("Joan");

		dao.update(john);
		assertEquals("Joan", dao.find(Person.class, john.getId()).getFirstName());
	}

	@Test
	public void testRemovePerson() {
		Person ryan = new Person("Ryan", new Date());
		dao.add(ryan);

		assertNotNull(dao.find(Person.class, ryan.getId()));
		dao.remove(Person.class, ryan.getId());
		assertNull(dao.find(Person.class, ryan.getId()));

	}

	@Test
	public void testAddPersonCar() {
		adam = new Person("Adam", new Date());
		adam.addCar(car1);

		dao.add(adam);

		assertTrue(dao.find(Person.class, adam.getId()).getCars().size() == 1);

	}

	@Test
	public void testUpdatePersonCar() {
		jane.addCar(car2);
		dao.update(jane);

		assertTrue(jane.getCars().contains(car2));

	}

	@Test
	public void testAddPersonMovie() {
		harry = new Person("Harry", null);

		Movie harryPotter = new Movie("Harry Potter");
		Movie lotr = new Movie("LoTR");

		harry.addMovie(harryPotter);
		harry.addMovie(lotr);

		dao.add(harry);
		assertNotNull(dao.find(Person.class, harry.getId()).getMovies());

		dao.remove(Person.class, harry.getId());
		assertNull(dao.find(Person.class, harry.getId()));
	}

	@Test
	public void testAddCV() {
		harry = new Person("Harry", new Date());
		CV cv = new CV("body", harry);

		harry.setCv(cv);
		harry.getCv();

		dao.add(harry);
		assertNotNull(dao.find(Person.class, harry.getId()).getCv());
		assertEquals("body", dao.find(Person.class, harry.getId()).getCv());

	}

	@Test
	public void testChangeFirstName() {
		hermione = new Person("Harry", null);

		dao.add(hermione);
		dao.changeFirstName(hermione.getId(), "Hermione");

		assertEquals("Hermione", dao.find(Person.class, hermione.getId()).getFirstName());

		dao.remove(Person.class, hermione.getId());
	}

}