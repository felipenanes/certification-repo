package com.nnsgroup.certification.cucumber.steps;

import io.cucumber.java.en.*;
import static org.junit.jupiter.api.Assertions.*;

public class HelloSteps {

    private String output;

    @Given("the system is running")
    public void systemIsRunning() {
        // Any setup logic
    }

    @When("I say hello")
    public void iSayHello() {
        output = "Hello, Cucumber!";
    }

    @Then("I should see {string}")
    public void iShouldSee(String expected) {
        assertEquals(expected, output);
    }
}