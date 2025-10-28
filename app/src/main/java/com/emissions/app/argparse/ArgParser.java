package com.emissions.app.argparse;

public class ArgParser {

    private static final String START_ARG = "--start";
    private static final String START_ARG_ALT = "--start=";
    private static final String END_ARG = "--end";
    private static final String END_ARG_ALT = "--end=";
    private static final String TRANSPORT_ARG = "--transportation-method";
    private static final String TRANSPORT_ARG_ALT = "--transportation-method=";


    public Arguments parseArguments(String... args) {
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
                continue;
            } else if (arg.startsWith(START_ARG_ALT) && (arg.length() > START_ARG_ALT.length())) {
                startCity = arg.substring(START_ARG_ALT.length());
                continue;
            } else if (arg.equals(END_ARG) && (i+1 <  args.length)) {
                endCity = args[i + 1];
                i++;
                continue;
            } else if (arg.startsWith(END_ARG_ALT) && (arg.length() > END_ARG_ALT.length())) {
                endCity = arg.substring(END_ARG_ALT.length());
                continue;
            } else if (arg.equals(TRANSPORT_ARG) && (i+1 <  args.length)) {
                transportionMethod = args[i + 1];
                i++;
                continue;
            } else if (arg.startsWith(TRANSPORT_ARG_ALT) && (arg.length() > TRANSPORT_ARG_ALT.length())) {
                transportionMethod = arg.substring(TRANSPORT_ARG_ALT.length());
                continue;
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

        return new Arguments(startCity, endCity, transportionMethod);
    }


}