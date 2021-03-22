package ua.edu.sumdu.j2ee.pohorila.parse.model.services;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRun;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.convert.ConversionService;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import ua.edu.sumdu.j2ee.pohorila.parse.model.SendGetRequestInterface;
import ua.edu.sumdu.j2ee.pohorila.parse.model.entities.Film;
import ua.edu.sumdu.j2ee.pohorila.parse.model.entities.FilmList;

import java.io.*;
import java.net.URLEncoder;
import java.util.concurrent.CompletableFuture;

@Service
public class FilmServices implements  ServicesInterface{
    private static final Logger logger = LogManager.getLogger();
    @Autowired
    private ConversionService conversionService;
    @Autowired
    private SendGetRequestInterface getRequestInterface;
    @Value("${sbpg.init.APIKEY}")
    public String key;
    @Value("${sbpg.init.SEARCH_BY_IMDB_URL}")
    public String myURLid;
    @Value("${sbpg.init.SEARCH_URL}")
    public String myURL;

    protected FilmServices(){
        super();
    }

    public FilmServices(String s) {
    }

    /**
     * Service for receive film by ID.
     * @param imdb identification for search.
     * @return Film.
     */
    public Film getFilmById(String imdb){
        String requestUrl = myURLid.replaceAll("IMDB", imdb).replaceAll("APIKEY", key);
        String request = getRequestInterface.sendGetRequest(requestUrl);
        Film result = conversionService.convert(request, Film.class);
        return result;
    }

    /**
     * Service for receive film by title.
     * @param title identification for search.
     * @return FilmList.
     */
    public FilmList getFilmByTitle(String title) throws UnsupportedEncodingException {
        title = URLEncoder.encode(title, "UTF-8");
        String requestUrl = myURL.replaceAll("TITLE", title).replaceAll("APIKEY", key);
        String request = getRequestInterface.sendGetRequest(requestUrl);
        return conversionService.convert(request, FilmList.class);
    }

    /**
     * Service for writing film.
     * @param film identification for write.
     * @return File.
     */
    public File writeFilmToDocByTemplate(Film film) throws IOException {
        File file = new File("src//main//resources//templates//result.docx");
        FileInputStream fis = new FileInputStream(file.getAbsolutePath());
        try (XWPFDocument document = new XWPFDocument(fis)) {
            for (XWPFParagraph paragraph : document.getParagraphs()) {
                for (XWPFRun run : paragraph.getRuns()) {
                    String text= run.getText(0);
                    text = text.replace("title", film.getTitle());
                    text = text.replace("directory", film.getDirectory());
                    text = text.replace("year", film.getYear());
                    text = text.replace("duration", film.getDuration());
                    text = text.replace("genre", film.getGenre());
                    text = text.replace("description", film.getDescription());
                    text = text.replace("posterLink", film.getPosterLink());
                    run.setText(text, 0);
                }
            }
            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            try {
                document.write(stream);
            } catch (IOException e) {
                logger.error("Error message", e);
                return null;
            }
            try(FileOutputStream outputStream = new FileOutputStream("src/main/resources/templates/"+film.getTitle()+".docx")){
            outputStream.write(stream.toByteArray());
            }
            File res = new File("src/main/resources/templates/"+film.getTitle()+".docx");
            return res;
        }

    }

    /**
     * Service for receive film by title in asynchronous way.
     * @param title identification for search.
     * @return CompletableFuture<FilmList>.
     * @throws InterruptedException
     */
    @Async("asyncExecutor")
    public CompletableFuture<FilmList> getFilmByTitleAsync(String title) throws InterruptedException
    {
        FilmList f = new FilmList();
        String film;
        RestTemplate restTemplate = new RestTemplate();
        logger.info("getFilmByTitle starts");
        String[] t = title.split(", ");
        for (String tit: t) {
            tit = tit.replace("\"", "");
            film = restTemplate.getForObject("http://localhost:8080/films?title=\"" + tit + "\"", String.class);
            logger.info("getForObject ends");
            f = conversionService.convert(film, f.getClass());
        }
        Thread.sleep(1000L);
        logger.info("getFilmById completed");
        return CompletableFuture.completedFuture(f);
    }

}
