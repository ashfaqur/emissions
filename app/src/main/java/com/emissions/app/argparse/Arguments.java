package com.emissions.app.argparse;

import com.emissions.app.constants.EmissionData;

public record Arguments(String startCity, String endCity, EmissionData transportationEmissionType) {

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
