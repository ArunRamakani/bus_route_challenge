package arun.solve.route.challenge.busroutechallenge.validation;

import static org.junit.Assert.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.ResourceLoader;
import org.springframework.test.context.junit4.SpringRunner;

import arun.solve.route.challenge.busroutechallenge.exceptions.FileErrorType;
import arun.solve.route.challenge.busroutechallenge.exceptions.RouteFileInvalidFormatException;
import arun.solve.route.challenge.busroutechallenge.validation.RouteFileValidator;

@RunWith(SpringRunner.class)
@SpringBootTest
public class RouteFileValidatorTest {

	@Autowired
	private ResourceLoader resourceLoader;

	@Test
	public void testExampleRouteFile() throws Exception {

		try {

			Map<Integer, ArrayList<Integer>> mapOfRouts = RouteFileValidator
					.validateFile(resourceLoader.getResource("classpath:example").getFile().getAbsolutePath());
			assertEquals(10, mapOfRouts.keySet().size());
			assertTrue(mapOfRouts.containsKey(18));
			assertFalse(mapOfRouts.containsKey(50));

			ArrayList<Integer> routeNo19 = mapOfRouts.get(19);
			assertTrue(routeNo19.size() == 5);
			assertTrue(routeNo19.contains(114));
			assertTrue(routeNo19.contains(153));
			assertTrue(routeNo19.contains(5));
			assertFalse(routeNo19.contains(200));

			ArrayList<Integer> routeNo7 = mapOfRouts.get(7);
			assertTrue(routeNo7.size() == 7);

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (RouteFileInvalidFormatException e) {
			assertTrue(e.getMessage().equals("Number of route in the file does not match the head row"));
			throw e;
		}

	}
	
	@Test
	public void testRouteRowMinToken() throws Exception {

		try {
			Map<Integer, ArrayList<Integer>> mapOfRouts = RouteFileValidator
					.validateFile(resourceLoader.getResource("classpath:RouteRowMinToken").getFile().getAbsolutePath());
			ArrayList<Integer> routeNo7 = mapOfRouts.get(18);
			assertTrue(routeNo7.size() == 2);

		} catch (IOException e) {
			e.printStackTrace();
		} catch (RouteFileInvalidFormatException e) {
			throw e;
		}
	}
	
	@Test
	public void testMaxRoute() throws Exception {

		try {
			Map<Integer, ArrayList<Integer>> mapOfRouts = RouteFileValidator.validateFile(
					resourceLoader.getResource("classpath:MaxRoute").getFile().getAbsolutePath());
			assertTrue(mapOfRouts.keySet().size() == RouteFileValidator.MAX_ROUTS);

		} catch (IOException e) {
			e.printStackTrace();
		} catch (RouteFileInvalidFormatException e) {
			throw e;
		}
	}
	
//	@Test
//	public void testMaxStationTotal() throws Exception {
//
//		try {
//			Map<Integer, ArrayList<Integer>> mapOfRouts = RouteFileValidator.validateFile(
//					resourceLoader.getResource("classpath:MaxStationTotal").getFile().getAbsolutePath());
//			assertTrue(mapOfRouts.keySet().size() == RouteFileValidator.MAX_ROUTS);
//			
//
//		} catch (IOException e) {
//			e.printStackTrace();
//		} catch (RouteFileInvalidFormatException e) {
//			throw e;
//		}
//	}
	
	
	@Test(expected = RouteFileInvalidFormatException.class)
	public void testRountCountMismatch() throws Exception {

		try {
			RouteFileValidator.validateFile(
					resourceLoader.getResource("classpath:RountCountMismatch").getFile().getAbsolutePath());
		} catch (IOException e) {
			e.printStackTrace();
		} catch (RouteFileInvalidFormatException e) {
			assertTrue(e.getErrorType() == FileErrorType.ROUTE_COUNT_MISSMATCH);
			throw e;
		}

	}

	@Test(expected = RouteFileInvalidFormatException.class)
	public void testDuplicateStation() throws Exception {

		try {
			RouteFileValidator
					.validateFile(resourceLoader.getResource("classpath:DuplicateStation").getFile().getAbsolutePath());
		} catch (IOException e) {
			e.printStackTrace();
		} catch (RouteFileInvalidFormatException e) {
			assertTrue(e.getErrorType() == FileErrorType.DUPLICATE_STATION_IN_ROUTE);
			throw e;
		}
	}

	@Test(expected = RouteFileInvalidFormatException.class)
	public void testZeroRow() throws Exception {

		try {
			RouteFileValidator
					.validateFile(resourceLoader.getResource("classpath:ZeroRow").getFile().getAbsolutePath());
		} catch (IOException e) {
			e.printStackTrace();
		} catch (RouteFileInvalidFormatException e) {
			assertTrue(e.getErrorType() == FileErrorType.INVALID_ROUTE_COUNT);
			throw e;
		}
	}

	@Test(expected = RouteFileInvalidFormatException.class)
	public void testDuplicateRoute() throws Exception {

		try {
			RouteFileValidator
					.validateFile(resourceLoader.getResource("classpath:DuplicateRoute").getFile().getAbsolutePath());
		} catch (IOException e) {
			e.printStackTrace();
		} catch (RouteFileInvalidFormatException e) {
			assertTrue(e.getErrorType() == FileErrorType.DUPLICATE_ROUTE);
			throw e;
		}
	}

	@Test(expected = RouteFileInvalidFormatException.class)
	public void testRouteRowLessToken() throws Exception {

		try {
			RouteFileValidator.validateFile(
					resourceLoader.getResource("classpath:RouteRowLessToken").getFile().getAbsolutePath());
		} catch (IOException e) {
			e.printStackTrace();
		} catch (RouteFileInvalidFormatException e) {
			assertTrue(e.getErrorType() == FileErrorType.INVALID_MINIMUM_ROUTE_TOKENS);
			throw e;
		}
	}

	@Test
	public void testMaxStationLimit() throws Exception {

		try {
			Map<Integer, ArrayList<Integer>> mapOfRouts = RouteFileValidator.validateFile(
					resourceLoader.getResource("classpath:MaxRouteStation").getFile().getAbsolutePath());
			ArrayList<Integer> routeNo19 = mapOfRouts.get(19);
			assertTrue(routeNo19.size() == RouteFileValidator.MAX_STATIONS_PER_ROUTE);

		} catch (IOException e) {
			e.printStackTrace();
		} catch (RouteFileInvalidFormatException e) {
			throw e;
		}
	}

	@Test(expected = RouteFileInvalidFormatException.class)
	public void testMoreThanMaxStationLimit() throws Exception {

		try {
			RouteFileValidator.validateFile(
					resourceLoader.getResource("classpath:MoreThanMaxRouteStation").getFile().getAbsolutePath());

		} catch (IOException e) {
			e.printStackTrace();
		} catch (RouteFileInvalidFormatException e) {
			assertTrue(e.getErrorType() == FileErrorType.MAX_STATION_COUNT_EXCEEDS_PER_ROUTE);
			throw e;
		}
	}
	

	@Test(expected = RouteFileInvalidFormatException.class)
	public void testMoreThanMaxRoute() throws Exception {

		try {
			RouteFileValidator.validateFile(
					resourceLoader.getResource("classpath:MoreThanMaxRoute").getFile().getAbsolutePath());

		} catch (IOException e) {
			e.printStackTrace();
		} catch (RouteFileInvalidFormatException e) {
			assertTrue(e.getErrorType() == FileErrorType.INVALID_ROUTE_COUNT);
			throw e;
		}
	}

}
