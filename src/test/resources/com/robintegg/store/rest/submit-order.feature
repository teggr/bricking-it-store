@stage-1
Feature: Submit order
  As a Rest Client
  I want to submit new orders for bricks
  So I can start customers’ orders

  Scenario: Submit new order
    Given A customer wants to buy any number of bricks
    When A create Order request for a number of bricks is submitted
    Then an Order reference is returned
		And the Order reference is unique to the submission
