package ua.edu.sumdu.j2ee.pohorila.parse.model.converters;

import org.springframework.core.convert.converter.Converter;
import ua.edu.sumdu.j2ee.pohorila.parse.model.entities.Film;
import org.json.*;

import java.util.ArrayList;
import java.util.List;

public class Converters implements Converter<String, Film>{
        public Film convert(String source) {
            Film film = new Film();
            try {
                JSONObject obj = new JSONObject(source);
                film.setTitle(obj.getString("Title"));
                film.setDirectory(obj.getString("Director"));
                film.setDescription(obj.getString("Plot"));
                film.setPosterLink(obj.getString("Poster"));
                film.setDuration(obj.getString("Runtime"));
                film.setYear(obj.getInt("Year"));
                film.setGenre(obj.getString("Genre"));
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return film;
       }

   /* public List<Film> convert(String source) {
        List<Film> films = new ArrayList<Film>();

        try {
            JSONObject obj = new JSONObject(source);
            film.setTitle(obj.getString("Title"));
            film.setDirectory(obj.getString("Director"));
            film.setDescription(obj.getString("Plot"));
            film.setPosterLink(obj.getString("Poster"));
            film.setDuration(obj.getString("Runtime"));
            film.setYear(obj.getInt("Year"));
            film.setGenre(obj.getString("Genre"));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return films;
    }*/
}
