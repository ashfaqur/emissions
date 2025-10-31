package com.emissions.app;

import com.emissions.app.argparse.ArgParser;
import com.emissions.app.argparse.Arguments;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class ArgumentsTests {

    @Test
    void argumentsTest(){
        final ArgParser parser = new ArgParser();
        Arguments args = parser.parseArguments(new String[] {"--start", "Hamburg", "--end", "Berlin", "--transportation-method", "diesel-car-medium"});
        assertEquals("Hamburg", args.getStartCity());
        assertEquals("Berlin", args.getEndCity());
        assertEquals("diesel-car-medium", args.getTransportationEmission().getName());

        args = parser.parseArguments(new String[] {"--start", "Los Angeles", "--end", "New York", "--transportation-method=diesel-car-medium"});
        assertEquals("Los Angeles", args.getStartCity());
        assertEquals("New York", args.getEndCity());
        assertEquals("diesel-car-medium", args.getTransportationEmission().getName());

        args = parser.parseArguments(new String[] {"--start", "Los Angeles", "--end=New York", "--transportation-method=electric-car-large"});
        assertEquals("Los Angeles", args.getStartCity());
        assertEquals("New York", args.getEndCity());
        assertEquals("electric-car-large", args.getTransportationEmission().getName());

        assertThrows(IllegalArgumentException.class, () -> {
            parser.parseArguments(new String[] {});
        });

        assertThrows(IllegalArgumentException.class, () -> {
            parser.parseArguments(new String[] {"--start", "Los Angeles"});
        });

        assertThrows(IllegalArgumentException.class, () -> {
            parser.parseArguments(new String[] {"--start", "Los Angeles", "--end=New York", "--transportation-method=undefined"});
        });
    }
}
