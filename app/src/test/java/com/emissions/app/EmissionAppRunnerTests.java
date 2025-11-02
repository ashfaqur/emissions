package com.emissions.app;

import com.emissions.app.argparse.AppEnvironment;
import com.emissions.app.argparse.ArgParser;
import com.emissions.app.argparse.Arguments;
import com.emissions.app.constants.EmissionData;
import com.emissions.app.core.Emission;
import com.emissions.app.core.EmissionAppRunner;
import com.emissions.app.help.Help;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class EmissionAppRunnerTests {

    private ArgParser argParser;
    private Emission emission;
    private AppEnvironment env;
    private Help help;

    @BeforeEach
    void testSetup(){
        this.argParser = Mockito.mock(ArgParser.class);
        this.emission = Mockito.mock(Emission.class);
        this.env = Mockito.mock(AppEnvironment.class);
        this.help = Mockito.mock(Help.class);
    }

    @Test
    void testRun() throws Exception {
        String[] args = {"args"};
        String key = "key";
        double expectedResult = 50;
        Arguments mockArgs = new Arguments("Munich", "Berlin", EmissionData.BUS);
        Mockito.when(this.help.showHelp(args)).thenReturn(false);
        Mockito.when(this.argParser.parseArguments(args)).thenReturn(mockArgs);
        Mockito.when(this.env.findOrsToken()).thenReturn(key);
        Mockito.when(this.emission.calculateEmission(key, mockArgs.getStartCity(), mockArgs.getEndCity(), mockArgs.getTransportationEmission())).thenReturn(expectedResult);

        EmissionAppRunner runner = new EmissionAppRunner(this.emission, this.argParser, this.env, this.help);
        assertEquals(expectedResult, runner.run(args));
    }

}
