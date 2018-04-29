@stage-1
Feature: Retrieve orders
  As a Rest Client
  I want to retrieve orders
  So I can display simple customers orders

  Scenario: Retrieve a unique order
    Given a customer has submitted an order for some bricks
    When a Get Order request is submitted with a valid Order reference
    Then the order details are returned
		And the order details contains the Order reference and the number of bricks ordered

  Scenario: Retrieve non-existent order
  	Given a customer has submitted an order for some bricks
    When a Get Order request is submitted with an invalid Order reference
    Then no order details are returned

  Scenario: Retrieve all orders
    Given many customers have submitted orders for bricks
    When a Get Orders request is submitted
    Then all the orders details are returned
		And each order details contains the Order reference and the number of bricks ordered
