# Currency Alerts Application
A kotlin springboot application with jwt authentication that allows users to create currencies and create alerts for a traget price of the currency

## Scenario
BayzTracker is a cryptocurrency tracker app which allows its users to create alerts to be notified when a price of a coin reaches the price user determines.

User can create multiple alerts and can track the alert status anytime (triggered or waiting).

There is also currency list page where all coins with their current prices are listed.

The admin user also manages the currencies that will be listed on the app.

## General Application Constraints
- There are two types of users: Admin and User.
    - Both user types can create alerts.
    - Both users can query currencies.
    - Only admin can manage the currency types in the system.
    - `UnsupportedCurrencyCreationException` should be thrown Unsupported coins: [ ETH, LTC, ZKN, MRD, LPR, GBZ ]
    - Admin user can add/remove currencies
    - All users can query currencies
