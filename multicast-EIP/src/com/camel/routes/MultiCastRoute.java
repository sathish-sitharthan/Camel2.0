package com.camel.routes;

import org.apache.camel.builder.RouteBuilder;

public class MultiCastRoute extends RouteBuilder {

	@Override
	public void configure() throws Exception {
		
		from("direct:start")		
			.multicast()
				.parallelProcessing()
					.to("direct:getOrder","direct:validateOrder","direct:confirmOrder")
						.end() 
							.to("mock:result");
		
		from("direct:getOrder")
			.to("bean:getOrder?method=getOrder");
		
		from("direct:validateOrder")
			.to("bean:validateOrder?method=validateOrder");	
		
		from("direct:confirmOrder")
			.to("bean:confirmOrder?method=confirmOrder");	
	}
}
