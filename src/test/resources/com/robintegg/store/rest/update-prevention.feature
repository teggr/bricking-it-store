@stage-4
Feature: Update prevention
  As a Rest Client
  I want the prevent updates to an order, when that order has been dispatched
  So I don't accept updates to orders that have already shipped

  Scenario: Dispatched order 
    Given an order exists
	And that order has been dispatched
    When an Update Order request is submitted for a valid Order reference
    Then a 400 bad request response is returned