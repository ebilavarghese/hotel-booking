Feature: Hotel Booking

  Scenario: Make a hotel booking
    When a request is made to create a new booking
    Then the booking is done successfully

  Scenario: Change the booking made
    Given that a valid token is obtained
    When a request is made to update a booking
    Then the booking is updated successfully

  Scenario: Modify a hotel booking
    Given that a valid token is obtained
    When a request is made to partially update a booking
    Then the booking is partially updated successfully

  Scenario: Get all booking Ids
    When a request is made to get all booking Ids
    Then the booking Ids are returned successfully

  Scenario: Cancel a hotel booking
    Given that a valid token is obtained
    When a request is made to delete a booking
    Then the booking is deleted successfully