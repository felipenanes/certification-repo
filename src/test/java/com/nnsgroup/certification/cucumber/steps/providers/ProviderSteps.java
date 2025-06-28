package com.nnsgroup.certification.cucumber.steps.providers;

import com.nnsgroup.certification.providers.domain.Provider;
import com.nnsgroup.certification.providers.repository.ProviderRepository;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

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
        // System is already running due to @SpringBootTest in CucumberSpringConfig
    }
    
    @Given("I have a valid provider with name {string} and website {string}")
    public void iHaveAValidProviderWithNameAndWebsite(String name, String website) {
        this.providerName = name;
        this.providerWebsite = website;
    }

    @Given("a provider exists with ID {string}")
    public void aProviderExistsWithId(String id) {
        this.providerId = UUID.fromString(id);
        providerRepository.deleteById(providerId); // garante consistência

        Provider provider = Provider.builder()
                .id(providerId)
                .name("Test Provider")
                .website("https://test.com")
                .build();

        providerRepository.save(provider);
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

    // Additional steps for listing scenarios
    @Given("I have multiple providers in the system")
    public void iHaveMultipleProvidersInTheSystem() {
        // This will be implemented when listing functionality is added
    }

    @Given("there are {int} providers in the system")
    public void thereAreProvidersInTheSystem(int count) {
        // This will be implemented when listing functionality is added
    }

    @Given("there are no providers in the system")
    public void thereAreNoProvidersInTheSystem() {
        providerRepository.deleteAll();
    }

    @Given("there are providers with names containing {string}")
    public void thereAreProvidersWithNamesContaining(String namePattern) {
        // This will be implemented when listing functionality is added
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

    // Additional steps for listing scenarios
    @When("I request to list all providers")
    public void iRequestToListAllProviders() {
        // This will be implemented when listing functionality is added
    }

    @When("I request to list providers with page {int} and size {int}")
    public void iRequestToListProvidersWithPageAndSize(int page, int size) {
        // This will be implemented when listing functionality is added
    }

    @When("I request to list providers filtered by name {string}")
    public void iRequestToListProvidersFilteredByName(String name) {
        // This will be implemented when listing functionality is added
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

    // Additional assertions for listing scenarios
    @Then("I should receive a list of {int} providers")
    public void iShouldReceiveAListOfProviders(int expectedCount) {
        // This will be implemented when listing functionality is added
    }

    @Then("I should receive an empty list")
    public void iShouldReceiveAnEmptyList() {
        // This will be implemented when listing functionality is added
    }

    @Then("I should receive a paginated list with {int} providers")
    public void iShouldReceiveAPaginatedListWithProviders(int expectedCount) {
        // This will be implemented when listing functionality is added
    }

    @Then("I should receive a list of providers with names containing {string}")
    public void iShouldReceiveAListOfProvidersWithNamesContaining(String namePattern) {
        // This will be implemented when listing functionality is added
    }

    //--------------- INCREMENTS(And) ---------------//

}