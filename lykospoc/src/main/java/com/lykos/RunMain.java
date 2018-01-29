package com.lykos;

import java.util.HashMap;
import java.util.Map;

import org.apache.activemq.ActiveMQConnection;
import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.camel.CamelContext;
import org.apache.camel.component.jms.JmsComponent;
import org.apache.camel.impl.DefaultCamelContext;
import org.apache.camel.impl.SimpleRegistry;
import org.apache.camel.main.Main;

import com.lykos.routes.QuickFixRoutes;
import com.lykos.routes.ServiceRoute;
import com.lykos.services.ServiceImpl;

/**
 * A main class to try to run this example
 */
public class RunMain {

	Map<String, CamelContext> answer = new HashMap<String, CamelContext>();

	public static void main(String[] args) throws Exception {
		new RunMain().run();
	}

	public void run() throws Exception {
	

		SimpleRegistry registry = new SimpleRegistry();
		registry.put("service", new ServiceImpl());

		CamelContext ctx = new DefaultCamelContext(registry);
		//CamelContext ctx2 = new DefaultCamelContext();

		ActiveMQConnectionFactory connectionFactory = new ActiveMQConnectionFactory(
				ActiveMQConnection.DEFAULT_BROKER_URL);
		connectionFactory.setTrustAllPackages(true);
		ctx.addComponent("jms", JmsComponent.jmsComponentAutoAcknowledge(connectionFactory));

		ctx.addRoutes(new ServiceRoute());
		//ctx2.addRoutes(new QuickFixRoutes());

		System.out.println("Application Running..................");
		ctx.start();
		//ctx2.start();

		

	}

}
