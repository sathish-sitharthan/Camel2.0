package com.lykos.routes;

import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.model.rest.RestBindingMode;
import org.springframework.stereotype.Component;

import com.lykos.model.MyLogBean;
import com.lykos.model.Product;
import com.lykos.security.JwtFilter;

import quickfix.field.EmailThreadID;
import quickfix.field.EmailType;
import quickfix.field.Subject;
import quickfix.field.Text;
import quickfix.fix42.Email;

@Component 
public class ServiceRoute extends RouteBuilder { 	 
	
	@Override
	public void configure() throws Exception {	 
        
		restConfiguration()		
			.component("undertow").port(8081)		
				.bindingMode(RestBindingMode.json);		
						
		rest("/service")		
			.get("{id}").outType(String.class)
				.to("direct:get")			
					.post().type(Product.class)				
						.to("direct:post");	  		
		
		from("direct:get").routeId("GetProducer")	
	    	.filter()
	    		.method(new JwtFilter(), "filter")
	    			.to("direct:jwtFilter")	    				
	    					.wireTap("direct:tap")	     						
	    							.to("bean:service?method=getProduct(${header.id})")	    								  							    						 	
	    								.to("jms:queue:javainuse");
		
		/*from("jms:queue:javainuse")
		 	.choice()		 
		 		.when().body(Product.class)
		 			.enrich("bean:service?method=getProductEnrich")
						.to("bean:service?method=createProduct");*/
		
	/*	from("jms:queue:javainuse")	    
	    		.to("vm:testCtx");*/
		
		from("jms:queue:javainuse")	   
		.log("Calling Netty")
		.to("netty:tcp://localhost:8080");
		
		/*from("netty:tcp://localhost:8081")	    
		.log("response came");
				  	*/
		
		from("direct:post").routeId("PostProduct")		
    		.filter()
    			.method(new JwtFilter(), "filter")
    				.to("direct:jwtFilter")    						    		
    						.wireTap("direct:tap")    						 
    					 		.to("bean:service?method=createProduct");	
    						
				
		from("direct:tap")         
         		.bean(MyLogBean.class, "logit");       			
		
		from("direct:jwtFilter")
		 	.bean(JwtFilter.class,"filter");

								 
	}
	
	public static Email createEmailMessage(String subject) {
		Email email = new Email(new EmailThreadID("ID"), new EmailType(EmailType.NEW), new Subject(subject));
		Email.LinesOfText text = new Email.LinesOfText();
		text.set(new Text("Content"));
		email.addGroup(text);
		return email;
	}
	
}
