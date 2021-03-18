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
import ua.edu.sumdu.j2ee.pohorila.parse.model.services.ServicesInterface;

import java.util.ArrayList;
import java.util.List;

@Component
public class StringToFilmListConverter implements Converter<String, List<Film>> {
    private static final Logger logger = LogManager.getLogger();
    @Autowired
    private static ServicesInterface services;

    @Override
    public List<Film> convert(String source) {
        List<Film> films = new ArrayList<Film>();
        try {
            JSONObject obj = new JSONObject(source);
            JSONArray arr = obj.getJSONArray("Search");
            for (int i=0; i<arr.length(); i++) {
                JSONObject search = arr.getJSONObject(i);
                Film jsonResponse = services.getFilmById(search.getString("imdbID"));
                films.add(jsonResponse);
            }
        } catch (JSONException e) {
            logger.error("Error message", e);
        }
        return films;
    }
}
