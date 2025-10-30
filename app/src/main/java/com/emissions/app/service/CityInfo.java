package com.emissions.app.service;

import java.util.List;

public record CityInfo(String cityName, double cityLatitude, double cityLongitude) {

    @Override
    public String toString() {
        return "CityInfo{" +
                "cityName='" + cityName + '\'' +
                ", cityLongitude=" + cityLongitude +
                ", cityLatitude=" + cityLatitude +
                '}';
    }
}
