package sda.db.hibernate;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.hibernate.query.Query;
import sda.db.hibernate.entity.Album;
import sda.db.hibernate.entity.Author;
import sda.db.hibernate.entity.Song;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import java.time.Instant;
import java.util.List;

public class Project {

    public void run(){
        SessionFactory sessionFactory = new Configuration()
                .configure("hibernate.cfg.xml")
                .addAnnotatedClass(Album.class)
                .addAnnotatedClass(Author.class)
                .addAnnotatedClass(Song.class)
                .buildSessionFactory();
// HQL query
        EntityManager em = sessionFactory.createEntityManager();

        EntityTransaction t = em.getTransaction();

        t.begin();

        Author author = new Author();
        author.setName("Super Author");

        Song songD = new Song("song D", author, 220, Instant.now());
        songD.setName("song D");
        songD.setAuthor(author);

        Album albumA = createAlbumA(author);
        albumA.addSong(songD);

        Album albumB = createAlbumB(author);
        albumB.addSong(songD);

        em.persist(author);

        em.persist(albumA);
        em.persist(albumB);

        t.commit();

        List<Song> songs = em.createQuery("FROM Song s", Song.class).getResultList();
        songs.forEach(System.out::println);

        List<Album> albums = em.createQuery("FROM Album", Album.class).getResultList();
        albums.forEach(System.out::println);


    }

    private Album createAlbumA(Author author) {
        Song songA = new Song("song A", author, 195, Instant.now());

        Song songB = new Song("song B", author, 195, Instant.now());

        Album album = new Album();
        album.setName("Old Album");
        album.setAuthor(author);
        album.addSong(songA);
        album.addSong(songB);

        return album;
    }

        private Album createAlbumB(Author author) {
            Song songA = new Song("song C", author, 241, Instant.now());
            songA.setName("song C");
            songA.setAuthor(author);

            Album album = new Album();
            album.setName("New Album");
            album.setAuthor(author);
            album.addSong(songA);

            return album;
    }

// SQL query
    //  try (Session session = sessionFactory.openSession()) {
    //     Query q = session.createQuery("SELECT s FROM Song s", Song.class);

}
