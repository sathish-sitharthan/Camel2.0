package com.test.lykos.project;

import org.apache.camel.CamelContext;
import org.apache.camel.impl.DefaultCamelContext;
import org.apache.camel.impl.SimpleRegistry;

import com.test.lykos.project.netty.MyRoute;
import com.test.lykos.project.netty.ServiceImpl;

/**
 * Hello world!
 *
 */
public class App {
	public static void main(String[] args) throws Exception {
		new App().run();
	}

	public void run() throws Exception {
		SimpleRegistry registry = new SimpleRegistry();
		registry.put("service", new ServiceImpl());
		
		CamelContext ctx = new DefaultCamelContext(registry);

		ctx.addRoutes(new MyRoute());

		System.out.println("Application Running..................");
		ctx.start();

	}

}
