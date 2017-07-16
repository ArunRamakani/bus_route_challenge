/* @author Arun Ramakani  */
package arun.solve.route.challenge.busroutechallenge;

import java.util.ArrayList;
import java.util.Map;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import arun.solve.route.challenge.busroutechallenge.dataholder.DepartureArrivalsDataHolder;
import arun.solve.route.challenge.busroutechallenge.dataholder.DepartureArrivalsDataOptimizer;
import arun.solve.route.challenge.busroutechallenge.exceptions.RouteFileInvalidFormatException;
import arun.solve.route.challenge.busroutechallenge.validation.RouteFileValidator;

/**
 * 
 * BusRouteChallengeApplication - Start of the Spring Boot application
 * 
 **/
@SpringBootApplication
public class BusRouteChallengeApplication {

	private static final Logger LOGGER = LoggerFactory.getLogger(BusRouteChallengeApplication.class.getName());

	// Application starts here 
	public static void main(String[] args) {
		SpringApplication.run(BusRouteChallengeApplication.class, args);	
	}

	// Singleton bean that holds cache data structure to provide quick response time for rest service
	@Bean
	public DepartureArrivalsDataHolder getDepartureArrivalsDataHolder() throws Exception {

		Map<Integer, ArrayList<Integer>> routeMap = null;
		Map<Integer, Set<Integer>> departureArrivalsData = null;

		try {
			// Validate the file and create map of route and stations
			routeMap = RouteFileValidator.validateFile(System.getProperty("RouteFilePath"));
			// Create cache of departure and Arrival stations
			departureArrivalsData = DepartureArrivalsDataOptimizer.optimizeDepartureArrivalData(routeMap);
			LOGGER.error("Successfully created a cache data structure");
		} catch (RouteFileInvalidFormatException e) {
			LOGGER.error("Error while validating the file");
			throw e;
		} catch (Exception e) {
			LOGGER.error("Exception in initial file processing and data optimization");
			throw e;
		}

		return new DepartureArrivalsDataHolder(departureArrivalsData);
	}
}
