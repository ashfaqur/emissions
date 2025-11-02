package com.emissions.app.argparse;

import com.emissions.app.constants.EmissionData;

public class Arguments {

    private final String startCity;
    private final String endCity;
    private final EmissionData transportationEmissionType;

    public Arguments(String startCity, String endCity,
                     EmissionData transportationEmissionType) {
        this.startCity = startCity;
        this.endCity = endCity;
        this.transportationEmissionType = transportationEmissionType;
    }

    public String getStartCity() {
        return startCity;
    }

    public String getEndCity() {
        return endCity;
    }

    public EmissionData getTransportationEmission() {
        return this.transportationEmissionType;
    }

    @Override
    public String toString() {
        return "Given Arguments:\nStart City: " + this.startCity
                + "\nEnd City: " + endCity
                + "\nTransportation Method: " + this.transportationEmissionType.getName()
                + "\nEmission (g) per km: " + this.transportationEmissionType.getEmission();
    }
}
