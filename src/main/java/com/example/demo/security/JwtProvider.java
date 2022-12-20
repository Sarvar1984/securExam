package com.example.demo.security;

import com.example.demo.entity.Role;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.Set;

@Component
public class JwtProvider {
    private static Long EXPIRATION_TIME =86_000L ;
    private static String KAY="KALITSOZ";

    public String generatedToken(String userName, Set<Role> roleSet){
        String token=
                Jwts
                        .builder()
                        .setSubject(userName)
                        .setIssuedAt(new Date())
                        .setExpiration(new Date(System.currentTimeMillis()+EXPIRATION_TIME))
                        .signWith(SignatureAlgorithm.HS512,KAY)
                        .claim("roles",roleSet)
                        .compact();
        return token;
    }
}
