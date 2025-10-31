package com.emissions.app;

import com.emissions.app.service.*;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class OrsServiceTests {

    @Test
    void testCityLocationRequest(){
        OrsRestClient mockOrsRestClient = Mockito.mock(OrsRestClient.class);
        OrsService orsService = new OrsService(mockOrsRestClient);
        // Values
        String city = "Munich";
        String key = "key";
        List<Double> coordinates = List.of(new Double[]{11.544467, 48.152126});
        // Mock the response
        GeocodeResponse mockedResponse = new GeocodeResponse(List.of(
                new GeocodeResponse.Feature(new GeocodeResponse.Geometry(coordinates))));
        Mockito.when(mockOrsRestClient.requestCityLocation(key, city)).thenReturn(mockedResponse);

        CityData cityData = orsService.requestCityLocation(key,city);
        assertEquals(city, cityData.name());
        assertEquals(coordinates, cityData.coordinates());
    }

    @Test
    void testCityDistanceRequest(){
        OrsRestClient mockOrsRestClient = Mockito.mock(OrsRestClient.class);
        OrsService orsService = new OrsService(mockOrsRestClient);
        // Values
        String key = "key";
        CityData start = new CityData("Munich", List.of(new Double[]{11.544467, 48.152126}));
        CityData end = new CityData("Berlin", List.of(new Double[]{13.407032, 52.524932}));
        double distance = 585534.81;
        // Mock the response
        List<List<Double>> distanceMatrix = new ArrayList<>();
        distanceMatrix.add(List.of(new Double[]{0.0,  distance}));
        distanceMatrix.add(List.of(new Double[]{distance,  0.0}));
        DistanceResponse mockedResponse = new DistanceResponse(distanceMatrix);
        Mockito.when(mockOrsRestClient.requestDistance(
                key, start.coordinates(), end.coordinates()))
                .thenReturn(mockedResponse);
        assertEquals(distance, orsService.requestDistance(key, start, end));

    }
}
