package arun.solve.route.challenge.busroutechallenge.service;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class DepartureArrivalRouteServiceTest {
	
	
	static {
        System.setProperty("RouteFilePath", "data/example");
    }
	
	@Autowired
	private DepartureArrivalRouteService departureArrivalRouteService;
	

	@Test
	public void testService() {
		assertTrue(departureArrivalRouteService.findDirectBusRoute(153, 138));
		assertFalse(departureArrivalRouteService.findDirectBusRoute(17, 174));
		assertTrue(departureArrivalRouteService.findDirectBusRoute(174, 17));
		
	}
	

}
