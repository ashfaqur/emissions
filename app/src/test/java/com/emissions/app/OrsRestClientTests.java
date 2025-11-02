package com.emissions.app;

import com.emissions.app.service.CityData;
import com.emissions.app.service.DistanceResponse;
import com.emissions.app.service.GeocodeResponse;
import com.emissions.app.service.OrsRestClient;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.client.MockRestServiceServer;
import org.springframework.web.client.RestClient;

import java.net.URI;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.*;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withSuccess;

@ExtendWith(SpringExtension.class)
public class OrsRestClientTests {

    private static final String ORS_BASE_URL = "https://api.openrouteservice.org";

    private MockRestServiceServer mockServer;
    private OrsRestClient orsRestClient;

    @BeforeEach
    void setup() {
        RestClient.Builder builder = RestClient.builder().baseUrl(ORS_BASE_URL);
        this.mockServer = MockRestServiceServer.bindTo(builder).build();
        this.orsRestClient = new OrsRestClient(builder.build());
    }

    @Test
    void testRequestCityLocation() {
        String key = "key";
        String city = "Berlin";
        URI expectedUri = this.orsRestClient.buildGeocodeRequestFullUri(key, city);
        String mockJsonResponse =
        """
            {
                "features": [
                    {
                        "geometry": { "coordinates": [13.4050, 52.5200] }
                    }
                ]
            }
        """;
        this.mockServer.expect(requestTo(expectedUri))
                .andExpect(method(org.springframework.http.HttpMethod.GET))
                .andRespond(withSuccess(mockJsonResponse, MediaType.APPLICATION_JSON));

        GeocodeResponse response = this.orsRestClient.requestCityLocation(key, city);
        assertNotNull(response);
        assertEquals(1, response.features().size());
        assertEquals(List.of(13.4050, 52.5200), response.features().getFirst().geometry().coordinates());
        this.mockServer.verify();
    }

    @Test
    void testRequestDistance() {
        String key = "key";
        List<Double> startCity =  List.of(new Double[]{11.544467, 48.152126});
        List<Double> endCity = List.of(new Double[]{13.407032, 52.524932});
        URI expectedUri = this.orsRestClient.buildDistanceRequestFullUri();
        double expectedDistance = 585534.81;
        String bodyContent = String.format("""
            {
                "locations": [
                    [%f, %f],
                    [%f, %f]
                ],
                "metrics": ["distance"]
            }
        """, startCity.get(0), startCity.get(1), endCity.get(0), endCity.get(1));
        String mockJsonResponse = String.format("""
            {
                "distances": [
                    [0.0, %f],
                    [%f, 0.0]
                ]
            }
        """, expectedDistance, expectedDistance);

        this.mockServer.expect(requestTo(expectedUri))
                .andExpect(method(org.springframework.http.HttpMethod.POST))
                .andExpect(header("Authorization", key))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().json(bodyContent))
                .andRespond(withSuccess(mockJsonResponse, MediaType.APPLICATION_JSON));

        DistanceResponse response = this.orsRestClient.requestDistance(key, startCity, endCity);
        assertNotNull(response);
        assertNotNull(response.distances());
        assertEquals(2, response.distances().size());
        assertEquals(expectedDistance, response.distances().getFirst().get(1));
    }
    
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
