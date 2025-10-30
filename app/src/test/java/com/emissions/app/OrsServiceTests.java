package com.emissions.app;

import com.emissions.app.service.CityData;
import com.emissions.app.service.GeocodeResponse;
import com.emissions.app.service.OrsRestClient;
import com.emissions.app.service.OrsService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class OrsServiceTests {

    @Test
    void testCityLocationRequest(){
        OrsRestClient mockOrsRestClient = Mockito.mock(OrsRestClient.class);
        // Values
        OrsService orsService = new OrsService(mockOrsRestClient);
        String city = "Munich";
        String key = "key";
        List<Double> coordinates = List.of(new Double[]{11.544467, 48.152126});
        // Mock the response
        GeocodeResponse mockResponse = new GeocodeResponse(List.of(
                new GeocodeResponse.Feature(new GeocodeResponse.Geometry(coordinates))));
        Mockito.when(mockOrsRestClient.requestCityLocation(key, city)).thenReturn(mockResponse);

        CityData cityData = orsService.requestCityLocation2(key,city);
        assertEquals(city, cityData.name());
        assertEquals(coordinates, cityData.coordinates());
    }
}
