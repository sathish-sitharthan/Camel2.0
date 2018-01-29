package com.test.lykos.project.netty;

import org.apache.camel.builder.RouteBuilder;

/**
 * This example demonstrates several features of the QuickFIX/J component. It
 * uses QFJ session events to synchronize application behavior (e.g., Session
 * logon).
 * 
 */
public class MyRoute extends RouteBuilder {

	@Override
	public void configure() throws Exception {

		from("netty:tcp://localhost:8080").bean(new ServiceImpl(), "nettyMethod").log("Netty Server2 Reached")
				.to("netty:tcp://localhost:8081");
	}

}
