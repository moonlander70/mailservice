# mail-service

This app is currently under construction and will be ready soon. It will serve as an email integration app, with rudimentary fail over.  

## Tech Stack:

1. Java 11
2. Spring Boot 2.2.3
3. Maven 3.6
4. Heroku

## Third Party Mail Services:

1. SendGrid
2. SendInBlue


## Build and Run

You will need Java 11 and Maven 3 installed.

You will also need to set SendGrid and SendInBlue API Keys as system variables. 
These are referenced in the application.yml

To protect your keys, be sure to not paste them directly into application.yml.

To Build: 
In the root of the project, do:  `mvn clean install`

To Run: `mvn spring-boot:run`

## Send an email
The app is currently deployed to heroku and available on:
`https://siteminder-mail.herokuapp.com/`

As Heroku has cold start times, the first request may be slow :)

Make a http POST request to `/mail`. 

For example: `https://siteminder-mail.herokuapp.com/mail`

The endpoint consumes a JSON object in the body.

### Request body
* **to**: *required* Set of email addresses to send the email to
* **cc** *optional* Set of email addresses to send the email in cc to
* **bcc** *optional* Set of email addresses to send the email in bcc (blind carbon copy) to
* **from** *required* Email address of the sender. Replies will be directed to this address.
* **subject** *required* The email subject line
* **message** *required* The message in plain text

Example:

```json
{
  "to": ["test@test.com", "second@test.com"],
  "cc": ["cc@test.com"],
  "from": "sender@sender.com",
  "subject": "Hello World",
  "message": "Hi, How are you today?"
}
```

### Response

to be completed

## TODOs for Production

Code Design:
1. Add documentation using OpenAPI or Spring REST docs for integrators

2. Add more detailed logging through the code and specify a particular format which can be consumed by a log aggregator

3. Improve validation error messages for more cases. For example, if a user Posts without a body or a field of the incorrect type, it currently gives a
Json Deserialization error which is difficult to read.   

4. The current Failover mechanism is very rudimentary. When there is any exception by the first gateway, it will retry with the second gateway. 
    
   For a Production system of high loads, we would take an asynchronous approach where the user would hit the POST /mail endpoint to send the mail. It will give the user an messageId. 
   The user will then hit a GET /mail/status endpoint with the messageId to see if it has been sent. The mails would be forwarded to a queue, and from there be send to the gateways. 
   
   If one gateway is down, it will attempt to use the other gateway. If both gateways are down, the message will remain in the queue. Beyond the scope of this problem, we can also use a bounce queue,
   for if a message bounced back from the provider.
        
5. Authentication / Authorization would also be implemented in a Production system

6. The integration tests to the Clients are currently sending mails to the live clients. To improve this, we would use Hoverfly or an equivalent HTTP simulation tool which integrates to JUnit

DevOps:



SecOps:
