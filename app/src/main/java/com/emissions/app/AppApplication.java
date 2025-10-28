package com.emissions.app;

import com.emissions.app.argparse.ArgParser;
import com.emissions.app.argparse.Arguments;
import com.emissions.app.argparse.Environment;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class AppApplication implements CommandLineRunner {

    private ArgParser argParser = new ArgParser();

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
            orsToken = Environment.findOrsEnv();
        } catch (IllegalArgumentException e) {
            System.out.println("Missing startup configuration. " + e.getMessage());
            return;
        }
        System.out.println("Using ORS API token: " + orsToken);

    }
}
