package com.emissions.app.service;

import java.util.List;

public record CityData(String name, List<Double> coordinates) {

    @Override
    public String toString() {
        return "CityData{" +
                "name='" + name + '\'' +
                ", coordinates=" + coordinates +
                '}';
    }
};
