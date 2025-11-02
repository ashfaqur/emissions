package com.emissions.app.core;

import com.emissions.app.constants.EmissionData;
import com.emissions.app.service.CityData;
import com.emissions.app.service.OrsService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Component;

@Component
public class Emission {

    public final OrsService service;
    private final Logger log = LogManager.getLogger(Emission.class);

    public Emission(OrsService orsService) {
        this.service = orsService;
    }

    /**
     * Calculates the total emission between cities with given transportation type
     *
     * @param apiKey                ORS API Key
     * @param start                 Name of start city
     * @param end                   Name of end city
     * @param transportEmissionType Transport emission type
     * @return the calculated emission in kg
     */
    public double calculateEmission(String apiKey, String start, String end,
                                    EmissionData transportEmissionType) {
        CityData startCity = this.service.requestCityLocation(apiKey, start);
        log.debug("Start City: {}, with coordinates {}", startCity.name(), startCity.coordinates());

        CityData endCity = this.service.requestCityLocation(apiKey, end);
        log.debug("End City: {}, with coordinates {}", endCity.name(), endCity.coordinates());

        double distance = this.service.requestDistance(apiKey, startCity, endCity);
        log.debug("Distance between cities: {} meters", distance);

        int transportEmission = transportEmissionType.getEmission();
        return (transportEmission * distance) / 1000000;
    }
}
