Feature: Provider Listing
  As a system user
  I want to list providers
  So that I can view all available providers

  Background:
    Given the system is running
    And I have multiple providers in the system

  Scenario: List all providers successfully
    Given there are 3 providers in the system
    When I request to list all providers
    Then I should receive a list of 3 providers
    And the response status should be 200

  Scenario: List providers when no providers exist
    Given there are no providers in the system
    When I request to list all providers
    Then I should receive an empty list
    And the response status should be 200

  Scenario: List providers with pagination
    Given there are 10 providers in the system
    When I request to list providers with page 0 and size 5
    Then I should receive a paginated list with 5 providers
    And the response status should be 200

  Scenario: List providers with filtering by name
    Given there are providers with names containing "Test"
    When I request to list providers filtered by name "Test"
    Then I should receive a list of providers with names containing "Test"
    And the response status should be 200 