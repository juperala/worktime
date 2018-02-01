package fi.jperala.worktime.jpa.impl;

import java.io.Serializable;
import java.util.List;
import javax.persistence.EntityManager;

/**
 * Abstract JPA DAO
 *
 * @param <T> Implementing type.
 */
public abstract class AbstractDAO<T extends Serializable> {

    private final Class<T> clazz;
    protected EntityManager em = null;

    public AbstractDAO(Class<T> clazzToSet) {
        clazz = clazzToSet;
    }
    
    protected synchronized void initEntityManager() {
        if(em == null) {
            em = EmfProvider.getEmf().createEntityManager();            
        }
    }

    public synchronized void releaseEntityManager() {
        if(em != null) {
            em.close();
            em = null;
        }
    }
    
    public T findOne(int id) {
        initEntityManager();
        T value = em.find(clazz, id);
        return value;
    }

    public List<T> findAll() {
        initEntityManager();
        List<T> list = em.createQuery("from " + clazz.getName() + " r").getResultList();
        return list;        
    }

    public void create(T entity) {
        initEntityManager();
        em.getTransaction().begin();
        em.persist(entity);
        em.getTransaction().commit();
    }

    public T update(T entity) {
        initEntityManager();
        em.getTransaction().begin();
        T e = em.merge(entity);
        em.getTransaction().commit();
        return e; 
    }

    public void delete(T entity) {
        initEntityManager();
        em.getTransaction().begin();
        em.refresh(entity);
        em.remove(entity);
        em.getTransaction().commit();
    }

    public void deleteById(int entityId) {
        initEntityManager();
        em.getTransaction().begin();
        T entity = findOne(entityId);
        em.remove(entity);
        em.getTransaction().commit();
    }
}
