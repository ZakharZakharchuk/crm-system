Feature: Trainee Controller Tests

  Scenario: Update Trainee
    Given a Trainee with ID
    When I update the Trainee information
    Then the Trainee information should be updated successfully