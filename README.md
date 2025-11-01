# emissions

A simple app to calculate the approximate Carbon dioxide emissions
occurred when travelling between two cities based on the type of
transporation.

## Requirements:

Java 21 is required on the system.

If not installed then download appropiate version
and follow the install guide:

https://adoptium.net/temurin/releases


## Installation

Navigate to the app folder and install the app with the following command

Unix:

    ./mvnw clean package

Windows:

    mvnw.cmd clean package

## Open Route Service API Key

The app requires API Key to access to Open Route Service APIs.

Create a free account with Open Route Service to get an API Key.

https://openrouteservice.org/

Store the API Key in an environment variable.

Unix:

    export ORS_TOKEN=key

Windows:

    set ORS_TOKEN=key

## Commands

Invoke help to see the commands available

    java -jar target/app-0.0.1-SNAPSHOT.jar --help

    Compute CO2 emission when travelling between two cities.
        Options:
        --start  Start city name
        --end    End city name
        --transportation-method  Type of transport used for travel

Where the transportation-method can be one of the following:

    diesel-car-small
    petrol-car-small
    plugin-hybrid-car-small
    electric-car-small
    diesel-car-medium
    petrol-car-medium
    plugin-hybrid-car-medium
    electric-car-medium
    diesel-car-large
    petrol-car-large
    plugin-hybrid-car-large
    electric-car-large
    bus-default
    train-default

## Docker
