package com.camel.routes;

import org.apache.camel.CamelContext;
import org.apache.camel.impl.DefaultCamelContext;
import org.apache.camel.impl.SimpleRegistry;

import com.camel.domain.Order;
import com.camel.service.ConfirmOrder;
import com.camel.service.GetOrder;
import com.camel.service.ValidateOrder;

public class Main {
	public static void main(String[] args) {

		try {
			SimpleRegistry reg = new SimpleRegistry();

			Order order = new Order();
			order.setCost("50000000");
			order.setOrderID("D102");
			order.setOrderName("DNPL");

			GetOrder getOrder = new GetOrder();
			ValidateOrder validateOrder = new ValidateOrder();
			ConfirmOrder confirmOrder = new ConfirmOrder();

			reg.put("order", order);
			reg.put("getOrder", getOrder);
			reg.put("validateOrder", validateOrder);
			reg.put("confirmOrder", confirmOrder);

			CamelContext ctx = new DefaultCamelContext(reg);
			ctx.start();
			System.out.println("start !");
			MultiCastRoute multiCastRout = new MultiCastRoute();
			ctx.addRoutes(multiCastRout);

			ctx.createProducerTemplate().requestBody("direct:start", ctx.getRegistry().lookup("order"), Order.class);

			System.out.println("end !");
			Thread.sleep(10000);
			ctx.stop();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}