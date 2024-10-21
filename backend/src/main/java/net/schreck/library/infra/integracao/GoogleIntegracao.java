package net.schreck.library.infra.integracao;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import static java.util.Objects.requireNonNull;

@Service
@RequiredArgsConstructor
public class GoogleIntegracao {

    @Value("${google.apikey}")
    private String googleKey;

    @Value("${google.url}")
    private String url;

    private final RestTemplate restTemplate;

    public String googleAPIget(String request) {

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(org.springframework.http.MediaType.APPLICATION_JSON);

        String urlCompleta = "https://www.googleapis.com/books/v1/volumes?q=" + request + "&key=" + googleKey;

        HttpEntity<String> requestEntity = new HttpEntity<>(request, headers);

        ResponseEntity<String> response = restTemplate.exchange(urlCompleta, HttpMethod.GET, requestEntity, String.class);

        return response.getBody();
    }
}
