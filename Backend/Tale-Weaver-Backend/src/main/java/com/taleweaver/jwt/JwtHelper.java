package com.taleweaver.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Component
public class JwtHelper {

    private SecretKey key = Keys.hmacShaKeyFor(JwtConstants.JWT_SECRET.getBytes());

    public String generateToken(Authentication auth) {

        String jwt = Jwts.builder()
                .setIssuedAt(new Date())
                .setExpiration(new Date(new Date().getTime() + 86400000))
                .claim("email" , auth.getName())
                .signWith(key).compact();

        System.out.println("Token generated : "+jwt);
        return jwt;
    }

    public String getEmailFromJwtToken(String jwt)
    {
        //BearerJWT --> removing Bearer from front therefore removing first 7 characters!
        jwt = jwt.substring(7); //to cut down the prefix "bearer " received from client side
        Claims claims = Jwts.parser().setSigningKey(key).build().parseClaimsJws(jwt).getBody();

        String email = String.valueOf(claims.get("email"));

        return email;
    }

}