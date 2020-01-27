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

to be completed
