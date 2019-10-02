# Newsletter Subscription Service

A Restful API which allows users to perform various subscription services. It uses H2 as the in-memory DB. Hence the tests are portable for this application.

## Technology stack

Spring Boot (2.1.8) and Spring MVC have been used to develop and expose the Rest endpoints. H2 is used as the DB (in-memory).

## Requirements

1. Java - 1.8.x

2. Maven - 3.x.x

## Steps to Setup

**1. Clone the application**

```bash
git clone 
```

**2. Build and run the app**

```bash
mvn spring-boot:run
```

The app will start running at <http://localhost:8080>

## API Details

| HTTP Method | URI	| Description |
| ------------- | ------------- | ------------------------ |
|GET | /subscribers | Returns current list of newsletter subscribers |
|GET | /issubscribed/{emailId} | Informs if a given emailId is subscribed to newsletter service or not |
|POST | /subscribe | Subscribes one emailId to newsletter service. It expects the emailId as input in givenm format {"subscriptionID": "{emailId}"} |
|DELETE | /unsubscribe/{emailId} | Unsubscribes one user from newsletter service. |
|GET | /subscribers/{operator}/{date} | Retuens the list of users who have subscribed to the newsletter service before or after certain date. {operation} can be "before"/"after". The Date should be in "dd-mm-yyyy" format. |

```bash
Note: The APIs can be tested using postman or any other rest client. A sample request JSON (Newsletter.postman_collection.json) is available in the repo for ready reference which can directly be imported to postman.
```

### /subscribers
```
GET http://localhost:8080/subscribers

Response: HTTP 200
Content: application/json
```

### /issubscribed/{emailId}
```
GET http://localhost:8080/issubscribed/soumya.hazz@gmail.com

Response: HTTP 200 [404 in case of not found]
Content: boolean
```

### /subscribe
```
POST http://localhost:8080/subscribe
accept: application/json
Content-Type: application/json

{
"subscriptionID": "test.email@gmail.com"
}

Response: HTTP 201 (Created) [404 in case of error]
Content: application/json
```

### /unsubscribe/{emailId}
```
DELETE http://localhost:8080/unsubscribe/test.email@gmail.com

Response: HTTP 200 [404 in case the enmail id not already subscribed]
Content: application/json
```

### /subscribers/{operator}/{date}
```
GET http://localhost:8080/subscribers/before/10-01-2019 / GET http://localhost:8080/subscribers/after/10-01-2019

Response: 200
Content: application/json
```

## Test strategy

```
How to Run: mvn clean install
```

**1. Unit testing**
The controller functionalities are tested using @MockBean and MockMvc approach.

**2. Integration Testing**
Integration test has been done using the native features of Spring Boot.


