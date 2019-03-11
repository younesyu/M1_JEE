package myapp.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import javax.persistence.PersistenceException;
import javax.persistence.TypedQuery;

import myapp.model.Person;

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
   
   public void deletePerson(Person p) {
	   EntityManager em = null;
	   try {
	      em = newEntityManager();
	      // utilisation de l'EntityManager
	      if (findPerson(p.getId()) != null) // if statement may be disposable
	    	  em.remove(p);
	      em.getTransaction().commit();
	      System.err.println("deletePerson with id=" + p.getId());
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

}