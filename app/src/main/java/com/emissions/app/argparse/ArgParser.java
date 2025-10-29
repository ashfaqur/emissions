package com.emissions.app.argparse;

import com.emissions.app.constants.EmissionData;

import java.util.Arrays;
import java.util.Objects;
import java.util.Optional;

public class ArgParser {

    private static final String START_ARG = "--start";
    private static final String START_ARG_ALT = "--start=";
    private static final String END_ARG = "--end";
    private static final String END_ARG_ALT = "--end=";
    private static final String TRANSPORT_ARG = "--transportation-method";
    private static final String TRANSPORT_ARG_ALT = "--transportation-method=";


    public Arguments parseArguments(String... args) {
        Objects.requireNonNull(args);
        if (args.length == 0) {
            throw new IllegalArgumentException("No arguments given");
        }

        String startCity = null;
        String endCity = null;
        String transportionMethod = null;

        for (int i = 0; i < args.length; i++) {
            String arg = args[i];
            if (arg.equals(START_ARG) && (i+1 <  args.length)) {
                startCity = args[i + 1];
                i++;
            } else if (arg.startsWith(START_ARG_ALT) && (arg.length() > START_ARG_ALT.length())) {
                startCity = arg.substring(START_ARG_ALT.length());
            } else if (arg.equals(END_ARG) && (i+1 <  args.length)) {
                endCity = args[i + 1];
                i++;
            } else if (arg.startsWith(END_ARG_ALT) && (arg.length() > END_ARG_ALT.length())) {
                endCity = arg.substring(END_ARG_ALT.length());
            } else if (arg.equals(TRANSPORT_ARG) && (i+1 <  args.length)) {
                transportionMethod = args[i + 1];
                i++;
            } else if (arg.startsWith(TRANSPORT_ARG_ALT) && (arg.length() > TRANSPORT_ARG_ALT.length())) {
                transportionMethod = arg.substring(TRANSPORT_ARG_ALT.length());
            }
        }

        if (startCity == null){
            throw new IllegalArgumentException("Start city not specified.");
        }

        if (endCity == null){
            throw new IllegalArgumentException("End city not specified.");
        }

        if (transportionMethod == null){
            throw new IllegalArgumentException("Transportation method not specified.");
        }

        final String transport = transportionMethod;

        Optional<EmissionData> emissionType =  Arrays.stream(EmissionData.values()).filter(data -> data.getName().equalsIgnoreCase(transport)).findFirst();
        if (emissionType.isEmpty()){
            throw new IllegalArgumentException("Invalid transportation method given.");
        }
        EmissionData transportEmissionType = emissionType.get();

        if (Arrays.stream(EmissionData.values()).noneMatch(data -> data.getName().equals(transport))){
            throw new IllegalArgumentException("Invalid transportation method given.");
        }

        return new Arguments(startCity, endCity, transportEmissionType);
    }


}