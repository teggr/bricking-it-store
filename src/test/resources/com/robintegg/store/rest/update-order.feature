@stage-2
Feature: Update order
  As a Rest Client
  I want to update orders for bricks
  So I can update customers’ orders

  Scenario: Existing order
    Given a customer has ordered a number of bricks
    When an Update Order request for an existing order reference and a number of bricks is submitted
    Then an Order reference the returned
	  And the Order reference is unique to the submission
