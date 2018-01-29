package com.lykos.quickfix;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import org.apache.camel.Exchange;
import org.apache.camel.ExchangePattern;
import org.apache.camel.Producer;
import org.apache.camel.builder.PredicateBuilder;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.component.quickfixj.QuickfixjEndpoint;
import org.apache.camel.component.quickfixj.QuickfixjEventCategory;
import org.apache.camel.impl.DefaultCamelContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import quickfix.field.EmailThreadID;
import quickfix.field.EmailType;
import quickfix.field.MsgType;
import quickfix.field.Subject;
import quickfix.field.Text;
import quickfix.fix42.BidRequest;
import quickfix.fix42.Email;

/**
 * This example demonstrates several features of the QuickFIX/J component. It
 * uses QFJ session events to synchronize application behavior (e.g., Session
 * logon).
 * 
 */
public class SimpleMessagingExample {
	private static final Logger LOG = LoggerFactory.getLogger(SimpleMessagingExample.class);

	public static void main(String[] args) throws Exception {
		new SimpleMessagingExample().sendMessage();
	}

	public void sendMessage() throws Exception {
		DefaultCamelContext context = new DefaultCamelContext();

		final CountDownLatch logonLatch = new CountDownLatch(2);
		final CountDownLatch receivedMessageLatch = new CountDownLatch(1);	
		final CountDownLatch receivedMessageLatch2 = new CountDownLatch(1);	

		RouteBuilder routes = new RouteBuilder() {
			@Override
			public void configure() throws Exception {
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
						.bean(new QuickfixjMessageJsonPrinter());

				// If the market session receives an email then release the
				// latch
				from("quickfix:inprocess.cfg?sessionID=FIX.4.2:MARKET->TRADER")
						.filter(header(QuickfixjEndpoint.MESSAGE_TYPE_KEY).isEqualTo(MsgType.EMAIL))
						.bean(new CountDownLatchDecrementer("message", receivedMessageLatch));		
				
				from("quickfix:inprocess.cfg?sessionID=FIX.4.2:TRADER->MARKET")
				.filter(header(QuickfixjEndpoint.MESSAGE_TYPE_KEY).isEqualTo(MsgType.EMAIL))
				.bean(new CountDownLatchDecrementer("message1", receivedMessageLatch2));	*/	
				
				  from("netty:tcp://localhost:8080")
		           .log("Netty Success");
			
			}
		};

		context.addRoutes(routes);

		LOG.info("Starting Camel context");
		context.start();

		if (!logonLatch.await(5L, TimeUnit.SECONDS)) {
			throw new IllegalStateException("Logon did not succeed");
		}

		String marketUri = "quickfix:inprocess.cfg?sessionID=FIX.4.2:TRADER->MARKET";
		Producer producer = context.getEndpoint(marketUri).createProducer();

		Email email = createEmailMessage("Example");
		Exchange exchange = producer.createExchange(ExchangePattern.InOnly);
		exchange.getIn().setBody(email);
		producer.process(exchange);		
		
		String marketUri1 = "quickfix:inprocess.cfg?sessionID=FIX.4.2:MARKET->TRADER";
		Producer producer1 = context.getEndpoint(marketUri1).createProducer();		
		
		Exchange exchange1 = producer1.createExchange(ExchangePattern.InOnly);
		exchange1.getIn().setBody(email);
		producer1.process(exchange1);		

		if (!receivedMessageLatch.await(5L, TimeUnit.SECONDS)) {
			throw new IllegalStateException("Message did not reach market");
		}	
		
		if (!receivedMessageLatch2.await(5L, TimeUnit.SECONDS)) {
			throw new IllegalStateException("Message did not reach trader");
		}	

		LOG.info("Message received, shutting down Camel context");

		context.stop();

		LOG.info("Example complete");
	}

	public static Email createEmailMessage(String subject) {
		Email email = new Email(new EmailThreadID("ID"), new EmailType(EmailType.NEW), new Subject(subject));
		Email.LinesOfText text = new Email.LinesOfText();
		text.set(new Text("Content"));
		email.addGroup(text);
		return email;
	}
}
