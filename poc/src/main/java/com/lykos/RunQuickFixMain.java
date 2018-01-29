package com.lykos;

import org.apache.camel.CamelContext;
import org.apache.camel.impl.DefaultCamelContext;
import org.apache.camel.impl.SimpleRegistry;
import org.apache.camel.main.Main;

import com.lykos.routes.QuickFixRoutes;

/**
 * A main class to try to run this example
 */
public class RunQuickFixMain {

	public static void main(String[] args) throws Exception {
		new RunQuickFixMain().run();
	}

	public void run() throws Exception {
		Main main = new Main();
		SimpleRegistry registry = new SimpleRegistry();
		CamelContext ctx = new DefaultCamelContext(registry);
		ctx.addRoutes(new QuickFixRoutes());
		main.getCamelContexts().add(ctx);
		System.out.println("Application Running QuickFix..................");
		main.run();
	}

}
