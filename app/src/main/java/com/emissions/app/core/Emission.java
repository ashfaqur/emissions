package com.emissions.app.core;

import com.emissions.app.constants.EmissionData;
import com.emissions.app.service.CityInfo;
import com.emissions.app.service.OrsService;
import org.springframework.stereotype.Component;

@Component
public class Emission {

    public final OrsService service;

    public Emission(OrsService orsService) {
        this.service = orsService;
    }

    /**
     * Calculates the total emission given the cities and
     *
     * @param apiKey                ORS API Key
     * @param startCity             Name of start city
     * @param endCity               Name of end city
     * @param transportEmissionType Transport emission type
     */
    public void calculateEmission(String apiKey, String startCity, String endCity,
                                  EmissionData transportEmissionType) {

        CityInfo startCityInfo = this.service.requestCityLocation(apiKey, startCity);
        System.out.println(startCityInfo);

        CityInfo endCityInfo = this.service.requestCityLocation(apiKey, endCity);
        System.out.println(endCityInfo);

        double distance = this.service.requestDistance(startCityInfo, endCityInfo, apiKey);
        System.out.println("Distance: " + distance);

        int transportEmission = transportEmissionType.getEmission();

        double totalEmission = (transportEmission * distance) / 1000000;
        System.out.println("Total emission (kg): " + totalEmission);


    }
}
