package com.emissions.app;

import com.emissions.app.argparse.ArgParser;
import com.emissions.app.argparse.Arguments;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
class AppApplicationTests {

	@Test
	void contextLoads() {
	}

    @Test
    void argumentsTest(){
        final ArgParser parser = new ArgParser();
        Arguments args = parser.parseArguments(new String[] {"--start", "Hamburg", "--end", "Berlin", "--transportation-method", "diesel-car-medium"});
        assertEquals("Hamburg", args.getStartCity());
        assertEquals("Berlin", args.getEndCity());
        assertEquals("diesel-car-medium", args.getTransportationMethod());

        args = parser.parseArguments(new String[] {"--start", "Los Angeles", "--end", "New York", "--transportation-method=diesel-car-medium"});
        assertEquals("Los Angeles", args.getStartCity());
        assertEquals("New York", args.getEndCity());
        assertEquals("diesel-car-medium", args.getTransportationMethod());

        args = parser.parseArguments(new String[] {"--start", "Los Angeles", "--end=New York", "--transportation-method=electric-car-large"});
        assertEquals("Los Angeles", args.getStartCity());
        assertEquals("New York", args.getEndCity());
        assertEquals("electric-car-large", args.getTransportationMethod());

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
