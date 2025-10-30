package com.emissions.app.core;

import com.emissions.app.AppApplication;
import com.emissions.app.constants.EmissionData;
import com.emissions.app.service.CityData;
import com.emissions.app.service.CityInfo;
import com.emissions.app.service.OrsService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Component;

@Component
public class Emission {

    private final Logger log = LogManager.getLogger(Emission.class);

    public final OrsService service;

    public Emission(OrsService orsService) {
        this.service = orsService;
    }

    /**
     * Calculates the total emission between cities with given transportation type
     *
     * @param apiKey            ORS API Key
     * @param start             Name of start city
     * @param end               Name of end city
     * @param transportEmissionType Transport emission type
     */
    public double calculateEmission(String apiKey, String start, String end,
                                  EmissionData transportEmissionType) {
        CityData startCity = this.service.requestCityLocation2(apiKey, start);
        log.debug("Start City: {}, with coordinates {}", startCity.name(), startCity.coordinates());

        CityData endCity = this.service.requestCityLocation2(apiKey, end);
        log.debug("End City: {}, with coordinates {}",endCity.name(),endCity.coordinates());

        double distance = this.service.requestDistance(apiKey, startCity, endCity);
        log.debug("Distance between cities: {}", distance);

        int transportEmission = transportEmissionType.getEmission();
        return (transportEmission * distance) / 1000000;
    }
}
