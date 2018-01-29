package com.lykos.security;

import java.util.Map;

import org.apache.camel.Exchange;
import org.springframework.mobile.device.Device;

import com.lykos.jwt.JwtUtil;
import com.lykos.model.User;

import io.jsonwebtoken.JwtException;

public class JwtFilter {
	public boolean filter(Exchange exchange) {	
		
		/*User userDetails = new User();
		userDetails.setUsername("SATHISHS");		
		System.out.println(JwtUtil.generateToken(userDetails));*/
		
		Map<String, Object> headers = exchange.getIn().getHeaders();
		String header = (String) headers.get("Authorization");

		if (header == null || !header.startsWith("Bearer ")) {
			throw new JwtException("No JWT token found in request headers");
		}
		
		String token = header.substring(7);
		return JwtUtil.isValidToken(token);
		
	}
}
