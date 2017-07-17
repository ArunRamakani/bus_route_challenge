/* @author Arun Ramakani  */
package arun.solve.route.challenge.busroutechallenge.validation;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.regex.Pattern;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import arun.solve.route.challenge.busroutechallenge.exceptions.FileErrorType;
import arun.solve.route.challenge.busroutechallenge.exceptions.RouteFileInvalidFormatException;

/**
 * 
 * RouteFileValidator holds methods to validate the file with all the rules
 * provided RouteFileValidator converts the file in to a map of route number
 * with respective station array
 * 
 **/
public class RouteFileValidator {

	private static final Logger LOGGER = LoggerFactory.getLogger(RouteFileValidator.class.getName());
	
	public static final int MAX_ROUTS = 100000;
	public static final int MAX_STATIONS = 1000000;
	public static final int MAX_STATIONS_PER_ROUTE = 1000;
	public static final int MIN_ROUTE_ROW_TOKENS = 3;

	/**
	 * 
	 * validateFile : Validates the file and convert it to map
	 * 
	 * @param filePath
	 *            : String - Location of the bus route file
	 * @return Map<Integer, ArrayList<Integer>> - Map of the route number with
	 *         respective station array
	 * 
	 **/
	public static Map<Integer, ArrayList<Integer>> validateFile(final String filePath) throws RouteFileInvalidFormatException {

		Map<Integer, ArrayList<Integer>>  routeMap = null;
		// Check if we are able to locate, read the file
		try {
			BufferedReader bufferedReader = new BufferedReader(new FileReader(filePath));
			ArrayList<String> stringArray = validateRouteCount(bufferedReader);
			bufferedReader.close();
			routeMap = validateRouteInformations(stringArray);
			
		} catch (IOException e) {
			LOGGER.error("Not able to reade the file");
			e.printStackTrace();
			throw new RouteFileInvalidFormatException("Not able to reade the file",FileErrorType.FILE_READE_ISSUE);
		}
		
		// Validate the file content for compliance
		return routeMap;

	}

	/**
	 * 
	 * validateRouteCount : Validates the route count for all business rules
	 * 
	 * @param scannedFile
	 *            : BufferedReader - scanned route details files
	 * @return ArrayList<String> - List of route rows in string format
	 * 
	 **/
	private static ArrayList<String> validateRouteCount(final BufferedReader scannedFile) throws RouteFileInvalidFormatException {

		int noOfRoutes;
		final ArrayList<String> routeLineArray = new ArrayList<String>();

		// Check if the file has a head row
		String headLine = null;
		try {
			headLine = scannedFile.readLine();
		} catch (IOException e1) {
			e1.printStackTrace();
			throw new RouteFileInvalidFormatException("Error in reading the route file head", FileErrorType.FILE_READE_ISSUE);
		}
		if (headLine == null)
			throw new RouteFileInvalidFormatException("Route File Does not have a head row", FileErrorType.FILE_READE_ISSUE);

		// Validate if the head row represents a proper number of rout
		try {
			noOfRoutes = Integer.parseInt(headLine.trim());
			LOGGER.info(String.format("Number of route mentioned in the head : %d", noOfRoutes));
		} catch (NumberFormatException e) {
			throw new RouteFileInvalidFormatException("Head row does not express a valid number of route", FileErrorType.INVALID_CONTENT_IN_FILE);
		}

		// Check if the head row represents a value a more than 1 and bounds
		// within the maximum route limit
		if (noOfRoutes < 1 || noOfRoutes > MAX_ROUTS)
			throw new RouteFileInvalidFormatException("Number of route in the head is not valid", FileErrorType.INVALID_ROUTE_COUNT);

		// Check if the actual route count matches the head route count
		int numberOfLines = 0;
		String routeLine = null;
		try {
			while ((routeLine = scannedFile.readLine()) != null) {
				// Ignore empty lines
				if(routeLine.trim().length() > 0){
					numberOfLines++;
					routeLineArray.add(routeLine);
				}
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new RouteFileInvalidFormatException("Error in reading the route file route rows" , FileErrorType.FILE_READE_ISSUE);
		}

		LOGGER.info(String.format("Number of route lines after the head line : %d", numberOfLines));
		if (noOfRoutes != numberOfLines)
			throw new RouteFileInvalidFormatException("Number of route in the file does not match the head row",FileErrorType.ROUTE_COUNT_MISSMATCH );

		LOGGER.info("RouteCount verification completed successfully");

		// Validate individual route
		return routeLineArray;
	}

	/**
	 * 
	 * validateRouteInformations : Validates the route row for all business
	 * rules
	 * 
	 * @param routeLineArray
	 *            : ArrayList<String> - List of route rows in string format
	 * @return Map<Integer, ArrayList<Integer>> - Map of the route number with
	 *         respective station array
	 * 
	 **/
	private static Map<Integer, ArrayList<Integer>> validateRouteInformations(final ArrayList<String> routeLineArray) throws RouteFileInvalidFormatException{

		// Map of the route number with respective station array
		final Map<Integer, ArrayList<Integer>> routeDetailsMap = new HashMap<Integer, ArrayList<Integer>>();

		// Unique route number holder to identify duplicate route numbers
		final Set<Integer> uniqueRouteId = new HashSet<Integer>();
		
		// Unique station number to identify max count
		final Set<Integer> uniqueStationId = new HashSet<Integer>();

		// Pattern to split route row with single/multiple spaces
		final Pattern routeSplitPattern = Pattern.compile("\\s+");

		// Convert route string to a map of route number with respective station
		// array
		for (String routeLine : routeLineArray) {

			// Unique station number holder to identify duplicate station in a
			// given route
			final ArrayList<Integer> uniqueStationIds = new ArrayList<Integer>();

			// Route id of the current loop
			Integer routeId;

		
			// Split the route row with pattern
			ArrayList<String> splittedRouteLine = new ArrayList<String>(
					Arrays.asList(routeSplitPattern.split(routeLine)));

			// Check if the number of tokens in the route row falls with in the
			// limit
			if (splittedRouteLine.size() < MIN_ROUTE_ROW_TOKENS)
				throw new RouteFileInvalidFormatException(
						String.format("Number of integers in route row should be minimum of 3", MIN_ROUTE_ROW_TOKENS), FileErrorType.INVALID_MINIMUM_ROUTE_TOKENS);

			try {
				routeId = Integer.parseInt(splittedRouteLine.get(0));

				// Identify duplicate route number
				if (uniqueRouteId.contains(routeId))
					throw new RouteFileInvalidFormatException(String.format("Route No : %d already exist in the file", routeId),FileErrorType.DUPLICATE_ROUTE);
				uniqueRouteId.add(routeId);

				// Identify if the station count does not exceed the range
				ArrayList<String> stationArray = new ArrayList<String>(
						splittedRouteLine.subList(1, splittedRouteLine.size()));
				if (stationArray.size() > MAX_STATIONS_PER_ROUTE)
					throw new RouteFileInvalidFormatException(
							String.format("Route No : %d contains more than %d stations.", routeId, MAX_STATIONS_PER_ROUTE), FileErrorType.MAX_STATION_COUNT_EXCEEDS_PER_ROUTE);

				// Check if there is no duplicate station id and create a array
				// list of stations
				for (String stationId : stationArray) {
					Integer station = Integer.parseInt(stationId);
					if (uniqueStationIds.contains(station))
						throw new RouteFileInvalidFormatException(String
								.format("The station number : %d already exist in the route : %d", station, routeId), FileErrorType.DUPLICATE_STATION_IN_ROUTE);
					uniqueStationIds.add(station);
				}

			} catch (NumberFormatException e) {
				// Called when any of the route row token (Route ID / Station
				// ID) is not a valid integer.
				throw new RouteFileInvalidFormatException("Route row consist of non integer element", FileErrorType.INVALID_CONTENT_IN_FILE);

			}
			routeDetailsMap.put(routeId, uniqueStationIds);
			uniqueStationId.addAll(uniqueStationIds);
		}
		
		if(uniqueStationId.size() > MAX_STATIONS) 
			throw new RouteFileInvalidFormatException(
					String.format("Exceeds the allowd number of maximum stations : %d", MAX_STATIONS), FileErrorType.MAX_STATION_COUNT_EXCEEDS);
		
		return routeDetailsMap;
	}
}
