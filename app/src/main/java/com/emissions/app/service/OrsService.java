package com.emissions.app.service;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;

@Service
public class OrsService {

    private static final String ORS_BASE_URL = "https://api.openrouteservice.org";

    private final OrsRestClient restClient;

    public OrsService(OrsRestClient restClient) {
        this.restClient = restClient;
    }

    public CityData requestCityLocation(String key, String city) {
        GeocodeResponse getcodeResponse = this.restClient.requestCityLocation(key, city);
        return extractCityData(city, getcodeResponse);
    }

    private CityData extractCityData(String city, GeocodeResponse response) {
        if (response == null){
            throw new NoSuchElementException("No city " + city + " found");
        }
        List<GeocodeResponse.Feature> features = response.features();
        if (features == null ||  features.isEmpty()){
            throw new NoSuchElementException("No city " + city + " found");
        }
        GeocodeResponse.Geometry geometry =  features.getFirst().geometry();
        if (geometry == null){
            throw new NoSuchElementException("Unable to find coordinates");
        }
        List<Double> coordinates = geometry.coordinates();
        if (coordinates == null || coordinates.isEmpty()){
            throw new NoSuchElementException("Unable to find coordinates");
        }
        //TODO: would be better to extract the city name from the response
        return new CityData(city, coordinates);
    }

    public double requestDistance(String key, CityData startCity, CityData endCity){
        DistanceResponse response =  this.restClient.requestDistance(
                key, startCity.coordinates(), endCity.coordinates());
        if (response == null){
            throw new NoSuchElementException("Unable to get distance response from ORS");
        }
        List<List<Double>> distanceMatrix =  response.distances();
        if (distanceMatrix.isEmpty()){
            throw new NoSuchElementException("Unable to get distance information between cities");
        }
        List<Double> distances = distanceMatrix.getFirst();
        if (distanceMatrix.isEmpty()){
            throw new NoSuchElementException("Unable to get distance information between cities");
        }
        for (Double distance : distances){
            if (distance > 0){
                return distance;
            }
        }
        throw new NoSuchElementException("No distance information found");
    }
}
