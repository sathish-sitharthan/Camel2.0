package com.lykos.services;

import com.lykos.model.Product;


/**
 * Just a definition of the service. No implementation needed.
 * 
 * Needs to be a class so that Spring can call a default constructor (which it
 * can't do with an interface).
 */

public class ServiceImpl implements Service {	
	/**
	 * Camel intercepts this.
	 * 
	 * Receives a request with one URL-based argument and responds with JSON.
	 * 
	 * @param text
	 *            text
	 * @return A response in String format with MIME of application/json
	 */

	public String getString(String id) {
		// this does nothing -- just a shell for CXF and Camel
		System.out.println("You are in : " + id);		
		return id;
	}
	
	public String createProduct(Product product){
		System.out.println("Product Created "+ product.getProductId().toString());	
		return "Product Created";		
	}
	
	public Product getProduct(String id) {
		// this does nothing -- just a shell for CXF and Camel
		System.out.println("You are in : " + id);		
		Product product= new Product();
		product.setProductId("engine");
		product.setProductName("Airbus");
		return product;
	}
	
	public Product getProductEnrich(Product product) {
		// this does nothing -- just a shell for CXF and Camel	
		Product prod = product;
		product.setProductId(prod.getProductId().toString()+" enrich");
		product.setProductId(prod.getProductName().toString()+" enrich");		
		return prod;
	}
	
	public Product getStringEnrich(String id) {
		// this does nothing -- just a shell for CXF and Camel			
		Product product = new Product();
		product.setProductId(id+" enrich");
		product.setProductId(id+" enrich");		
		return product;
	}
}
