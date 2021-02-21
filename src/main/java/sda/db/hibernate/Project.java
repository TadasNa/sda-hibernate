package sda.db.hibernate;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import sda.db.hibernate.entity.*;
import sda.db.hibernate.entity.util.AgentId;
import sda.db.hibernate.repository.AgentRepository;
import sda.db.hibernate.repository.AlbumRepository;
import sda.db.hibernate.repository.AuthorRepository;
import sda.db.hibernate.repository.SongRepository;

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
                .addAnnotatedClass(Agent.class)
                .buildSessionFactory();
// HQL query
        EntityManager em = sessionFactory.createEntityManager();
        SongRepository songRepository = new SongRepository(em);
        AgentRepository agentRepository = new AgentRepository(em);
        AuthorRepository authorRepository = new AuthorRepository(em);
        AlbumRepository albumRepository = new AlbumRepository(em);

        EntityTransaction t = em.getTransaction();

        t.begin();

        Author author = new Author();
        author.setName("Super Author");

        Song songD = new Song("song D", author, 220, Instant.now());

        Album albumA = createAlbumA(author);
        albumA.addSong(songD);

        Album albumB = createAlbumB(author);
        albumB.addSong(songD);

        Agent agent = new Agent();
        agent.setId(new AgentId("Vardenis","Pavardenis"));
        agent.setActiveSince(Instant.now());
        author.setAgent(agent);

        em.persist(agent);
        authorRepository.save(author);
        albumRepository.save(albumA);
        albumRepository.save(albumB);


        t.commit();

        System.out.println("=========");
        List<Song> songs = songRepository.findALL();
        songs.forEach(System.out::println);
        System.out.println("=========");
        Song songA = songs.stream().findFirst().get();
        songA.setLyrics("some other lyrics");
        songRepository.save(songA);
        System.out.println(songRepository.find(songA.getId()));
        songRepository.delete(songA);
        System.out.println(songRepository.find(songA.getId()));
        System.out.println("=========");
        System.out.println("=========");
        List<Album> albums = albumRepository.findALL();
        albums.forEach(System.out::println);
        System.out.println("=========");
        System.out.println("=========");
        System.out.println(agentRepository.find("Vardenis", "Pavardenis"));
        System.out.println("=========");
        List<Author>authors = authorRepository.findALL();
        authors.forEach(System.out::println);
        System.out.println("=========");


    }

    private Album createAlbumA(Author author) {
        Song songA = new Song("song A", author, 195, Instant.now());
        songA.setLyrics("Some lyrics");

        Song songB = new Song("song B", author, 195, Instant.now());

        Album album = new Album();
        album.setName("Old Album");
        album.setAuthor(author);
        album.addSong(songA);
        album.addSong(songB);

        return album;
    }

        private Album createAlbumB(Author author) {
            Song songC = new Song("song C", author, 241, Instant.now());


            Album album = new Album();
            album.setName("New Album");
            album.setAuthor(author);
            album.addSong(songC);

            return album;
    }

// SQL query
    //  try (Session session = sessionFactory.openSession()) {
    //     Query q = session.createQuery("SELECT s FROM Song s", Song.class);

}
