package ua.edu.sumdu.j2ee.pohorila.parse.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.format.FormatterRegistry;
import org.springframework.http.MediaType;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.config.annotation.ContentNegotiationConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import ua.edu.sumdu.j2ee.pohorila.parse.model.converters.StringToFilmConverter;
import ua.edu.sumdu.j2ee.pohorila.parse.model.converters.StringToFilmListConverter;

import java.util.concurrent.Executor;

/**
 * Config class.
 */

@Configuration
@EnableAsync
public class WebConfig extends WebMvcConfigurerAdapter {
    @Override
    public void configureContentNegotiation(ContentNegotiationConfigurer configurer) {
        configurer.favorPathExtension(false).
                favorParameter(true).
                defaultContentType(MediaType.APPLICATION_JSON).
                mediaType("xml", MediaType.APPLICATION_XML);
    }

    /**
     * Converters registration.
     */
    @Override
    public void addFormatters(FormatterRegistry registry) {
        registry.addConverter(new StringToFilmListConverter());
        registry.addConverter(new StringToFilmConverter());
    }

    /**
     * Bean for async executor.
     * @return Executor.
     */
    @Bean
    public Executor asyncExecutor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(2);
        executor.setMaxPoolSize(2);
        executor.setQueueCapacity(500);
        executor.setThreadNamePrefix("AsynchThread-");
        executor.initialize();
        return executor;
    }

    /**
     * Bean for rest template.
     * @return RestTemplate.
     */
    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }

}
