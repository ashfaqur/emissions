package com.emissions.app.service;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

import static org.springframework.http.MediaType.APPLICATION_JSON;

@Service
public class OrsRestClient {

    private static final String ORS_BASE_URL = "https://api.openrouteservice.org";

    private final Logger log = LogManager.getLogger(OrsRestClient.class);

    private final RestClient restClient;

    public OrsRestClient() {
        this(RestClient.builder().baseUrl(ORS_BASE_URL).build());
    }

    public OrsRestClient(RestClient restClient) {
        this.restClient = restClient;
    }

    public GeocodeResponse requestCityLocation(String key, String city) {
        URI uri = buildGeocodeRequestFullUri(key, city);
        log.debug("City Location Request URI: {}", uri);
        return this.restClient.get()
                .uri(uri)
                .retrieve()
                .onStatus(HttpStatusCode::is4xxClientError, errorStatusHandler())
                .body(GeocodeResponse.class);
    }

    public URI buildGeocodeRequestFullUri(String key, String city){
        return UriComponentsBuilder.fromUriString(ORS_BASE_URL)
                .path("/geocode/search")
                .queryParam("api_key", key)
                .queryParam("text", city)
                .queryParam("layers", "locality")
                .build()
                .toUri();
    }

    public DistanceResponse requestDistance(String key, List<Double> startCityLocation, List<Double> endCityLocation) {
        Map<String, Object> requestBody = new HashMap<>();
        requestBody.put("locations", List.of(startCityLocation, endCityLocation));
        requestBody.put("metrics", List.of("distance"));
        URI uri = buildDistanceRequestFullUri();
        log.debug("Distance Request URI: {}", uri);
        return this.restClient.post().uri(uri)
                .contentType(APPLICATION_JSON)
                .body(requestBody)
                .header("Authorization", key)
                .retrieve()
                .onStatus(HttpStatusCode::is4xxClientError, errorStatusHandler())
                .body(DistanceResponse.class);
    }

    public URI buildDistanceRequestFullUri(){
        return UriComponentsBuilder.fromUriString(ORS_BASE_URL)
                .path("v2/matrix/driving-car")
                .build()
                .toUri();
    }

    private RestClient.ResponseSpec.ErrorHandler errorStatusHandler(){
        return (request, response) -> {
            throw new IllegalArgumentException(
                    "Invalid request to Open Route Service. Check API key. "
                            + response.getStatusCode());
        };
    }
}
