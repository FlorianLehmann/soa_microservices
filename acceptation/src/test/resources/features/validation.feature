Feature: validation of an order

  Scenario: Choose a menu and ask for an ETA

    Given a client named Arthur at Biot
      And a ramen soup
    When he ask for an ETA
    Then he get an ETA of 45 min


  Scenario: Choose and validate a menu

    Given a client named Arthur at Biot
      And a ramen soup
    When he validate his product
    Then the order is send to the restaurant
      And a deliveryman is assign for the task