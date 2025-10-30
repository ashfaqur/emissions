package com.emissions.app.service;

import java.util.List;

public record GeocodeResponse(List<Feature> features) {
    public static record Feature(Geometry geometry) {
    }
    public static record Geometry(List<Double> coordinates) {
    }
}


