package com.emissions.app.help;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Component;

@Component
public class Help {
    private static final String HELP_ARG = "--help";
    private static final String HELP_ARG_ALT = "-h";
    private final Logger log = LogManager.getLogger(Help.class);

    /**
     * Checks for conditions for displaying help text
     *
     * @param args given arguments
     * @return boolean TRUE to show, FALSE otherwise
     */
    public boolean showHelp(String... args) {
        return (args.length == 0
                || args[0].equalsIgnoreCase(HELP_ARG)
                || args[0].equalsIgnoreCase(HELP_ARG_ALT));
    }

    /**
     * Displays the help text on the console
     */
    public void displayHelp() {
        String helpText = "\nCompute CO2 emission when travelling between two cities."
                + "\nOptions:"
                + "\n  --start  Start city name"
                + "\n  --end    End city name"
                + "\n  --transportation-method  Type of transport used for travel";
        log.info(helpText);
    }
}
