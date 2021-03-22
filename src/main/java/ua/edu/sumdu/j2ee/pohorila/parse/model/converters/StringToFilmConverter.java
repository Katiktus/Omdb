package ua.edu.sumdu.j2ee.pohorila.parse.model.converters;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import ua.edu.sumdu.j2ee.pohorila.parse.model.entities.Film;
import org.json.*;
import ua.edu.sumdu.j2ee.pohorila.parse.model.services.ServicesInterface;

@Component
public class StringToFilmConverter implements Converter<String, Film>{
    private static final Logger logger = LogManager.getLogger();

    @Override
    public Film convert(String source) {
            Film film = new Film();
            try {
                JSONObject obj = new JSONObject(source);
                film.setTitle(obj.getString("Title"));
                film.setDirectory(obj.getString("Director"));
                film.setDescription(obj.getString("Plot"));
                film.setPosterLink(obj.getString("Poster"));
                film.setDuration(obj.getString("Runtime"));
                film.setYear(obj.getString("Year"));
                film.setGenre(obj.getString("Genre"));
            } catch (JSONException e) {
                logger.error("Error message", e);
            }
        return film;
       }

}
