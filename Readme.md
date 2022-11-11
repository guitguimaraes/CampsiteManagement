# CampsiteManagement

### Chalange:

An underwater volcano formed a new small island in the Pacific Ocean last month. All the conditions on the island seems perfect and it was
decided to open it up for the general public to experience the pristine uncharted territory.
The island is big enough to host a single campsite so everybody is very excited to visit. In order to regulate the number of people on the island, it
was decided to come up with an online web application to manage the reservations. You are responsible for design and development of a REST
API service that will manage the campsite reservations. To streamline the reservations a few constraints need to be in place:

● The campsite will be free for all.

● The campsite can be reserved for max 3 days.

● The campsite can be reserved minimum 1 day(s) ahead of arrival and up to 1 month in advance.

● Reservations can be cancelled anytime.

● For sake of simplicity assume the check-in & check-out time is 12:00 AM

### System Requirements:

● The users will need to find out when the campsite is available. So the system should expose an API to provide information of the
availability of the campsite for a given date range with the default being 1 month.

● Provide an end point for reserving the campsite. The user will provide his/her email & full name at the time of reserving the campsite
along with intended arrival date and departure date. Return a unique booking identifier back to the caller if the reservation is successful.

● The unique booking identifier can be used to modify or cancel the reservation later on. Provide appropriate end point(s) to allow
modification/cancellation of an existing reservation

● Due to the popularity of the island, there is a high likelihood of multiple users attempting to reserve the campsite for the same/overlapping
date(s). Demonstrate with appropriate test cases that the system can gracefully handle concurrent requests to reserve the campsite.


● Provide appropriate error messages to the caller to indicate the error cases.

● In general, the system should be able to handle large volume of requests for getting the campsite availability.

● There are no restrictions on how reservations are stored as as long as system constraints are not violated.

### Run Application

####Prerequisites:

* Java;
* Mongo running in localhost:27017;
* Gradle;


Command to run the application at the url: http://localhost:9080/, just run the command below:

`./gradlew bootRun`

Remembering that to run the application it is necessary to have Mongo on your machine.

To run the application build, unit tests and checkstyle, the command is:

`./gradlew build` 

####External Endpoints:
Information about the endpoints and your requests, you can access: 
`http://localhost:9080/swagger-ui.html` 


##Architecture

This application was developed in JAVA 8, using this frameworks:
* SpringBoot;
* Lombok;
* Swagger;
* Logstash;

Gradle and some frameworks were used to improve the code quality, such as:
* Checkstyle;
* CodeNarc;


