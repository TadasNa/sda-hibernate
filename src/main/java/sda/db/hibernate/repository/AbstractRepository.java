package sda.db.hibernate.repository;

import sda.db.hibernate.entity.Agent;
import sda.db.hibernate.entity.Song;
import sda.db.hibernate.entity.util.AgentId;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;

public abstract class AbstractRepository<T, ID> implements Repository<T, ID>{

    protected EntityManager entityManager;
    private Class<?extends T> entityClass;

    public AbstractRepository(EntityManager entityManager, Class<? extends T> entityClass) {
        this.entityManager = entityManager;
        this.entityClass = entityClass;
    }

    @Override
    public T find(ID id) {
        return entityManager.find(entityClass, id);
    }

    @Override
    public void save(T entity) {
        EntityTransaction transaction = entityManager.getTransaction();
        boolean isTransactionActive = transaction.isActive();
        if (!isTransactionActive) {
            transaction.begin();
        }
        entityManager.persist(entity);
        if (!isTransactionActive) {
            transaction.commit();
        }
    }

    @Override
    public void delete(T entity) {
        entityManager.remove(entity);
    }
}
