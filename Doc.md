# Design Decisions for Assignment
## Business Logic
    - Used map for updates in services to handle update requests that come in with some fields
    - Created a custom response handler to allow me tweak what response to return to client
    - Added One-to-Many relationhip between user and alert respectively to track users that created alerts
    - Added One-to-One relationhip between currency and alert respectively to track currency alerts was created for
    

## Clean Code
    - Used facades as a point of tranformation of requests to and from 
        responses this allowed my controllers and services to look cleaner and readeable

## Error Handling
    - Used custom exceptions I created help with error tracing
    - Added validation constraints to allow springboot to manage them

## Database
    - Run database migrations with flyway to track changes to my tables in the database

## Security
    - Used custom authentication and authorization to config jwt and handle authorization rather than using 
        default authentication and authorization