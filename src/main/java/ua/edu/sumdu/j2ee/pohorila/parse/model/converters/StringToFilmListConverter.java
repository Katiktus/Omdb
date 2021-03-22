package ua.edu.sumdu.j2ee.pohorila.parse.model.converters;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import ua.edu.sumdu.j2ee.pohorila.parse.model.entities.Film;
import ua.edu.sumdu.j2ee.pohorila.parse.model.entities.FilmList;

/**
 * Converter from string to FilmList.
 */
@Component
public class StringToFilmListConverter implements Converter<String, FilmList> {
    private static final Logger logger = LogManager.getLogger();

    /**
     * Overriding converter.
     * @param source resource for conversion.
     * @return FilmList.
     */
    @Override
    public FilmList convert(String source) {
        FilmList film = new FilmList();
        try {
            if(source.contains("Search")) {
                JSONObject obj = new JSONObject(source);
                JSONArray arr = obj.getJSONArray("Search");
                for (int i = 0; i < arr.length(); i++) {
                    Film response = new Film();
                    response.setTitle(arr.getJSONObject(i).getString("Title"));
                    response.setPosterLink(arr.getJSONObject(i).getString("Poster"));
                    response.setYear(arr.getJSONObject(i).getString("Year"));
                    film.add(response);
                }
            }
            else{
                JSONArray arr = new JSONArray(source);
                for (int i = 0; i < arr.length(); i++) {
                    Film response = new Film();
                    response.setTitle(arr.getJSONObject(i).getString("title"));
                    response.setPosterLink(arr.getJSONObject(i).getString("posterLink"));
                    response.setYear(arr.getJSONObject(i).getString("year"));
                    film.add(response);
                }
            }
        } catch (JSONException e) {
            logger.error("Error message", e);
        }
        return film;
    }
}

