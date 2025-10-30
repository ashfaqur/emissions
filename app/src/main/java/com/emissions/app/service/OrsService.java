package com.emissions.app.service;

import java.util.function.Function;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;
import org.springframework.web.util.UriBuilder;

import java.net.URI;

import static org.springframework.http.MediaType.APPLICATION_JSON;

@Service
public class OrsService {

    private static final String ORS_BASE_URL = "https://api.openrouteservice.org";

    private final RestClient restClient;

    public OrsService(){
        this.restClient = RestClient.builder().baseUrl(ORS_BASE_URL).build();
    }

    public CityInfo requestCityLocation(String key, String city){

        String response = this.restClient.get().uri(buildGeocodeRequestUri(key, city)).retrieve().body(String.class);

        System.out.println(response);

        // TODO: Use DTO and error handling
        ObjectMapper mapper = new ObjectMapper();
        JsonNode node = null;
        try {
            node = mapper.readTree(response);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }

        String cityName = node.get("features").get(0).get("properties").get("name").asText();
        double longitude = node.get("features").get(0).get("geometry").get("coordinates").get(0).asDouble();
        double latitude = node.get("features").get(0).get("geometry").get("coordinates").get(1).asDouble();

        return new CityInfo(cityName, latitude, longitude);
    }


    public double requestDistance(CityInfo start, CityInfo end, String key){
        // FIXME: use actual city info
        String response = this.restClient.post().uri(buildDistanceRequestUri())
                .contentType(APPLICATION_JSON)
                .body("{\"locations\":[[-118.25703,34.05513],[-73.9708,40.68295]],\"metrics\":[\"distance\"]}")
                .header("Authorization", key)
                .retrieve()
                .body(String.class);
        // TODO: Use DTO and error handling
        ObjectMapper mapper = new ObjectMapper();
        JsonNode node = null;
        try {
            node = mapper.readTree(response);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }

        double distance = node.get("distances").get(0).get(1).asDouble();
        System.out.println("Distance: " + response);
        return  distance;
    }

    private Function<UriBuilder, URI> buildGeocodeRequestUri(String key, String city) {
        return uriBuilder -> {
            URI uri = uriBuilder
                    .path("/geocode/search")
                    .queryParam("api_key", key)
                    .queryParam("text", city)
                    .queryParam("layers", "locality")
                    .build();

            System.out.println("gecode url= " + uri);
            return uri;
        };
    }

    private Function<UriBuilder, URI> buildDistanceRequestUri() {
        return uriBuilder -> {
            URI uri = uriBuilder
                    .path("v2/matrix/driving-car")
                    .build();
            return uri;
        };
    }
}
