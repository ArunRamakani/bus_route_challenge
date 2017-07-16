/* @author Arun Ramakani  */
package arun.solve.route.challenge.busroutechallenge.dataholder;

import java.util.Map;
import java.util.Set;

import lombok.Data;

/**
 * 
 * DepartureArrivalsDataHolder acts as a in-memory cache data holder of
 * Departure-Arrivals map
 * 
 **/

@Data
public class DepartureArrivalsDataHolder {

	// departure arrivals map
	private final Map<Integer, Set<Integer>> departureArrivalsMap;

	// Constructor to receive departure arrivals map
	public DepartureArrivalsDataHolder(final Map<Integer, Set<Integer>> departureArrivalsMap) {
		this.departureArrivalsMap = departureArrivalsMap;
	}

}
