package ua.edu.sumdu.j2ee.pohorila.parse.model.services;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import ua.edu.sumdu.j2ee.pohorila.parse.model.entities.Film;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.concurrent.CompletableFuture;

@Component
public interface ServicesInterface {

    public List<Film> getFilmByTitle(String title, @Value("${sbpg.init.APIKEY}") String key, @Value("${sbpg.init.SEARCH_URL}") String myURL) throws UnsupportedEncodingException;
    public Film getFilmById(String imdb, @Value("${sbpg.init.APIKEY}") String key, @Value("${sbpg.init.SEARCH_BY_IMDB_URL}") String myURL);
    public byte[] writeFilmToDocByTemplate(Film film) throws IOException, InterruptedException;
    public CompletableFuture<Film> getFilmByTitleAsync(String title) throws InterruptedException;
}
