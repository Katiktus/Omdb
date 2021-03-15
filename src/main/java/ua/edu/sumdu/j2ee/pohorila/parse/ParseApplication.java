package ua.edu.sumdu.j2ee.pohorila.parse;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;


@SpringBootApplication
@EnableAsync
public class ParseApplication {
    public static void main(String[] args) throws Exception {
        SpringApplication.run(ParseApplication.class, args);
    }
}
