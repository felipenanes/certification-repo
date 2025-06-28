package com.nnsgroup.certification.integration;

import org.springframework.http.*;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

public class TokenHelper {

    private final int port;
    private final RestTemplate restTemplate;

    public TokenHelper(int port) {
        this.port = port;
        this.restTemplate = new RestTemplate();
    }

    public String getAccessToken() {
        HttpHeaders headers = new HttpHeaders();
        headers.setBasicAuth("certification-app", "secret");
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        MultiValueMap<String, String> body = new LinkedMultiValueMap<>();
        body.add("grant_type", "client_credentials");
        body.add("scope", "read write");

        HttpEntity<?> request = new HttpEntity<>(body, headers);

        ResponseEntity<Map> response = restTemplate.postForEntity(
                "http://localhost:" + port + "/oauth2/token",
                request,
                Map.class
        );

        assert response.getBody() != null;
        return (String) response.getBody().get("access_token");
    }
}
