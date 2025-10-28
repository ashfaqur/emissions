package com.emissions.app;

import com.emissions.app.argparse.ArgParser;
import com.emissions.app.argparse.Arguments;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
class AppApplicationTests {

    String[] testArgs1 = {"--start", "Hamburg", "--end", "Berlin", "--transportation-method", "diesel-car-medium"};

    String[] testArgs2 = {"--start", "Los Angeles", "--end", "New York", "--transportation-method=diesel-car-medium"};

    String[] testArgs3 = {"--start", "Los Angeles", "--end=New York", "--transportation-method=electric-car-large"};

	@Test
	void contextLoads() {
	}

    @Test
    void argumentsTest(){
        ArgParser parser = new ArgParser();
        Arguments args = parser.parseArguments(testArgs1);
        assertEquals("Hamburg", args.getStartCity());
        assertEquals("Berlin", args.getEndCity());
        assertEquals("diesel-car-medium", args.getTransportationMethod());

        args = parser.parseArguments(testArgs2);
        assertEquals("Los Angeles", args.getStartCity());
        assertEquals("New York", args.getEndCity());
        assertEquals("diesel-car-medium", args.getTransportationMethod());

        args = parser.parseArguments(testArgs3);
        assertEquals("Los Angeles", args.getStartCity());
        assertEquals("New York", args.getEndCity());
        assertEquals("electric-car-large", args.getTransportationMethod());
    }
}
