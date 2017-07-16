/* @author Arun Ramakani  */
package arun.solve.route.challenge.busroutechallenge.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import arun.solve.route.challenge.busroutechallenge.dataholder.DepartureArrivalsDataHolder;

/**
 * 
 * DepartureArrivalRouteService - Implementation of service to find connected
 * departure and arrival station
 * 
 **/
@Service("departureArrivalRouteService")
public class DepartureArrivalRouteServiceImpl implements DepartureArrivalRouteService {

	private static final Logger LOGGER = LoggerFactory.getLogger(DepartureArrivalRouteServiceImpl.class.getName());

	// reference to the in-memory data holder bean
	@Autowired
	private DepartureArrivalsDataHolder departureArrivalsDataHolder;

	/**
	 * 
	 * findDirectBusRoute : find if direct route exist between two stations
	 * 
	 * @param departure
	 *            : int - departure station id
	 * @param arrival
	 *            : int - Arrival station id
	 * @return boolean - expresses if direct connection exist between the given
	 *         departure and Arrival
	 * 
	 **/
	@Override
	public boolean findDirectBusRoute(final int departure, final int arrival) {

		LOGGER.info("findDirectBusRoute service called");
		if (departureArrivalsDataHolder.getDepartureArrivalsMap().containsKey(departure)
				&& departureArrivalsDataHolder.getDepartureArrivalsMap().get(departure).contains(arrival))
			return true;

		LOGGER.info("Direct connection does not exist");
		return false;
	}

}
