package com.emissions.app;

import com.emissions.app.service.OrsRestClient;
import org.junit.jupiter.api.Test;

import java.net.URI;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class OrsRestClientTests {


    @Test
    void testGeocodeRequestUrl(){
        String key = "key";
        String city = "Los Angeles";
        String url = "https://api.openrouteservice.org/geocode/search?api_key=key&text=Los%20Angeles&layers=locality";

        OrsRestClient restClient = new OrsRestClient();
        URI uri = restClient.buildGeocodeRequestFullUri(key, city);
        assertEquals(url, uri.toString());
    }

    @Test
    void testDistanceRequestUrl(){
        String url = "https://api.openrouteservice.org/v2/matrix/driving-car";
        OrsRestClient restClient = new OrsRestClient();
        URI uri = restClient.buildDistanceRequestFullUri();
        assertEquals(url, uri.toString());
    }

}
