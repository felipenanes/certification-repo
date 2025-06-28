package com.nnsgroup.certification.cucumber.steps.providers;

import com.nnsgroup.certification.providers.domain.Provider;
import com.nnsgroup.certification.providers.repository.ProviderRepository;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ProviderSteps {
    
    @Autowired
    private TestRestTemplate restTemplate;
    
    @Autowired
    private ProviderRepository providerRepository;
    
    private Provider testProvider;
    private ResponseEntity<Provider> response;
    private UUID providerId;
    private String providerName;
    private String providerWebsite;

    //--------------- BACKGROUND(Given) ---------------//
    
    @Given("the system is running")
    public void theSystemIsRunning() {
        // System is already running due to @SpringBootTest
    }
    
    @Given("I have a valid provider with name {string} and website {string}")
    public void iHaveAValidProviderWithNameAndWebsite(String name, String website) {
        this.providerName = name;
        this.providerWebsite = website;
    }

    @Given("a provider exists with ID {string}")
    public void aProviderExistsWithId(String id) {
        this.providerId = UUID.fromString(id);
        testProvider = Provider.builder()
                .id(providerId)
                .name(providerName)
                .website(providerWebsite)
                .build();
        providerRepository.save(testProvider);
    }

    @Given("no provider exists with ID {string}")
    public void noProviderExistsWithId(String id) {
        this.providerId = UUID.fromString(id);
        // Ensure no provider exists with this ID
        providerRepository.deleteById(providerId);
    }

    @Given("I have provider data with name {string} and website {string}")
    public void iHaveProviderDataWithNameAndWebsite(String name, String website) {
        this.providerName = name;
        this.providerWebsite = website;
    }

    @Given("I have invalid provider data")
    public void iHaveInvalidProviderData() {
        this.providerName = ""; // Invalid empty name
        this.providerWebsite = "invalid-url"; // Invalid URL
    }

    @Given("a provider already exists with name {string}")
    public void aProviderAlreadyExistsWithName(String name) {
        Provider existingProvider = Provider.builder()
                .name(name)
                .website("https://existing.com")
                .build();
        providerRepository.save(existingProvider);
    }

    // Additional steps for validation scenarios
    @Given("I have provider data with null name and website {string}")
    public void iHaveProviderDataWithNullNameAndWebsite(String website) {
        this.providerName = null;
        this.providerWebsite = website;
    }

    @Given("I have provider data with empty name and website {string}")
    public void iHaveProviderDataWithEmptyNameAndWebsite(String website) {
        this.providerName = "";
        this.providerWebsite = website;
    }

    @Given("I have provider data with very long name and website {string}")
    public void iHaveProviderDataWithVeryLongNameAndWebsite(String website) {
        // Create a name longer than 100 characters (max length in entity)
        this.providerName = "A".repeat(101);
        this.providerWebsite = website;
    }

    @Given("I have provider data with name {string} and null website")
    public void iHaveProviderDataWithNameAndNullWebsite(String name) {
        this.providerName = name;
        this.providerWebsite = null;
    }

    // -------------- EXECUTION(When) ----------------
    
    @When("I request to get provider with ID {string}")
    public void iRequestToGetProviderWithId(String id) {
        this.providerId = UUID.fromString(id);
        try {
            response = restTemplate.getForEntity("/providers/" + providerId, Provider.class);
        } catch (Exception e) {
            // Handle 404 or other errors
            response = new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @When("I create a new provider")
    public void iCreateANewProvider() {
        Provider newProvider = Provider.builder()
                .name(providerName)
                .website(providerWebsite)
                .build();
        
        try {
            response = restTemplate.postForEntity("/providers", newProvider, Provider.class);
        } catch (Exception e) {
            // Handle validation errors
            response = new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    //--------------- ASSERTIONS(Then) ---------------
    
    @Then("I should receive a provider with name {string}")
    public void iShouldReceiveAProviderWithName(String expectedName) {
        assertNotNull(response);
        assertNotNull(response.getBody());
        assertEquals(expectedName, response.getBody().getName());
    }

    @Then("the response status should be {int}")
    public void theResponseStatusShouldBe(int expectedStatus) {
        assertNotNull(response);
        assertEquals(expectedStatus, response.getStatusCodeValue());
    }

    @Then("I should receive an error response")
    public void iShouldReceiveAnErrorResponse() {
        assertNotNull(response);
        assertTrue(response.getStatusCode().is4xxClientError());
    }

    @Then("I should receive a created provider with name {string}")
    public void iShouldReceiveACreatedProviderWithName(String expectedName) {
        assertNotNull(response);
        assertNotNull(response.getBody());
        assertEquals(expectedName, response.getBody().getName());
    }

    @Then("I should receive a validation error")
    public void iShouldReceiveAValidationError() {
        assertNotNull(response);
        assertTrue(response.getStatusCode().is4xxClientError());
    }

    @Then("I should receive a conflict error")
    public void iShouldReceiveAConflictError() {
        assertNotNull(response);
        assertEquals(HttpStatus.CONFLICT, response.getStatusCode());
    }

    //--------------- INCREMENTS(And) ---------------//

}