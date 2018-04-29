@stage-3
Feature: Order fulfilment
  As a Rest Client
  I want to note when orders have been dispatched
  So I can manage when orders are fulfilled

  Scenario: Valid order
    Given an order exists
    When a Fulfil Order request is submitted for a valid Order reference
    Then the Order is marked as dispatched

  Scenario: Invalid order
    Given an order exists
    When a Fulfil Order request is submitted for a invalid Order reference
    Then a 400 bad request response is returned