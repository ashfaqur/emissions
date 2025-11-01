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
import org.springframework.boot.logging.LogLevel;
import org.springframework.boot.logging.LoggingSystem;
import org.springframework.boot.logging.logback.RootLogLevelConfigurator;

import java.util.Objects;

@SpringBootApplication
public class AppApplication implements CommandLineRunner {

    private final Logger log = LogManager.getLogger(AppApplication.class);

    private final ArgParser argParser;
    private final Emission emission;
    private final Environment environment;
    private final Help help;
    private final LoggingSystem loggingSystem;


    public AppApplication(Emission emissionComputation,
                          LoggingSystem loggingSystem) {
        this.emission = emissionComputation;
        this.argParser = new ArgParser();
        this.environment = new Environment();
        this.help = new Help();
        this.loggingSystem = loggingSystem;
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
            errorLogger("Error occurred parsing arguments.", e);
            return;
        }
        this.log.debug(arguments);

        String orsToken;
        try {
            orsToken = this.environment.findOrsToken();
        } catch (IllegalArgumentException e) {
            errorLogger("Missing startup configuration.", e);
            return;
        }
        this.log.debug("Using ORS API token: {}", orsToken);

        double totalEmission;

        try {
            totalEmission = this.emission.calculateEmission(
                    orsToken, arguments.getStartCity(), arguments.getEndCity(),
                    arguments.getTransportationEmission());
        } catch (Exception e){
            errorLogger("Error occurred computing the emission.", e);
            return;
        }
        this.log.info("Your trip caused {}kg of CO2-equivalent.", String.format("%.1f", totalEmission));
    }

    /**
     * Log the error but print the stack trace only when debug log level is set
     * @param msg Custom message
     * @param e Original error
     * @throws Exception Throws exception only in debug mode
     */
    public void errorLogger(String msg, Exception e) throws Exception {
        this.log.error("{} {}",msg, e.getMessage());
        if (this.log.isDebugEnabled()) {
            this.log.debug("Stack trace:", e);
            throw e;
        }
    }
}
