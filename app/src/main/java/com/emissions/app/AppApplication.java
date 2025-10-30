package com.emissions.app;

import com.emissions.app.argparse.ArgParser;
import com.emissions.app.argparse.Arguments;
import com.emissions.app.argparse.Environment;
import com.emissions.app.core.Emission;
import com.emissions.app.help.Help;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.Objects;

@SpringBootApplication
public class AppApplication implements CommandLineRunner {

    private final Logger log = LogManager.getLogger(AppApplication.class);

    private final ArgParser argParser;
    private final Emission emission;
    private final Environment environment;
    private final Help help;

    public AppApplication(Emission emissionComputation) {
        this.emission = emissionComputation;
        this.argParser = new ArgParser();
        this.environment = new Environment();
        this.help = new Help();
    }

    public static void main(String[] args) {
        SpringApplication.run(AppApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        Objects.requireNonNull(args);
        if (this.help.showHelp(args)) {
            this.help.displayHelp();
            return;
        }
        Arguments arguments;
        try {
            arguments = argParser.parseArguments(args);
        } catch (IllegalArgumentException e) {
            log.error("Error occurred parsing arguments. {}", e.getMessage(), e);
            return;
        }
        log.debug(arguments);

        String orsToken;
        try {
            orsToken = this.environment.findOrsToken();
        } catch (IllegalArgumentException e) {
            log.error("Missing startup configuration. {}", e.getMessage(), e);
            return;
        }
        log.debug("Using ORS API token: {}", orsToken);

        this.emission.calculateEmission(orsToken, arguments.getStartCity(),
                arguments.getEndCity(), arguments.getTransportationEmission());
    }
}
