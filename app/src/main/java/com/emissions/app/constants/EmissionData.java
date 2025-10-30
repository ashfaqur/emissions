package com.emissions.app.constants;

public enum EmissionData {

    DIESEL_CAR_SM("diesel-car-small", 142),
    PETROL_CAR_SM("petrol-car-small", 154),
    PLUGIN_HYBRID_CAR_SM("plugin-hybrid-car-small", 73),
    ELECTRIC_CAR_SM("electric-car-small", 50),
    DIESEL_CAR_M("diesel-car-medium", 171),
    PETROL_CAR_M("petrol-car-medium", 192),
    PLUGIN_HYBRID_CAR_M("plugin-hybrid-car-medium", 110),
    ELECTRIC_CAR_M("electric-car-medium", 58),
    DIESEL_CAR_L("diesel-car-large", 209),
    PETROL_CAR_L("petrol-car-large", 282),
    PLUGIN_HYBRID_CAR_L("plugin-hybrid-car-large", 126),
    ELECTRIC_CAR_L("electric-car-large", 73),
    BUS("bus-default", 27),
    TRAIN("train-default", 6);


    private final String name;
    private final int emission;

    EmissionData(String name, int emission) {
        this.name = name;
        this.emission = emission;
    }

    public String getName() {
        return this.name;
    }

    public int getEmission() {
        return this.emission;
    }

}
