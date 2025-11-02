package com.emissions.app.core;

import com.emissions.app.argparse.AppEnvironment;
import com.emissions.app.argparse.ArgParser;
import com.emissions.app.argparse.Arguments;
import com.emissions.app.help.Help;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
public class EmissionAppRunner {

    private final Logger log = LogManager.getLogger(EmissionAppRunner.class);

    private final ArgParser argParser;
    private final Emission emission;
    private final AppEnvironment environment;
    private final Help help;

    public EmissionAppRunner(Emission emission,
                             ArgParser argParser,
                             AppEnvironment environment,
                             Help help) {
        this.emission = emission;
        this.argParser = argParser;
        this.environment = environment;
        this.help = help;
    }

    public double run(String... args) throws Exception {
        Objects.requireNonNull(args);
        if (this.help.showHelp(args)) {
            this.help.displayHelp();
            return 0;
        }
        Arguments arguments = this.argParser.parseArguments(args);
        this.log.debug(arguments);

        String orsToken = this.environment.findOrsToken();
        this.log.debug("Using ORS API token: {}", orsToken);

        double totalEmission = this.emission.calculateEmission(
                orsToken, arguments.startCity(), arguments.endCity(),
                arguments.getTransportationEmission());
        this.log.info("Your trip caused {}kg of CO2-equivalent.",
                String.format("%.1f", totalEmission));
        return totalEmission;
    }
}
