package com.emissions.app.service;

public class CityInfo {

    private final String cityName;
    private final double cityLatitude;
    private final double cityLongitude;

    public CityInfo(String cityName, double cityLatitude, double cityLongitude) {
        this.cityName = cityName;
        this.cityLongitude = cityLongitude;
        this.cityLatitude = cityLatitude;
    }

    public String getCityName() {
        return cityName;
    }

    public double getCityLatitude() {
        return cityLatitude;
    }

    public double getCityLongitude() {
        return cityLongitude;
    }

    @Override
    public String toString() {
        return "CityInfo{" +
                "cityName='" + cityName + '\'' +
                ", cityLongitude=" + cityLongitude +
                ", cityLatitude=" + cityLatitude +
                '}';
    }
}
