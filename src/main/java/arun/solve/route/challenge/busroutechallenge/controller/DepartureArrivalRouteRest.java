/* @author Arun Ramakani  */
package arun.solve.route.challenge.busroutechallenge.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import arun.solve.route.challenge.busroutechallenge.response.BusRouteRes;
import arun.solve.route.challenge.busroutechallenge.service.DepartureArrivalRouteService;

/**
 * 
 * DepartureArrivalRouteRest is a rest end point class with method to find if
 * direct route exist between two stations
 * 
 **/
@RestController
public class DepartureArrivalRouteRest {

	private static final Logger LOGGER = LoggerFactory.getLogger(DepartureArrivalRouteRest.class.getName());
	
	@Autowired
	private DepartureArrivalRouteService departureArrivalRouteService;

	/**
	 * 
	 * findDirectBusRoute : find if direct route exist between two stations
	 * 
	 * @param dep_sid
	 *            : int - departure station id
	 * @param arr_sid
	 *            : int - Arrival station id
	 * @return BusRouteRes - json string with departure & Arrival station id
	 *         with status if direct connection exist
	 * 
	 **/
	@RequestMapping(value = "/direct", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody BusRouteRes findDirectBusRoute(
			@RequestParam(value = "dep_sid", required = true) final int dep_sid,
			@RequestParam(value = "arr_sid", required = true) final int arr_sid) {
		LOGGER.info(String.format("findDirectBusRoute rest method called with departure %d and arraival %d", dep_sid, arr_sid));
		return new BusRouteRes(dep_sid, arr_sid, departureArrivalRouteService.findDirectBusRoute(dep_sid, arr_sid));
	}
}
