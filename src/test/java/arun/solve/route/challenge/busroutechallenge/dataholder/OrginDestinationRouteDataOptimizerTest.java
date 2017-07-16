package arun.solve.route.challenge.busroutechallenge.dataholder;

import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.util.Map;
import java.util.Set;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.ResourceLoader;
import org.springframework.test.context.junit4.SpringRunner;

import arun.solve.route.challenge.busroutechallenge.dataholder.DepartureArrivalsDataOptimizer;
import arun.solve.route.challenge.busroutechallenge.exceptions.RouteFileInvalidFormatException;
import arun.solve.route.challenge.busroutechallenge.validation.RouteFileValidator;

@RunWith(SpringRunner.class)
@SpringBootTest
public class OrginDestinationRouteDataOptimizerTest {

	@Autowired
	private ResourceLoader resourceLoader;

	@Test
	public void testExampleRouteFile() throws Exception {
		try {

			Map<Integer, Set<Integer>> orginDestinationsMap = DepartureArrivalsDataOptimizer
					.optimizeDepartureArrivalData(RouteFileValidator
							.validateFile(resourceLoader.getResource("classpath:example").getFile().getAbsolutePath()));
			
			Integer orgin = 106;
			Integer destination1 = 11;
			Integer destination2 = 140;
			Integer destination3 = 12;
			
			assertTrue(orginDestinationsMap.containsKey(orgin));
			
			Set<Integer> destinations = orginDestinationsMap.get(orgin);
	
			assertTrue(destinations.contains(destination1));
			assertTrue(destinations.contains(destination2));
			assertTrue(destinations.contains(destination3));
			assertTrue(destinations.size() == 7);
			 
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (RouteFileInvalidFormatException e) {
			assertTrue(e.getMessage().equals("Number of route in the file does not match the head row"));
			throw e;
		}

	}

}
