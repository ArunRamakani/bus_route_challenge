package arun.solve.route.challenge.busroutechallenge.controller;

import static org.junit.Assert.*;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import arun.solve.route.challenge.busroutechallenge.response.BusRouteRes;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class DepartureArrivalRouteRestTest {
	
	static {
        System.setProperty("RouteFilePath", "data/example");
    }
	
	@Autowired
    private TestRestTemplate restTemplate;
	

	@Test
	public void testActualConnected() throws Exception{
		ResponseEntity<BusRouteRes> responseEntity =
	            restTemplate.getForEntity("/direct?dep_sid=174&arr_sid=17", BusRouteRes.class);
	            
		BusRouteRes client = responseEntity.getBody();
	    assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
	    assertTrue(client.isDirect_bus_route());
	        
	}
	
	@Test
	public void testActualConnected2() throws Exception{
		ResponseEntity<BusRouteRes> responseEntity =
	            restTemplate.getForEntity("/direct?dep_sid=153&arr_sid=138", BusRouteRes.class);
	            
		BusRouteRes client = responseEntity.getBody();
	    assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
	    assertTrue(client.isDirect_bus_route());
	    assertTrue(client.getArr_sid() == 138);
	    assertTrue(client.getDep_sid() == 153);
	        
	}
	
	@Test
	public void testNotConnectedStation() throws Exception{
		ResponseEntity<BusRouteRes> responseEntity =
	            restTemplate.getForEntity("/direct?dep_sid=17&arr_sid=174", BusRouteRes.class);
	            
		BusRouteRes client = responseEntity.getBody();
	    assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
	    assertFalse(client.isDirect_bus_route());
	        
	}

}
