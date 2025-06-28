Feature: Provider Management
  As a system user
  I want to manage providers
  So that I can maintain provider information

  Background:
    Given the system is running
    And I have a valid provider with name "Test Provider" and website "https://test.com"

  Scenario: Get provider by ID successfully
    Given a provider exists with ID "550e8400-e29b-41d4-a716-446655440000"
    When I request to get provider with ID "550e8400-e29b-41d4-a716-446655440000"
    Then I should receive a provider with name "Test Provider"
    And the response status should be 200

  Scenario: Get provider by ID when provider does not exist
    Given no provider exists with ID "550e8400-e29b-41d4-a716-446655440001"
    When I request to get provider with ID "550e8400-e29b-41d4-a716-446655440001"
    Then I should receive an error response
    And the response status should be 404

  Scenario: Create provider successfully
    Given I have provider data with name "New Provider" and website "https://newprovider.com"
    When I create a new provider
    Then I should receive a created provider with name "New Provider"
    And the response status should be 201

  Scenario: Create provider with invalid data
    Given I have invalid provider data
    When I create a new provider
    Then I should receive a validation error
    And the response status should be 400

  Scenario: Create provider with duplicate name
    Given a provider already exists with name "Existing Provider"
    And I have provider data with name "Existing Provider" and website "https://duplicate.com"
    When I create a new provider
    Then I should receive a conflict error
    And the response status should be 409 