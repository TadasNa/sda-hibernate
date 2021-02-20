package sda.db.hibernate.entity;

import com.sun.istack.NotNull;
import com.sun.istack.Nullable;

import javax.persistence.*;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.UUID;
import java.util.stream.Collectors;

@Entity
@Table(name = "songs")
public class Song {

    @Id
    @GeneratedValue
    private UUID id;

    @Column(nullable = false)
    private String name;

    @OneToOne
    private Author author;

    @ManyToMany
    private List<Album> album = new ArrayList<>();

    @Column(nullable = false)
    private Integer duration;

    @Column(nullable = false)
    private Instant releaseDate;

    public Song(String name, Author author, Integer duration, Instant releaseDate) {
        this.name = name;
        this.author = author;
        this.duration = duration;
        this.releaseDate = releaseDate;
    }

    public Instant getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(Instant releaseDate) {
        this.releaseDate = releaseDate;
    }

    public Integer getDuration() {
        return duration;
    }
    public String getDurationString() {
        int s = duration % 60;
        return duration / 60 + ":" + (s < 10 ? "0" : "") +duration % 60;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Author getAuthor() {
        return author;
    }

    public void setAuthor(Author author) {
        this.author = author;
    }

    public List<Album> getAlbum() {
        return album;
    }

    public void setAlbum(List<Album> album) {
        this.album = album;
    }

    @Override
    public String toString() {
        DateTimeFormatter formatter =
                DateTimeFormatter.ofLocalizedDate( FormatStyle.SHORT )
                        .withLocale( Locale.forLanguageTag("LT") )
                        .withZone( ZoneId.systemDefault() );
        return "Song{" +
                "name = '" + name + '\'' +
                ", author = '" + author +
                ", album = " + album.stream().map(Album::getName).collect(Collectors.toList()) +
                ", duration = " + getDurationString() +
                ", release date = " + formatter.format(releaseDate) +
                '}';
    }
}
