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
import java.io.*;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;
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

    public Film getFilmById(String imdb){
        String requestUrl = myURLid.replaceAll("IMDB", imdb).replaceAll("APIKEY", key);
        String request = getRequestInterface.sendGetRequest(requestUrl);
        Film result = conversionService.convert(request, Film.class);
        return result;
    }

    public List<Film> getFilmByTitle(String title) throws UnsupportedEncodingException {
        List<Film> films = new ArrayList<>();
        title = URLEncoder.encode(title, "UTF-8");
        String requestUrl = myURL.replaceAll("TITLE", title).replaceAll("APIKEY", key);
        String request = getRequestInterface.sendGetRequest(requestUrl);
        films = conversionService.convert(request, films.getClass());
        return films;
    }

    public byte[] writeFilmToDocByTemplate(Film film) throws IOException, InterruptedException {
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
            return stream.toByteArray();
        }

    }

    @Async("asyncExecutor")
    public CompletableFuture<List> getFilmByTitleAsync(String title) throws InterruptedException
    {
        List f = new ArrayList<Film>();
        String films;
        RestTemplate restTemplate = new RestTemplate();
        logger.info("getFilmByTitle starts");
        String[] t = title.split(", ");
        for (String tit: t) {
            tit = tit.replace("\"", "");
            films = restTemplate.getForObject("http://localhost:8080/films?title=\"" + tit + "\"", String.class);
            logger.info("getForObject ends");
            f.add(conversionService.convert(films, f.getClass()));
        }
        Thread.sleep(1000L);
        logger.info("getFilmById completed");
        return CompletableFuture.completedFuture(f);
    }


}

