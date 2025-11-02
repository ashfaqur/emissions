package com.emissions.app;

import com.emissions.app.core.EmissionAppRunner;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class AppApplication implements CommandLineRunner {

    private final Logger log = LogManager.getLogger(AppApplication.class);

    private final EmissionAppRunner runner;

    public AppApplication(EmissionAppRunner runner) {
        this.runner = runner;
    }

    public static void main(String[] args) {
        SpringApplication.run(AppApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {

        try {
            this.runner.run(args);
        } catch (IllegalArgumentException e) {
            errorLogger("Error occurred with input.", e);
        } catch (Exception e) {
            errorLogger("Error occurred computing the emission.", e);
        }
    }

    /**
     * Log the error but print the stack trace only when debug log level is set
     *
     * @param msg Custom message
     * @param e   Original error
     * @throws Exception Throws exception only in debug mode
     */
    public void errorLogger(String msg, Exception e) throws Exception {
        this.log.error("{} {}", msg, e.getMessage());
        if (this.log.isDebugEnabled()) {
            this.log.debug("Stack trace:", e);
            throw e;
        }
    }
}
