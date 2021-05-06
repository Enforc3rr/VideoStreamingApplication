package com.videostream.app.util;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

/*
This class contains methods to
1 - Generate token
2 - validate Token
3 - has it expired or not
 */
@Component
public class JwtUtil {

    //Secret-Key used here is very small and not suitable for production grade application.
    private final String SECRET_KEY = "secretkey";

    //To Extract username from the token
    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }
    //To extract expiration time from the token
    public Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }
    //Extract claims basically takes in the claims and use claimsResolver to extract our claims.
    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }
    private Claims extractAllClaims(String token) {
        return Jwts.parser().setSigningKey(SECRET_KEY).parseClaimsJws(token).getBody();
    }

    private Boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }
    /*
    TO Generate token - it takes in UserDetails Class
    It's gonna call createToken Method and it's gonna pass in map of claims ( anything which we wanna include in payload)
     */
    public String generateToken(UserDetails userDetails) {
        Map<String, Object> claims = new HashMap<>();
        //it's not only gonna include claims which we are passing but also username of the user logged in.
        return createToken(claims, userDetails.getUsername());
    }

    private String createToken(Map<String, Object> claims, String subject) {
        /*
        claims = extra info that we wanna include in the payload.
        subject = user that is getting authenticated.
        */
        return Jwts.builder().setClaims(claims).setSubject(subject)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 10))
                .signWith(SignatureAlgorithm.HS256, SECRET_KEY).compact();//End of Builder Pattern.
    }
    //it basically checks the user name that is passed is same as the username in the user-details then checks the expiration.
    public Boolean validateToken(String token, UserDetails userDetails) {
        final String username = extractUsername(token);
        return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }
}