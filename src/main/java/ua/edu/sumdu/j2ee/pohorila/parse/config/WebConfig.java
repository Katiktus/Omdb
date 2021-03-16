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
import ua.edu.sumdu.j2ee.pohorila.parse.model.services.FilmServices;
import ua.edu.sumdu.j2ee.pohorila.parse.model.services.ServicesInterface;

import java.util.concurrent.Executor;

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

    @Override
    public void addFormatters(FormatterRegistry registry) {
        registry.addConverter(new StringToFilmConverter());
        registry.addConverter(new StringToFilmListConverter());
    }

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

    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }

    /*
    * <beans xmlns="http://www.springframework.org/schema/beans"
           xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
           xsi:schemaLocation="http://www.springframework.org/schema/beans
           http://www.springframework.org/schema/beans/spring-beans-3.0.xsd">
        <bean id="services" class="ua.edu.sumdu.j2ee.pohorila.model.services.Services">
            <property name="services"></property>
            <constructor-arg ref="getFilmByIdAsync"/>
        </bean>
        <bean id="conversionService"
              class="org.springframework.context.support.ConversionServiceFactoryBean">
            <property name="converters">
                <list merge="true">
                    <bean id="StringToFilmConverter"
                          class="ua.edu.sumdu.j2ee.pohorila.parse.model.converters.StringToFilmConverter"/>
                    <bean id="StringToFilmListConverter"
                          class="ua.edu.sumdu.j2ee.pohorila.parse.model.converters.StringToFilmListConverter"/>
                </list>
            </property>
        </bean>
    </beans>
    * */
}
