package com.lykos.jwt;

import java.util.Date;

import org.springframework.beans.factory.annotation.Value;

import com.lykos.model.User;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

public class JwtUtil {

    //@Value("${jwt.secret}")
    private static String secret="lykos";

    /**
     * Tries to parse specified String as a JWT token. If successful, returns User object with username, id and role prefilled (extracted from token).
     * If unsuccessful (token is invalid or not containing all required user properties), simply returns null.
     * 
     * @param token the JWT token to parse
     * @return the User object extracted from specified token or null if a token is invalid.
     */
    public static User parseToken(String token) {
        try {
            Claims body = Jwts.parser()
                    .setSigningKey(secret)
                    .parseClaimsJws(token)
                    .getBody();
           

            User u = new User();
            u.setUsername(body.getSubject());
            u.setId(Long.parseLong((String) body.get("userId")));
            u.setRole((String) body.get("role"));
            
            System.out.println("createdTime"+body.getIssuedAt());
            System.out.println("createdTime"+body.get("createdTime"));
            return u;

        } catch (ClassCastException e) {
            return null;
        } catch (JwtException e){
        	return null;
        }
    }
    
    public static boolean isValidToken(String token) {
    	boolean isValid = false;
        try {
          Jwts.parser()
                    .setSigningKey(secret)
                    .parseClaimsJws(token)
                    .getBody();         
            isValid = true;
        } catch (ClassCastException e) {
             throw e;
        } catch (JwtException e){
        	 throw e;
        }
		return isValid;       
    }

    /**
     * Generates a JWT token containing username as subject, and userId and role as additional claims. These properties are taken from the specified
     * User object. Tokens validity is infinite.
     * 
     * @param u the user for which the token will be generated
     * @return the JWT token
     */
    public static String generateToken(User u) {
        Claims claims = Jwts.claims().setSubject(u.getUsername());
        claims.put("userId", u.getId() + "");
        claims.put("role", u.getRole());
        claims.put("createdTime", new Date());

        return Jwts.builder()
                .setClaims(claims)
                .signWith(SignatureAlgorithm.HS512, secret)
                .compact();
    }
}
