# mail-service

This app serves as an email integration app, with a basic fail over between a primary and secondary provider. 

It is available on https://siteminder-mail.herokuapp.com/mail
      

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

To protect your keys, set them as system variables rather than directly into application.yml.

To Build: 
In the root of the project, do:  `mvn clean install`

To Run: `mvn spring-boot:run`

## Send an email
The app is currently deployed to heroku and available on:
`https://siteminder-mail.herokuapp.com/`

As Heroku may have cold start times, the first request may be slow :)

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

**On Success:**

* Returns a `200 OK` Header and no Body. For this particular case, a body tends to add noise.

**On Failure:**

* Returns a `400 Bad Request` with error messages in the body if the input fails to meet the validation criteria.
An example is shown below for when all the mandatory fields are missing in the request:

```json
{
  "name": "ValidationError",
  "details": [
    {
      "location": "params",
      "param": "message",
      "msg": "'message' cannot be blank or null",
      "value": "null"
    },
    {
      "location": "params",
      "param": "to",
      "msg": "'to' field cannot be empty or null",
      "value": "null"
    },
    {
      "location": "params",
      "param": "subject",
      "msg": "'subject' cannot be blank or null",
      "value": "null"
    },
    {
      "location": "params",
      "param": "from",
      "msg": "'from' cannot be blank or null",
      "value": "null"
    }
  ]
}
```

* Returns a `502 Bad Gateway` with an error body if the app can't successfully send the email to either provider.
An example is shown below:

```json
{
  "msg": "Our services are temporarily down. Please try again later"
}
```

* May return a `500 Internal Server Error` in anomalous error scenarios. 


## TODOs for Production

**User Experience**

1. Implement user login and auth

2. Implement a tracking mechanism (such as using a messageId) so the user can see if their message was successful or rejected. The 200 OK header response doesn't account for bounced back messages.

**Code Design:**

1. Add documentation using OpenAPI or Spring REST docs for integrators

2. Add more detailed logging through the code and specify a particular format which can be consumed by a log aggregator

3. Improve validation error messages for more cases and also the consistency. For example, if a user Posts without a body or a field of the incorrect type, it currently gives a
Json Deserialization error which is difficult to read. Another example is where it has 'to' or 'to[]' as the param depending on the error. This requires consistency

4. The current Failover mechanism is very rudimentary. When there is any exception by the first gateway, it will retry with the second gateway
    
   For a Production system of high loads, I would take an asynchronous approach where the user would hit the POST /mail endpoint to send the mail. It will give the user an messageId. 
   The user will then hit a GET /mail/status endpoint with the messageId to get the status (to see if it has been sent). 
   
   The mails would be forwarded to a queue, and from there be sent to the gateways. If one gateway is down, it will attempt to use the other gateway. If both gateways are down, the message will remain in the queue. Beyond the scope of this problem, we can also use a bounce queue,
   for if a message bounced back from the provider.
        
5. Authentication / Authorization would also be implemented in a Production system

6. The Client integration tests are currently sending mails to the live service providers. To improve this, we would use Hoverfly or an equivalent HTTP simulation tool which integrates to JUnit

7. Have an acceptance testing and load testing suite

8. Have support for attachments and Multimedia files 

**DevOps Concerns:**

1. The app currently runs in a single container on Heroku. For Production, it would be run on at least 2 containers and behind a load balancer

2. Use diagnostic and telemetry tools to monitor the app and its health. A very simple example would be to activate parts of the spring boot ``` /actuator ``` and Spring Boot Admin

3. Use a log aggregation and viewing mechanism such as ELK or Splunk, to monitor usage and any errors, and provide alerts 

**SecOps Concerns:**

1. In Production, use a Vault / Secrets Management System to store encrypted keys instead of storing them as system variables 

2. Configure security mechanisms to prevent, detect and respond to malicious activity. This may include but not limited to rate limiting users, detecting and preventing spammers or bots, and spike arrests
