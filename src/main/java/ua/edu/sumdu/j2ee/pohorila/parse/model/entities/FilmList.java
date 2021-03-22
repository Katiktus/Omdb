package ua.edu.sumdu.j2ee.pohorila.parse.model.entities;

import java.util.*;

public class FilmList extends ArrayList<Film> {
    public List<Film> films;

    public FilmList(){
        super();
    }

    @Override
    public boolean add(Film film) {
        return films.add(film);
    }

    @Override
    public boolean remove(Object o) {
        return films.remove(o);
    }

    @Override
    public boolean containsAll(Collection<?> c) {
        return films.containsAll(c);
    }

    @Override
    public boolean addAll(Collection<? extends Film> c) {
        return films.addAll(c);
    }

    @Override
    public boolean addAll(int index, Collection<? extends Film> c) {
        return films.addAll(index, c);
    }

    @Override
    public boolean removeAll(Collection<?> c) {
        return films.removeAll(c);
    }

    @Override
    public boolean retainAll(Collection<?> c) {
        return films.retainAll(c);
    }

    @Override
    public void clear() {
        films.clear();
    }

    @Override
    public int size(){
        return films.size();
    }

    @Override
    public boolean isEmpty() {
        return films.isEmpty();
    }

    @Override
    public boolean contains(Object o) {
        return films.contains(o);
    }

    @Override
    public Iterator<Film> iterator() {
        return films.iterator();
    }

    @Override
    public Object[] toArray() {
        return films.toArray();
    }

    @Override
    public <T> T[] toArray(T[] a) {
        return films.toArray(a);
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
    public Film get(int index) {
        return films.get(index);
    }

    @Override
    public Film set(int index, Film element) {
        films.set(index, element);
        return element;
    }

    @Override
    public void add(int index, Film element) {
        films.add(index, element);
    }

    @Override
    public Film remove(int index) {
        return films.remove(index);
    }

    @Override
    public int indexOf(Object o) {
        return films.indexOf(o);
    }

    @Override
    public int lastIndexOf(Object o) {
        return films.lastIndexOf(o);
    }

    @Override
    public ListIterator<Film> listIterator() {
        return films.listIterator();
    }

    @Override
    public ListIterator<Film> listIterator(int index) {
        return films.listIterator(index);
    }

    @Override
    public List<Film> subList(int fromIndex, int toIndex) {
        return films.subList(fromIndex, toIndex);
    }

    @Override
    public String toString() {
        return "FilmList{" +
                "films=" + films +
                '}';
    }

    public List<Film> getFilms() {
        return films;
    }

    public void setFilms(List<Film> films) {
        this.films = films;
    }
}
