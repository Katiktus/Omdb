package ua.edu.sumdu.j2ee.pohorila.parse.controller;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Async;
import org.springframework.web.bind.annotation.*;
import ua.edu.sumdu.j2ee.pohorila.parse.model.entities.Film;
import ua.edu.sumdu.j2ee.pohorila.parse.model.entities.FilmList;
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
    public ResponseEntity<?> getFilmId(@RequestParam(value = "id", defaultValue = "tt0372784") String id) {
        logger.info("Get film by is here: " + id);
        Film filmById = filmsService.getFilmById(id);
        return ResponseEntity.ok(filmById);
    }

    @RequestMapping(value = "/films", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<?> getFilmTitle(@RequestParam(value = "title", defaultValue = "Life") String title) throws UnsupportedEncodingException {
        logger.info("Get film by title here: " + title);
        FilmList filmList = filmsService.getFilmByTitle(title);
        logger.info("Get film by title ends here: " + title);
        return ResponseEntity.ok(filmList);
    }

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public ResponseEntity<?> test() {
        return ResponseEntity.ok("Everything is ok");
    }

    @RequestMapping(value = "/writeFilm", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<?> writeFilmId(@RequestParam(value = "id", defaultValue = "tt0372784") String id) throws IOException, InterruptedException {
        logger.info("Write is here: " + id);
        Film filmById = filmsService.getFilmById(id);
        return ResponseEntity.ok(filmsService.writeFilmToDocByTemplate(filmById));
    }

    @Async("asyncExecutor")
    @RequestMapping(path = "/byTitle")
    @ResponseStatus(HttpStatus.OK)
    public CompletableFuture<FilmList> getByTitle(@RequestParam(value = "title", defaultValue = "Life") String title) throws InterruptedException, ExecutionException {
        CompletableFuture<FilmList> film = filmsService.getFilmByTitleAsync(title);
        CompletableFuture.allOf(film).join();
        return film;
    }
}



