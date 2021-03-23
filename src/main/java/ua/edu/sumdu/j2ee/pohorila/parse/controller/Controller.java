package ua.edu.sumdu.j2ee.pohorila.parse.controller;

import org.apache.commons.io.IOUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Async;
import org.springframework.web.bind.annotation.*;
import ua.edu.sumdu.j2ee.pohorila.parse.model.entities.Film;
import ua.edu.sumdu.j2ee.pohorila.parse.model.entities.FilmList;
import ua.edu.sumdu.j2ee.pohorila.parse.model.services.ServicesInterface;

import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.concurrent.CompletableFuture;

/**
 * Class for controller.
 */
@RestController
public class Controller {
    /**
     * Services injection.
     */
    private final ServicesInterface filmsService;
    private static final Logger logger = LogManager.getLogger();

    /**
     * Constructor for controller.
     * @param filmsService
     */
    public Controller(ServicesInterface filmsService) {
        this.filmsService = filmsService;
    }

    /**
     * Method for receive film using id.
     * @param id search identifier.
     * @return result of request.
     */
    @RequestMapping(value = "/film", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<?> getFilmId(@RequestParam(value = "id", defaultValue = "tt0372784") String id) {
        logger.info("Get film by is here: " + id);
        Film filmById = filmsService.getFilmById(id);
        return ResponseEntity.ok(filmById);
    }

    /**
     * Method for receive film using title.
     * @param title what we want to find.
     * @return list of films.
     * @throws UnsupportedEncodingException
     */
    @RequestMapping(value = "/films", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<?> getFilmTitle(@RequestParam(value = "title", defaultValue = "Life") String title) throws UnsupportedEncodingException {
        logger.info("Get film by title here: " + title);
        FilmList filmList = filmsService.getFilmByTitle(title);
        logger.info("Get film by title ends here: " + title);
        return ResponseEntity.ok(filmList);
    }

    /**
     * Test method.
     */
    @RequestMapping(value = "/", method = RequestMethod.GET)
    public ResponseEntity<?> test() {
        return ResponseEntity.ok("Everything is ok");
    }

    /**
     * Method for writing information about film.
     * @param id identification for search.
     * @return file.
     * @throws IOException
     * @throws InterruptedException
     */
    @RequestMapping(value = "/writeFilm", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public ResponseEntity<?> writeFilmId(@RequestParam(value = "id", defaultValue = "tt0372784") String id) throws IOException, InterruptedException {
        HttpHeaders header = new HttpHeaders();
        Film film = filmsService.getFilmById(id);
        header.setContentDispositionFormData( "attachment", film.getTitle().replace(" ", "_") + ".docx");
        header.setContentType(new MediaType("application", "vnd.openxmlformats-officedocument.wordprocessingml.document"));
        byte[] file = filmsService.writeFilmToDocByTemplate(film);
        header.setContentLength(file.length);
        InputStreamResource inputStreamResource = new InputStreamResource (new ByteArrayInputStream(file));
        return ResponseEntity.ok().headers(header).body(inputStreamResource);
    }

    /**
     * Method for async invocation of searching.
     * @param title identification for search.
     * @return list of films.
     * @throws InterruptedException
     */
    @Async("asyncExecutor")
    @RequestMapping(path = "/byTitle")
    @ResponseStatus(HttpStatus.OK)
    public CompletableFuture<FilmList> getByTitle(@RequestParam(value = "title", defaultValue = "Life") String title) throws InterruptedException {
        CompletableFuture<FilmList> film = filmsService.getFilmByTitleAsync(title);
        CompletableFuture.allOf(film).join();
        return film;
    }
}



