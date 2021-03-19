package ua.edu.sumdu.j2ee.pohorila.parse.model.entities;

import java.util.*;

public class FilmList extends ArrayList<Object>{
    public ArrayList<Film> films;

    public FilmList(){
        films = new ArrayList<>();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        FilmList objects = (FilmList) o;
        return Objects.equals(films, objects.films);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), films);
    }

    @Override
    public String toString() {
        return "FilmList{" +
                "films=" + films +
                '}';
    }

    public ArrayList<Film> getFilms() {
        return films;
    }

    public void setFilms(ArrayList<Film> films) {
        this.films = films;
    }
}
