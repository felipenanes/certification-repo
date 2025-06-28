package com.nnsgroup.certification.providers.web;

import com.nnsgroup.certification.integration.TokenHelper;
import com.nnsgroup.certification.providers.domain.Provider;
import com.nnsgroup.certification.providers.service.ProviderService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.*;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.Map;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@ActiveProfiles("test")
class ProviderControllerIntegrationTest {

    @LocalServerPort
    private int port;

    @Autowired
    private ProviderService providerService;

    private final RestTemplate restTemplate = new RestTemplate();

    private String getAccessToken() {
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

        assertNotNull(response.getBody());
        return (String) response.getBody().get("access_token");
    }

    @Test
    void testCreateProvider_authenticated() {
        TokenHelper tokenHelper = new TokenHelper(port);
        String token = tokenHelper.getAccessToken();

        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(token);
        headers.setContentType(MediaType.APPLICATION_JSON);

        Provider provider = Provider.builder()
                .name("Integration Test Provider")
                .website("https://integration-test.com")
                .build();

        HttpEntity<Provider> request = new HttpEntity<>(provider, headers);

        ResponseEntity<Provider> response = restTemplate.postForEntity(
                "http://localhost:" + port + "/providers",
                request,
                Provider.class
        );

        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    void testGetProvider_Success() {
        String token = getAccessToken();

        // First create a provider
        Provider createdProvider = providerService.create(Provider.builder()
                .name("Get Test Provider")
                .website("https://get-test.com")
                .build());

        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(token);

        HttpEntity<Void> entity = new HttpEntity<>(headers);

        ResponseEntity<Provider> response = restTemplate.exchange(
                "http://localhost:" + port + "/providers/" + createdProvider.getId(),
                HttpMethod.GET,
                entity,
                Provider.class
        );

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(createdProvider.getId(), response.getBody().getId());
        assertEquals("Get Test Provider", response.getBody().getName());
    }

    @Test
    void testGetProvider_NotFound() {
        String token = getAccessToken();
        UUID nonExistentId = UUID.randomUUID();

        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(token);
        HttpEntity<Void> entity = new HttpEntity<>(headers);

        try {
            restTemplate.exchange(
                    "http://localhost:" + port + "/providers/" + nonExistentId,
                    HttpMethod.GET,
                    entity,
                    String.class
            );
            fail("Expected HttpClientErrorException.NotFound");
        } catch (HttpClientErrorException.NotFound ex) {
            assertEquals(HttpStatus.NOT_FOUND, ex.getStatusCode());
            assertTrue(ex.getResponseBodyAsString().contains("Provider not found"));
        }
    }
}
