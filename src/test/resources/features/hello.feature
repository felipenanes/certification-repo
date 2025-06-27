Feature: Hello Cucumber

  Scenario: Saying hello
    Given the system is running
    When I say hello
    Then I should see "Hello, Cucumber!"