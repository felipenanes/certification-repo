Feature: Provider Validation
  As a system user
  I want to validate provider data
  So that I can ensure data integrity

  Background:
    Given the system is running

  Scenario: Create provider with null name
    Given I have provider data with null name and website "https://test.com"
    When I create a new provider
    Then I should receive a validation error
    And the response status should be 400

  Scenario: Create provider with empty name
    Given I have provider data with empty name and website "https://test.com"
    When I create a new provider
    Then I should receive a validation error
    And the response status should be 400

  Scenario: Create provider with name exceeding maximum length
    Given I have provider data with very long name and website "https://test.com"
    When I create a new provider
    Then I should receive a validation error
    And the response status should be 400

  Scenario: Create provider with valid data but null website
    Given I have provider data with name "Valid Provider" and null website
    When I create a new provider
    Then I should receive a created provider with name "Valid Provider"
    And the response status should be 201

  Scenario: Create provider with special characters in name
    Given I have provider data with name "Provider & Co. (Ltd.)" and website "https://test.com"
    When I create a new provider
    Then I should receive a created provider with name "Provider & Co. (Ltd.)"
    And the response status should be 201

  Scenario: Create provider with international characters in name
    Given I have provider data with name "Proveedor Español" and website "https://test.com"
    When I create a new provider
    Then I should receive a created provider with name "Proveedor Español"
    And the response status should be 201 