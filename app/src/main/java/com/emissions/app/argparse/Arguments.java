package com.emissions.app.argparse;

public class Arguments {

    private final String startCity;
    private final String endCity;
    private final String transportationMethod;

    public Arguments(String startCity, String endCity, String transportationMethod){
        this.startCity = startCity;
        this.endCity = endCity;
        this.transportationMethod= transportationMethod;
    }

    public String getStartCity() {
        return startCity;
    }

    public String getEndCity() {
        return endCity;
    }

    public String getTransportationMethod() {
        return transportationMethod;
    }

    @Override
    public String toString(){
        return "Given Arguments:\nStart City: " + this.startCity
                + "\nEnd City: " + endCity
                + "\nTransportation Method: " +transportationMethod;
    }
}
