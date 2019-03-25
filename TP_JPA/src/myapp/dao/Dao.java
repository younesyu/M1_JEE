package myapp.dao;

import java.util.Collection;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.LockModeType;
import javax.persistence.Persistence;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceContextType;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;

import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import myapp.model.FirstName;
import myapp.model.Person;

@Service
@Repository
@Transactional
public class Dao {

	private EntityManagerFactory factory = null;

	public void init() {
		factory = Persistence.createEntityManagerFactory("myBase");
	}

	public void close() {
		if (factory != null) {
			factory.close();
		}
	}

	@PersistenceContext(type = PersistenceContextType.TRANSACTION)
	EntityManager em;

	public <T> T find(Class<T> clazz, Object id) {
		return em.find(clazz, id);
	}

	public <T> Collection<T> findAll(String query, Class<T> clazz) {
		TypedQuery<T> q = em.createQuery(query, clazz);
		return q.getResultList();
	}

	public Person addPerson(Person p) {
		em.persist(p);
		System.err.println("addPerson witdh id=" + p.getId());
		return (p);
	}

	public <T> T add(T entity) {
		em.persist(entity);
		return (entity);
	}

	public <T> T update(T entity) {
		entity = em.merge(entity);
		return entity;
	}

	public <T> void remove(Class<T> clazz, Object pk) {
		T entity = em.find(clazz, pk);
		if (entity != null) {
			em.remove(entity);
		}
	}

	public List<Person> findPersonsByFirstName(String pattern) {
		TypedQuery<Person> q = em.createNamedQuery("findPersonsByFirstName", Person.class);
		q.setParameter("pattern", pattern);
		return q.getResultList();
	}

	public List<FirstName> getAllFirstNames() {
		TypedQuery<FirstName> q = em.createNamedQuery("getAllFirstNames", FirstName.class);
		return q.getResultList();
	}

	public void changeFirstName(long idPerson, String firstName) {
		Person p = em.find(Person.class, idPerson, LockModeType.PESSIMISTIC_WRITE);
		// Vous pouvez utiliser em.lock(p, LockModeType.PESSIMISTIC_WRITE);
		try {
			Thread.sleep(3_000);
		} catch (InterruptedException e) {
		}
		p.setFirstName(firstName);
	}

}