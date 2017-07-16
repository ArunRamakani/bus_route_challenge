/* @author Arun Ramakani  */
package arun.solve.route.challenge.busroutechallenge.dataholder;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 
 * DepartureArrivalsDataOptimizer is a helper class to convert simple map of
 * route with stations into a detailed map of Departure to all possible
 * arrivals
 * 
 * This optimized map will increase the memory footprint of the application, but
 * will provide quick response time for the restful service.
 * 
 * If we have requirement for bigger route data file then cache server like
 * redis or EVCache can be used.
 * 
 * This data structure is applicable only to find the if there is a direct
 * connection exist. any additional requirement need a changes to the data
 * structure
 * 
 **/

public class DepartureArrivalsDataOptimizer {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(DepartureArrivalsDataOptimizer.class.getName());

	/**
	 * 
	 * optimizeDepartureArrivalData : optimize and convert simple map of route
	 * with stations into a complex map of origin destination map
	 * 
	 * @param routeMap
	 *            : Map<Integer, ArrayList<Integer>> - Map of the route-stations
	 * @return Map<Integer, Set<Integer>> - Map of the Origin-destinations
	 * 
	 **/
	public static Map<Integer, Set<Integer>> optimizeDepartureArrivalData(final Map<Integer, ArrayList<Integer>> routeMap) {

		LOGGER.info("optimizeDepartureArrivalData called");
		// departure arrivals map
		Map<Integer, Set<Integer>> departureArrivalData = new HashMap<Integer, Set<Integer>>();

		// Loop for all the route with stations
		for (Integer route : routeMap.keySet()) {
			// all stations in the given route
			ArrayList<Integer> forwardDirectionStations = routeMap.get(route);
			// Exclude the last station since it does not have any further
			// connection
			ArrayList<Integer> subStationListExcludingLast = new ArrayList<Integer>(
					forwardDirectionStations.subList(0, forwardDirectionStations.size() - 1));

			// Index to keep get sub list of station from the looping station in
			// forward direction
			int subListIndex = 1;
			for (Integer station : subStationListExcludingLast) {
				// If the given departure station does not exist then create a new
				// map entry and update destinations for the departure station
				if (!departureArrivalData.containsKey(station)) {
					departureArrivalData.put(station, new HashSet<Integer>(
							forwardDirectionStations.subList(subListIndex, forwardDirectionStations.size())));
				} else {
					departureArrivalData.get(station)
							.addAll(forwardDirectionStations.subList(subListIndex, forwardDirectionStations.size()));
				}

				subListIndex++;
			}
		}
		LOGGER.info("Optimization completed successfully");
		return departureArrivalData;
	}

}
