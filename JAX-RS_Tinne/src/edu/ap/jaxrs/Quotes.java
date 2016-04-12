package edu.ap.jaxrs;

import java.util.List;
import java.util.Set;
import javax.ws.rs.*;
import redis.clients.jedis.Jedis;

@Path("/quotes")
public class Quotes {
	Jedis jedis = new Jedis("localhost");
	
	@GET
	@Produces({"text/html"})
	public String getProductsHTML() {
		String htmlString = "<html><body>";
		try {
		    
		    List<String> quotes = jedis.keys("author:*");
		    
		    int i = 0;
			for(String string : quotes) {
				i ++;
				Set<String> values = jedis.smembers("quote:" + i);
				for(String val : values) {
					htmlString += val + "<br>";
				}
			}
		}
		catch(Exception ex) {
			htmlString = "<html><body>" + ex.getMessage();
		}
		
		return htmlString + "</body></html>";
	}
	
	@POST
	@Produces({"text/html"})
	public String postQuotesHTML(String author_name) {
		String htmlString = "<html><body>";
		try {
		    List<String> quotes = jedis.keys("author:*");
		    
			for(String string : quotes) {
				String author = jedis.get(string);
				if (author_name == author)
				{
					Set<String> values = jedis.smembers("quote:" + string);
					for(String val : values) {
						htmlString += val + "<br>";
					}
				}
			}
		}
		catch(Exception ex) {
			htmlString = "<html><body>" + ex.getMessage();
		}
		
		return htmlString + "</body></html>";
	}
}
