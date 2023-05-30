# Travel Buddy Finder Backend

This project implements web services to create a distributed service-oriented system called Travel Buddy Finder 
for advertising and searching for travel opportunities.

![Overview of the SOA system component interactions.png](images%2FOverview%20of%20the%20SOA%20system%20component%20interactions.png)

## API

<table>
<tr>
<td>

**Method**
</td> 
<td> 

**URN**
</td> 
<td> 

**Request Body**
</td> 
<td>

**Output**
</td> 
<td>

**Description**
</td>
</tr>

<tr>
<td>POST</td><td>/users</td>
<td>

```json
{
  "username": "admin",
  "password": "admin",
  "isAdmin": true
}
```
</td>
<td>

```json
{
  "id": "9b1deb4d-3b7d-4bad-9bdd-2b0d7b3dcb6d",
  "username": "admin",
  "isAdmin": true
}
```
</td>
<td>Create a user <br /> Note: Only an admin user can create another admin user</td>
</tr>

<tr>
<td>DELETE</td><td>/users/:id</td><td>-</td><td>-</td>
<td>Delete a user <br /> Note: Only an administrator user is authorized</td>
</tr>

<tr>
<td>POST</td><td>/login</td>
<td>

```json
{
  "username": "admin",
  "password": "admin"
}
```
</td>
<td>

```json
{
  "id": "9b1deb4d-3b7d-4bad-9bdd-2b0d7b3dcb6d",
  "username": "admin",
  "isAdmin": true
}
```
</td>
<td>Login</td>
</tr>

<tr>
<td>POST</td><td>/trips</td>
<td>

```json
{
  "location": "Paris",
  "startDate": "2023-04-29",
  "durationInDays": "4"
}
```
</td>
<td>

```json
{
  "id": "e58ed763-928c-4155-bee9-fdbaaadc15f3",
  "location": "Paris",
  "startDate": "2023-04-29",
  "durationInDays": "4",
  "weather": [
    {
      "date": "2023-04-29",
      "maxtempC": 22,
      "mintempC": 11,
      "avgtempC": 17,
      "sunHour": 15.9,
      "uvIndex": 5
    }
  ],
  "sharedBy": {
    "username": "admin"
  },
  "interestedUsers": []
}
```
</td>
<td>Share a trip</td>
</tr>

<tr>
<td>GET</td><td>/trips?available={true|false}&sharedBy={username}</td><td>-</td>
<td>

```json
[
  {
    "id": "e58ed763-928c-4155-bee9-fdbaaadc15f3",
    "location": "Paris",
    "startDate": "2023-04-29",
    "durationInDays": "4",
    "weather": [
      {
        "date": "2023-04-29",
        "maxtempC": 22,
        "mintempC": 11,
        "avgtempC": 17,
        "sunHour": 15.9,
        "uvIndex": 5
      }
    ],
    "sharedBy": {
      "username": "admin"
    },
    "interestedUsers": [
      {
        "username": "admin"
      }
    ]
  }
]
```
</td>
<td>Get trips according to query params</td>
</tr>

<tr>
<td>GET</td><td>/trips/:id</td><td>-</td>
<td>

```json
{
  "id": "e58ed763-928c-4155-bee9-fdbaaadc15f3",
  "location": "Paris",
  "startDate": "2023-04-29",
  "durationInDays": "4",
  "weather": [
    {
      "date": "2023-04-29",
      "maxtempC": 22,
      "mintempC": 11,
      "avgtempC": 17,
      "sunHour": 15.9,
      "uvIndex": 5
    }
  ],
  "sharedBy": {
    "username": "admin"
  },
  "interestedUsers": [
    {
      "username": "admin"
    }
  ]
}
```
</td>
<td>Get a trip by id</td>
</tr>

<tr>
<td>POST</td><td>/trips/:id/interestedUsers</td><td>-</td>
<td>

```json
{
  "id": "e58ed763-928c-4155-bee9-fdbaaadc15f3",
  "location": "Paris",
  "startDate": "2023-04-29",
  "durationInDays": "4",
  "weather": [
    {
      "date": "2023-04-29",
      "maxtempC": 22,
      "mintempC": 11,
      "avgtempC": 17,
      "sunHour": 15.9,
      "uvIndex": 5
    }
  ],
  "sharedBy": {
    "username": "admin"
  },
  "interestedUsers": [
    {
      "username": "admin"
    }
  ]
}
```
</td>
<td>Add interest to a shared trip</td>
</tr>
</table>

## Licence

Code released under Apache License 2.0

## Running the application in dev mode

You can run your application in dev mode that enables live coding using:
```shell script
./mvnw compile quarkus:dev
```

> **_NOTE:_**  Quarkus now ships with a Dev UI, which is available in dev mode only at http://localhost:8080/q/dev/.

## Packaging and running the application

The application can be packaged using:
```shell script
./mvnw package
```
It produces the `quarkus-run.jar` file in the `target/quarkus-app/` directory.
Be aware that it’s not an _über-jar_ as the dependencies are copied into the `target/quarkus-app/lib/` directory.

The application is now runnable using `java -jar target/quarkus-app/quarkus-run.jar`.

If you want to build an _über-jar_, execute the following command:
```shell script
./mvnw package -Dquarkus.package.type=uber-jar
```

The application, packaged as an _über-jar_, is now runnable using `java -jar target/*-runner.jar`.
