/* @author Arun Ramakani  */
package arun.solve.route.challenge.busroutechallenge.service;

/**
 * 
 * DepartureArrivalRouteService - Interface definition of service to find
 * connected departure and arrival station
 * 
 **/
public interface DepartureArrivalRouteService {
	public boolean findDirectBusRoute(final int departure, final int arrival);
}
