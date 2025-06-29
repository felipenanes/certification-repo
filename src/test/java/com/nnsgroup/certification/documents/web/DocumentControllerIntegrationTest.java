package com.nnsgroup.certification.documents.web;

import com.nnsgroup.certification.documents.domain.Document;
import com.nnsgroup.certification.documents.service.DocumentService;
import com.nnsgroup.certification.integration.TokenHelper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.Map;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@ActiveProfiles("test")
class DocumentControllerIntegrationTest {

    @LocalServerPort
    private int port;

    @Autowired
    private DocumentService documentService;

    private final RestTemplate restTemplate = new RestTemplate();

    Document document = Document.builder()
            .name("AWS Certified Cloud Practitioner")
            .fileUrl("https://cp.certmetrics.com/amazon/en/public/verify/credential/7P8J7F7CVFBQQWG3")
            .build();

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
    void testCreateDocument_authenticated() {
        TokenHelper tokenHelper = new TokenHelper(port);
        String token = tokenHelper.getAccessToken();

        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(token);
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<Document> request = new HttpEntity<>(document, headers);

        ResponseEntity<Document> response = restTemplate.postForEntity(
                "http://localhost:" + port + "/documents",
                request,
                Document.class
        );

        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    void testGetDocument_Success() {
        String token = getAccessToken();

        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(token);

        HttpEntity<Void> entity = new HttpEntity<>(headers);

        // First create a provider
        Document createdProvider = documentService.create(Document.builder()
                .name("AWS Certified Cloud Practitioner")
                .fileUrl("https://cp.certmetrics.com/amazon/en/public/verify/credential/7P8J7F7CVFBQQWG3")
                .build());

        ResponseEntity<Document> response = restTemplate.exchange(
                "http://localhost:" + port + "/documents/" + createdProvider.getId(),
                HttpMethod.GET,
                entity,
                Document.class
        );

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(createdProvider.getId(), response.getBody().getId());
        assertEquals("AWS Certified Cloud Practitioner", response.getBody().getName());
    }

    @Test
    void testGetDocument_NotFound() {
        String token = getAccessToken();
        UUID nonExistentId = UUID.randomUUID();

        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(token);
        HttpEntity<Void> entity = new HttpEntity<>(headers);

        try {
            restTemplate.exchange(
                    "http://localhost:" + port + "/documents/" + nonExistentId,
                    HttpMethod.GET,
                    entity,
                    String.class
            );
            fail("Expected HttpClientErrorException.NotFound");
        } catch (HttpClientErrorException.NotFound ex) {
            assertEquals(HttpStatus.NOT_FOUND, ex.getStatusCode());
            assertTrue(ex.getResponseBodyAsString().contains("Document not found"));
        }
    }
}
