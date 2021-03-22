package ua.edu.sumdu.j2ee.pohorila.parse.model.converters;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import ua.edu.sumdu.j2ee.pohorila.parse.model.entities.Film;
import ua.edu.sumdu.j2ee.pohorila.parse.model.entities.FilmList;
import ua.edu.sumdu.j2ee.pohorila.parse.model.services.ServicesInterface;

@Component
public class StringToFilmListConverter implements Converter<String, FilmList> {
    private static final Logger logger = LogManager.getLogger();
    @Autowired
    private static ServicesInterface services;

    @Override
    public FilmList convert(String source) {
        FilmList film = new FilmList();
        try {
            JSONObject obj = new JSONObject(source);
            JSONArray arr = obj.getJSONArray("Search");
            for (int i=0; i<arr.length(); i++) {
                JSONObject search = arr.getJSONObject(i);
                Film response = services.getFilmById(search.getString("imdbID"));
                System.out.println(response);
                film.add(response);
            }
        } catch (JSONException e) {
            logger.error("Error message", e);
        }
        return film;
    }
}

