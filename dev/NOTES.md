# Development Notes

## Intro

The goal is to create a CO2 emissions calculator when travelling between two cities.

## Initial setup

First, a simple Spring Boot project that takes in arguments
and prints something.

Use spring io initializer for the generating the initial project stub.

## IDE

Using Intellij as IDE with AI hints off.

## Arguments Parser

Create a arg parser to parse and validate the inputs.
Add some tests.

## Emissions Data

Add the emissions data as enums. Use that to validate arg input.

## ORS token

Read the token from the env var

## ORS service

Set up the core service for client calls to the ORS to get the data
and the simple computation end to end.

## PostMan

Use postman for testing the rest client calls

## First core logic draft

Get the first very rough draft of the core logic working for initial test.
Next to polish, remove test values and handle error conditions.

## Rest client

https://docs.spring.io/spring-framework/reference/integration/rest-clients.html#rest-restclient

## DTO example

https://medium.com/@mariorodrguezgalicia/what-is-a-dto-in-spring-boot-and-why-should-you-use-it-97651506e516

## Uri builder

Should help with testing the url

https://www.baeldung.com/spring-uricomponentsbuilder











