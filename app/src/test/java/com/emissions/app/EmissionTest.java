package com.emissions.app;

import com.emissions.app.constants.EmissionData;
import com.emissions.app.core.Emission;
import com.emissions.app.service.CityData;
import com.emissions.app.service.OrsService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class EmissionTest {

    private OrsService mockService;

    @BeforeEach
    void setUp() {
        this.mockService = Mockito.mock(OrsService.class);
    }

    @Test
    void calculateEmissionTest(){
        String key = "key";
        String start = "Munich";
        String end = "Berlin";
        EmissionData transport = EmissionData.PETROL_CAR_M;
        double distance = 585534.81;
        double result = (transport.getEmission() * distance)/1000000;

        CityData startCity = new CityData(start, List.of(new Double[]{11.544467, 48.152126}));
        CityData endCity = new CityData(end, List.of(new Double[]{13.407032, 52.524932}));

        Emission emission = new Emission(this.mockService);

        Mockito.when(this.mockService.requestCityLocation(key,start)).thenReturn(startCity);
        Mockito.when(this.mockService.requestCityLocation(key,end)).thenReturn(endCity);
        Mockito.when(this.mockService.requestDistance(key,startCity, endCity)).thenReturn(distance);
        assertEquals(result, emission.calculateEmission(key,start,end, transport));

    }
}
