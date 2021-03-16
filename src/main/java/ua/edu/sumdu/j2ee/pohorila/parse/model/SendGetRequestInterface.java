package ua.edu.sumdu.j2ee.pohorila.parse.model;

import org.springframework.stereotype.Component;

@Component
public interface SendGetRequestInterface {
    public String sendGetRequest(String requestUrl);
}
