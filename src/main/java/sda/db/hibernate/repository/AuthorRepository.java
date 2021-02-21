package sda.db.hibernate.repository;

import com.sun.xml.bind.v2.model.core.ID;
import sda.db.hibernate.entity.Agent;
import sda.db.hibernate.entity.Author;

import javax.persistence.EntityManager;
import javax.persistence.Id;
import java.util.List;
import java.util.UUID;

public class AuthorRepository extends AbstractRepository<Author, UUID> {

    public AuthorRepository(EntityManager entityManager) {
        super(entityManager, Author.class);
    }

    @Override
    public List<Author> findALL() {
        return entityManager.createQuery("FROM Author", Author.class).getResultList();
    }
}
