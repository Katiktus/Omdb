package ua.edu.sumdu.j2ee.pohorila.parse.controller;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Async;
import org.springframework.web.bind.annotation.*;
import ua.edu.sumdu.j2ee.pohorila.parse.model.entities.Film;
import ua.edu.sumdu.j2ee.pohorila.parse.model.services.ServicesInterface;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

@RestController
public class Controller {
    private final ServicesInterface filmsService;
    private static final Logger logger = LogManager.getLogger();

    public Controller(ServicesInterface filmsService) {
        this.filmsService = filmsService;
    }

    @RequestMapping(value = "/film", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<?> getFilmId(@RequestParam(value = "id", defaultValue = "tt0372784") String id,
                                     @RequestParam(value = "apikey", defaultValue = "6b935860") String apikey,
                                     @Value("${sbpg.init.SEARCH_BY_IMDB_URL}") String myurl){
        System.out.println("Get film by is here: " + id);
        Film filmById = filmsService.getFilmById(id, apikey, myurl);
        return ResponseEntity.ok(filmById);
    }

    @RequestMapping(value = "/films", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<?> getFilmTitle(@RequestParam(value = "title", defaultValue = "Life") String title,
                                     @RequestParam(value = "apikey", defaultValue = "6b935860") String apikey,
                                     @Value("${sbpg.init.SEARCH_URL}") String myurl) throws UnsupportedEncodingException {
        System.out.println("Get film by title here: " + title);
        List<Film> filmByTitle = filmsService.getFilmByTitle(title, apikey, myurl);
        System.out.println("Get film by title ends here: " + title);
        return ResponseEntity.ok(filmByTitle);
    }

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public ResponseEntity<?> test(){
        return ResponseEntity.ok("Everything is ok");
    }

    @RequestMapping(value = "/writeFilm", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<?> writeFilmId(@RequestParam(value = "id", defaultValue = "tt0372784") String id,
                                       @RequestParam(value = "apikey", defaultValue = "6b935860") String apikey,
                                       @Value("${sbpg.init.SEARCH_BY_IMDB_URL}") String myurl) throws IOException, InterruptedException {
        System.out.println("Write is here: " + id);
        Film filmById = filmsService.getFilmById(id, apikey, myurl);
        filmsService.writeFilmToDocByTemplate(filmById);
        return ResponseEntity.ok(filmById);
    }


    /*@Async("asyncExecutor")
    @RequestMapping(path = "/byTitle")
    public void getByTitle(@RequestParam(value = "title", defaultValue = "Life") String title) throws InterruptedException, ExecutionException, UnsupportedEncodingException {
        logger.info("testAsynch Start");
        String[] films = title.split(",");
        List<Film> f= new ArrayList<Film>();
        for (String t: films) {
            f = filmsService.getFilmByTitle(t, "6b935860", "${sbpg.init.SEARCH_URL}");
        }
        CompletableFuture<Film> film = filmsService.getFilmByTitleAsync(title);
        CompletableFuture.allOf(film).join();
        logger.info("Film " + film.get());
    }*/

    @Async("asyncExecutor")
    @RequestMapping(path = "/byTitle")
    public void getByTitle(@RequestParam(value = "title", defaultValue = "Life") String title) throws InterruptedException, ExecutionException {
        logger.info("testAsynch Start");
        CompletableFuture<List> film = filmsService.getFilmByTitleAsync(title);
        logger.info("allofJoin Start");
        CompletableFuture.allOf(film).join();
        logger.info("allofJoin End");
        logger.info("Film " + film.get());
    }
}

