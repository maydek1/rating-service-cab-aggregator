Feature: Rating Service

  Scenario: Create a rating successfully
    Given a ride with ID 2 exists
    When I create a rating with rate 5 for ride ID 2
    Then the rating should be created with rate 5 and ride ID 2

  Scenario: Fail to create a rating for a non-existing ride
    Given a ride with ID 999 does not exist
    When I try to create a rating with rate 5 for ride ID 999
    Then I should receive a RideNotFoundException for ride ID 999

  Scenario: Get rating by ID successfully
    Given a rating with ID 1 exists with rate 5 and ride ID 2
    When I request the rating by ID 1
    Then I should receive the rating with rate 5 and ride ID 2

  Scenario: Update rating successfully
    Given a rating with ID 1 exists with rate 5 and ride ID 2
    When I update the rating with ID 1 to rate 4
    Then the rating should be updated to rate 4

  Scenario: Delete rating successfully
    Given a rating with ID 1 exists with rate 5 and ride ID 2
    When I delete the rating with ID 1
    Then the rating should be deleted successfully
