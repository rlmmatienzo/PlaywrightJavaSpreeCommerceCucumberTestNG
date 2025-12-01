Feature: User sign up and orders an itemy

  As a registered user
  I want to be able to log into the application
  So that I can access my personalized content

@UserRegistration
  Scenario: User signs up for the first time 
    Given User launches the website
    When User signs up for the first time
    |Username|
    |globetest|
    Then User signed up successfully

@Test1
  Scenario: User orders an item without size
    Given User launches the website
    When User logs in using default credentials
    Then User selects products "Sale" tab
    Then User orders an item and adds to the cart
  		|Item|Size|Quantity|Price|CheckoutType|
    	|Rose-Tinted Sunglasses|NA|3|54.99|Checkout|
    Then User inputs contact information and shipping address
    	|Country|FirstName|LastName|StreetAndHouseNo|Apartment|City|State|PostalCode|Phone|
   	    |United States|Global|Tester|123 William Street|Condominium|New York|New York|10038|1234567890|
    Then User inputs payment information and places the order
    	|CreditCardNo|ExpiryDate|CVV|Email|mobileNumber|fullName|
		|default|10/31|155|perf@tester.com|09123456789|Globe Tester|
    And User validates the order confirmation of "Global"
    
  @Test2
  Scenario: User orders a item with size
    Given User launches the website
    When User logs in using default credentials
    Then User selects products "Sale" tab
    Then User orders an item and adds to the cart
  		|Item|Size|Quantity|Price|CheckoutType|
    	|Striped Crop Sweatshirt|M|1|45.99|Checkout|
    Then User inputs contact information and shipping address
    	|Country|FirstName|LastName|StreetAndHouseNo|Apartment|City|State|PostalCode|Phone|
    	|United States|Global|Tester|123 William Street|Condominium|New York|New York|10038|1234567890|
    Then User inputs payment information and places the order
    	|CreditCardNo|ExpiryDate|CVV|Email|mobileNumber|fullName|
	|default|10/31|155|perf@tester.com|09123456789|Globe Tester|
    And User validates the order confirmation of "Global"