package com.emissions.app.service;

import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;
import org.springframework.web.util.UriBuilder;

import java.net.URI;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

import static org.springframework.http.MediaType.APPLICATION_JSON;

@Service
public class OrsRestClient {

    private static final String ORS_BASE_URL = "https://api.openrouteservice.org";

    private final RestClient restClient;

    public OrsRestClient() {
        this(RestClient.builder().baseUrl(ORS_BASE_URL).build());
    }

    public OrsRestClient(RestClient restClient) {
        this.restClient = restClient;
    }

    public GeocodeResponse requestCityLocation(String key, String city) {
        return this.restClient.get()
                .uri(buildGeocodeRequestUri(key, city))
                .retrieve()
                .onStatus(HttpStatusCode::is4xxClientError, errorStatusHandler())
                .body(GeocodeResponse.class);
    }

    private Function<UriBuilder, URI> buildGeocodeRequestUri(String key, String city) {
        return uriBuilder -> uriBuilder
                .path("/geocode/search")
                .queryParam("api_key", key)
                .queryParam("text", city)
                .queryParam("layers", "locality")
                .build();
    }

    public DistanceResponse requestDistance(String key, List<Double> startCityLocation, List<Double> endCityLocation) {
        Map<String, Object> requestBody = new HashMap<>();
        requestBody.put("locations", List.of(startCityLocation, endCityLocation));
        requestBody.put("metrics", List.of("distance"));
        return this.restClient.post().uri(buildDistanceRequestUri())
                .contentType(APPLICATION_JSON)
                .body(requestBody)
                .header("Authorization", key)
                .retrieve()
                .onStatus(HttpStatusCode::is4xxClientError, errorStatusHandler())
                .body(DistanceResponse.class);
    }

    private Function<UriBuilder, URI> buildDistanceRequestUri() {
        return uriBuilder -> uriBuilder
                    .path("v2/matrix/driving-car")
                    .build();
    }

    private RestClient.ResponseSpec.ErrorHandler errorStatusHandler(){
        return (request, response) -> {
            throw new IllegalArgumentException(
                    "Invalid request to Open Route Service. Check API key. Details: "
                            + response.getStatusCode());
        };
    }
}
