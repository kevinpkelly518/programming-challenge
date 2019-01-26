package de.exxcellent.challenge;
import java.io.FileNotFoundException;

import de.exxcellent.challenge.solution.*;

/**
 * The entry class for your solution. This class is only aimed as starting point and not intended as baseline for your software
 * design. Read: create your own classes and packages as appropriate.
 *
 * @author Benjamin Schmid <benjamin.schmid@exxcellent.de>
 */
public final class App {

    /**
     * This is the main entry method of your program.
     * @param args The CLI arguments passed
     * @throws FileNotFoundException 
     */
    public static void main(String... args) throws FileNotFoundException {
    	// Reader for the weather data
        CSVReader reader_weather = new CSVReader("MxT", "MnT", "src/main/resources/de/exxcellent/challenge/weather.csv");

        // Output result
        String dayWithSmallestTempSpread = reader_weather.finder.result_key;
        System.out.printf("Day with smallest temperature spread : %s%n", dayWithSmallestTempSpread);
        
        // Reader for the football data
        CSVReader reader_football = new CSVReader("Goals", "Goals Allowed", "src/main/resources/de/exxcellent/challenge/football.csv");

        // Output result
        String teamWithSmallestGoalSpread = reader_football.finder.result_key; // Your goal analysis function call …
        System.out.printf("Team with smallest goal spread       : %s%n", teamWithSmallestGoalSpread);
    }
}
