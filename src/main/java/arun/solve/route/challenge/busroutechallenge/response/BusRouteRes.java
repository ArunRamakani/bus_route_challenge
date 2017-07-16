/* @author Arun Ramakani  */
package arun.solve.route.challenge.busroutechallenge.response;

import lombok.Data;

/**
 * 
 * BusRouteRes - rest service response pojo
 * 
 **/
@Data
public class BusRouteRes {

	// Departure
	int dep_sid;
	// Arrival
	int arr_sid;
	// Connected status
	boolean direct_bus_route;

	// Default constructor
	public BusRouteRes() {
		
	}
	
	// over
	public BusRouteRes(final int dep_sid, final int arr_sid, final boolean direct_bus_route) {
		this.dep_sid = dep_sid;
		this.arr_sid = arr_sid;
		this.direct_bus_route = direct_bus_route;
	}

}
