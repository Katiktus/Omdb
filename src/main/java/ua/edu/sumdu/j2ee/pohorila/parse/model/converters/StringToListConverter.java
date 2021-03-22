package ua.edu.sumdu.j2ee.pohorila.parse.model.converters;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import ua.edu.sumdu.j2ee.pohorila.parse.model.entities.Film;

import java.util.ArrayList;
import java.util.List;

@Component
public class StringToListConverter  implements Converter<String, List<Film>> {
    private static final Logger logger = LogManager.getLogger();

    @Override
    public List<Film> convert(String source) {
        List<Film> film = new ArrayList<>();
        try {
            JSONObject obj = new JSONObject(source);
            JSONArray arr = obj.getJSONArray("Search");
            for (int i=0; i<arr.length(); i++) {
                Film response = new Film();
                response.setTitle(arr.getJSONObject(i).getString("Title"));
                response.setDirectory(arr.getJSONObject(i).getString("Director"));
                response.setDescription(arr.getJSONObject(i).getString("Plot"));
                response.setPosterLink(arr.getJSONObject(i).getString("Poster"));
                response.setDuration(arr.getJSONObject(i).getString("Runtime"));
                response.setYear(arr.getJSONObject(i).getString("Year"));
                response.setGenre(arr.getJSONObject(i).getString("Genre"));
                film.add(response);
            }
        } catch (JSONException e) {
            logger.error("Error message", e);
        }
        return film;
    }
}