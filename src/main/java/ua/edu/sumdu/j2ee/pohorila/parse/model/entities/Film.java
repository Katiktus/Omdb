package ua.edu.sumdu.j2ee.pohorila.parse.model.entities;

import java.util.Objects;

/**
 * Entity for Film.
 */
public class Film {

    String title;
    String directory;
    String year;
    String duration;
    String genre;
    String description;
    String posterLink;


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Film film = (Film) o;
        return year.equals(film.year) && Objects.equals(title, film.title) && Objects.equals(directory, film.directory) && Objects.equals(duration, film.duration) && Objects.equals(genre, film.genre) && Objects.equals(description, film.description) && Objects.equals(posterLink, film.posterLink);
    }

    @Override
    public int hashCode() {
        return Objects.hash(title, directory, year, duration, genre, description, posterLink);
    }

    @Override
    public String toString() {
        return "Film{" +
                "title='" + title + '\'' +
                ", directory='" + directory + '\'' +
                ", year=" + year +
                ", duration='" + duration + '\'' +
                ", genre='" + genre + '\'' +
                ", description='" + description + '\'' +
                ", posterLink='" + posterLink + '\'' +
                '}';
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDirectory() {
        return directory;
    }

    public void setDirectory(String directory) {
        this.directory = directory;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPosterLink() {
        return posterLink;
    }

    public void setPosterLink(String posterLink) {
        this.posterLink = posterLink;
    }


}

