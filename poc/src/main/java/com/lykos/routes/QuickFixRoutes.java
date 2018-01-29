package com.lykos.routes;

import java.util.concurrent.CountDownLatch;

import org.apache.camel.Exchange;
import org.apache.camel.ExchangePattern;
import org.apache.camel.Processor;
import org.apache.camel.Producer;
import org.apache.camel.builder.PredicateBuilder;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.component.quickfixj.QuickfixjEndpoint;
import org.apache.camel.component.quickfixj.QuickfixjEventCategory;
import org.apache.camel.impl.DefaultCamelContext;

import com.lykos.model.MyLogBean;
import com.lykos.quickfix.CountDownLatchDecrementer;
import com.lykos.quickfix.QuickfixjMessageJsonPrinter;

import quickfix.field.EmailThreadID;
import quickfix.field.EmailType;
import quickfix.field.MsgType;
import quickfix.field.Subject;
import quickfix.field.Text;
import quickfix.fix42.Email;

public class QuickFixRoutes extends RouteBuilder{
	
	final CountDownLatch logonLatch = new CountDownLatch(2);
	final CountDownLatch receivedMessageLatch = new CountDownLatch(1);	
	final CountDownLatch receivedMessageLatch2 = new CountDownLatch(1);	

	@Override
	public void configure() throws Exception {
		final DefaultCamelContext context = new DefaultCamelContext();
		
		

		// Release latch when session logon events are received
		// We expect two events, one for the trader session and one for
		// the market session
		/*from("quickfix:inprocess.cfg")
				.filter(header(QuickfixjEndpoint.EVENT_CATEGORY_KEY)
						.isEqualTo(QuickfixjEventCategory.SessionLogon))
				.bean(new CountDownLatchDecrementer("logon", logonLatch));

		// For all received messages, print the JSON-formatted message
		// to stdout
		from("quickfix:inprocess.cfg")
				.filter(PredicateBuilder.or(
						header(QuickfixjEndpoint.EVENT_CATEGORY_KEY)
								.isEqualTo(QuickfixjEventCategory.AdminMessageReceived),
						header(QuickfixjEndpoint.EVENT_CATEGORY_KEY)
								.isEqualTo(QuickfixjEventCategory.AppMessageReceived)))
				.bean(new QuickfixjMessageJsonPrinter());*/

		// If the market session receives an email then release the
		// latch
		
		from("vm:testCtx")	    
		.log("you are in here");
			
		
		from("quickfix:inprocess.cfg?sessionID=FIX.4.2:MARKET->TRADER")
				.filter(header(QuickfixjEndpoint.MESSAGE_TYPE_KEY).isEqualTo(MsgType.EMAIL))
				.bean(new CountDownLatchDecrementer("message", receivedMessageLatch));		
		
		/*from("quickfix:inprocess.cfg?sessionID=FIX.4.2:TRADER->MARKET")
		.filter(header(QuickfixjEndpoint.MESSAGE_TYPE_KEY).isEqualTo(MsgType.EMAIL))
		.bean(new CountDownLatchDecrementer("message1", receivedMessageLatch2));	*/
	
	
		
	}
	
	

}
