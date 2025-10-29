package com.emissions.app;

import com.emissions.app.argparse.ArgParser;
import com.emissions.app.argparse.Arguments;
import com.emissions.app.argparse.Environment;
import com.emissions.app.core.Emission;
import com.emissions.app.service.OrsService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.security.Provider;

@SpringBootApplication
public class AppApplication implements CommandLineRunner {

    private ArgParser argParser = new ArgParser();

    private final Emission emission;

    public AppApplication(Emission emissionComputation) {
        this.emission = emissionComputation;
    }

	public static void main(String[] args) {
		SpringApplication.run(AppApplication.class, args);
	}

    @Override
    public void run(String... args) throws Exception {

        Arguments arguments;
        try {
            arguments = argParser.parseArguments(args);
        } catch (IllegalArgumentException e) {
            System.out.println("Error occurred parsing arguments. " + e.getMessage());
            return;
        }
        System.out.println(arguments);

        String orsToken;
        try {
            orsToken = Environment.findOrsToken();
        } catch (IllegalArgumentException e) {
            System.out.println("Missing startup configuration. " + e.getMessage());
            return;
        }
        System.out.println("Using ORS API token: " + orsToken);

        this.emission.calculateEmission(orsToken, arguments);
    }
}
