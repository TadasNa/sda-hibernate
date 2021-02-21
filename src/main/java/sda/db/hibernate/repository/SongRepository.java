package sda.db.hibernate.repository;

import sda.db.hibernate.entity.Song;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import java.util.List;
import java.util.UUID;

public class SongRepository extends AbstractRepository<Song, UUID> {

    public SongRepository(EntityManager entityManager) {
        super(entityManager, Song.class);
    }

    @Override
    public List<Song> findALL() {
        return entityManager.createQuery("FROM Song s", Song.class).getResultList();
    }
}
