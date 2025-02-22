package myapp.dao;

import java.util.Collection;
import java.util.List;
import java.util.Set;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.LockModeType;
import javax.persistence.Persistence;
import javax.persistence.PersistenceException;
import javax.persistence.TypedQuery;

import myapp.model.FirstName;
import myapp.model.Movie;
import myapp.model.Person;


public class Dao2 {

   private EntityManagerFactory factory = null;

   public void init() {
      factory = Persistence.createEntityManagerFactory("myBase");
   }

   public void close() {
      if (factory != null) {
         factory.close();
      }
   }
   
	// Créer un EM et ouvrir une transaction
	private EntityManager newEntityManager() {
	   EntityManager em = factory.createEntityManager();
	   em.getTransaction().begin();
	   return (em);
	}
	
	// Fermer un EM et défaire la transaction si nécessaire
	private void closeEntityManager(EntityManager em) {
	   if (em != null) {
	      if (em.isOpen()) {
	         EntityTransaction t = em.getTransaction();
	         if (t.isActive()) {
	            try {
	               t.rollback();
	            } catch (PersistenceException e) {
	            }
	         }
	         em.close();
	      }
	   }
	}
	
	public <T> T find(Class<T> clazz, Object id) {
	    EntityManager em = null;
	    try {
	        em = newEntityManager();
	        return em.find(clazz, id);
	    } finally {
	        closeEntityManager(em);
	    }
	}

	public <T> Collection<T> findAll(String query, Class<T> clazz) {
	    EntityManager em = null;
	    try {
	        em = newEntityManager();
	        TypedQuery<T> q = em.createQuery(query, clazz);
	        return q.getResultList();
	    } finally {
	        closeEntityManager(em);
	    }
	}
	
	public <T> T add(T entity) {
	    EntityManager em = null;
	    try {
	        em = newEntityManager();
	        em.persist(entity);
	        em.getTransaction().commit();
	        return (entity);
	    } finally {
	        closeEntityManager(em);
	    }
	}

	public <T> T update(T entity) {
	    EntityManager em = null;
	    try {
	        em = newEntityManager();
	        entity = em.merge(entity);
	        em.getTransaction().commit();
	    } finally {
	        closeEntityManager(em);
	    }
	    return entity;
	}

	public <T> void remove(Class<T> clazz, Object pk) {
	    EntityManager em = null;
	    try {
	        em = newEntityManager();
	        T entity = em.find(clazz, pk);
	        if (entity != null) {
	            em.remove(entity);
	        }
	        em.getTransaction().commit();
	    } finally {
	        closeEntityManager(em);
	    }
	}
	
	
	// Nouvelle version simplifiée
	public Person addPerson(Person p) {
	   EntityManager em = null;
	   try {
	      em = newEntityManager();
	      // utilisation de l'EntityManager
	      em.persist(p);
	      em.getTransaction().commit();
	      System.err.println("addPerson witdh id=" + p.getId());
	      return (p);
	   } finally {
	      closeEntityManager(em);
	   }
	}
   
   public Person findPerson(long id) {
	   EntityManager em = null;
	   try {
	      em = newEntityManager();
	      // utilisation de l'EntityManager
	      Person p = em.find(Person.class, id);
	      if(p != null) p.getCars().size();
	      if(p != null) p.getMovies().size();
	      System.err.println("getPerson with id=" + id);
	      return (p);
	   } finally {
	      if (em != null) {
	    	  closeEntityManager(em);
	      }
	   }
	}
   
   public void updatePerson(Person p) {
	   EntityManager em = null;
	   try {
	      em = newEntityManager();
	      // utilisation de l'EntityManager
	      em.merge(p);
	      em.getTransaction().commit();
	      System.err.println("updatePerson with id=" + p.getId());
	   } finally {
	      if (em != null) {
	    	  closeEntityManager(em);
	      }
	   }
	}
   
   public void removePerson(long id) {
	   EntityManager em = null;
	   try {
	      em = newEntityManager();
	      // utilisation de l'EntityManager
	      Person p = em.find(Person.class, id);
	      if(p != null) em.remove(p);
	      em.getTransaction().commit();
	      System.err.println("removePerson with id=" + p.getId());
	      return;
	   } finally {
	      if (em != null) {
	    	  closeEntityManager(em);
	      }
	   }
	}
   
   public List<Person> findAllPersons() {
	    EntityManager em = null;
	    try {
	        em = newEntityManager();
	        String query = "SELECT p FROM Person p";
	        TypedQuery<Person> q = em.createQuery(query, Person.class);
	        return q.getResultList();
	    } finally {
	        closeEntityManager(em);
	    }
	}
   
   public List<Person> findPersonsByFirstName(String pattern) {
	   EntityManager em = null;
	    try {
	        em = newEntityManager();
	        TypedQuery<Person> q = em.createNamedQuery("findPersonsByFirstName", Person.class);
	        q.setParameter("pattern", pattern);
	        return q.getResultList();
	    } finally {
	        closeEntityManager(em);
	    }
	}
   
   public List<FirstName> getAllFirstNames() {
	   EntityManager em = null;
	    try {
	        em = newEntityManager();
	        TypedQuery<FirstName> q = em.createNamedQuery("getAllFirstNames", FirstName.class);
	        return q.getResultList();
	    } finally {
	        closeEntityManager(em);
	    }
   }
   
   public Set<Movie> getMovies(long id) {
	   EntityManager em = null;
	   try {
	      em = newEntityManager();
	      // utilisation de l'EntityManager
	      Person p = em.find(Person.class, id);
	      if(p != null) return p.getMovies();
	      return null;
	   } finally {
	      if (em != null) {
	    	  closeEntityManager(em);
	      }
	   }
   }
   
   public void changeFirstName(long idPerson, String firstName) {
	    EntityManager em = null;
	    try {
	        em = newEntityManager();
	        Person p = em.find(Person.class, idPerson, LockModeType.PESSIMISTIC_WRITE);
	        // Vous pouvez utiliser em.lock(p, LockModeType.PESSIMISTIC_WRITE);
	        try {
	            Thread.sleep(3_000);
	        } catch (InterruptedException e) {
	        }
	        p.setFirstName(firstName);
	        em.getTransaction().commit();
	    } finally {
	        closeEntityManager(em);
	    }
	}
   

}