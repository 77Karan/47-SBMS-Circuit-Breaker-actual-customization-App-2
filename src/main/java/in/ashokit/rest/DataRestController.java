package in.ashokit.rest;

import java.util.Random;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;

@RestController
public class DataRestController
{
	@GetMapping("/data")
	@HystrixCommand(
			fallbackMethod= "getDataFromDB",
			commandProperties= {
					@HystrixProperty(name="circuitBreaker.requestVolumeThreshold",value="5"),
					@HystrixProperty(name="circuitBreaker.sleepWindowInMilliseconds",value="10000"),
					@HystrixProperty(name="circuitBreaker.enabled",value="true")
			})
	public String getDatafromRedies()
	{
		System.out.println("Executing getDataFromRedies()  method...");
		
		if(new Random().nextInt(10)<=10)
		{
			throw new RuntimeException("Redies Server is down");
		}
		return "Data is fetching from Redies";
	}
	
	public String getDataFromDB()
	{
		System.out.println("getDataFromDB() method is called");
		return "data accessed from database";
	}

}
