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

Invoke help to see the options available

    java -jar target/emissions-calculator.jar --help

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

To enable debug log:

    --logging.level.com.emissions.app=DEBUG

Example command:

    java -jar target/emissions-calculator.jar --start "Munich" --end="Berlin" --transportation-method=diesel-car-medium

## Run unit tests

Command to run the unit tests

Unix:

    ./mvnw test

Windows:

    mvnw.cmd test


## Docker

The app can be build and run insider a docker container.

Install docker

    https://docs.docker.com/engine/install/

Build the docker image from the app directory

    sudo docker build -t emissions-calculator:latest .

Run the docker with the key and the required command options

    sudo docker run -e ORS_TOKEN=<key> -t emissions-calculator:latest --start <city1> --end <city2> --transportation-method <transport>

Example command:

    sudo docker run -e ORS_TOKEN=<key> -t emissions-calculator:latest --start Munich --end Berlin --transportation-method diesel-car-medium


