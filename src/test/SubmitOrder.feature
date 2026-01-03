Feature: Submit order flow

  Scenario: Successful order submission
    Given the user is logged in with "ceren1481@gmail.com" and "1234"
    When the user adds "ZARA COAT 3" to the cart
    And the user goes to the cart page
    Then the cart should contain "ZARA COAT 3"
    When the user proceeds to checkout
    Then payment methods should be displayed
    When the user selects country by typing "ind" and choosing "India"
    And the user submits the order
    Then the confirmation text should be "THANKYOU FOR THE ORDER."
