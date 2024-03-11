package com.spring.spring_security.service;

import com.spring.spring_security.model.User;
import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;
import java.util.Map;

@Service
public class JwtService {

    //de aplication propetios
    @Value("${security.jwt.expiration-minutes}")
    private long EXPIRATION_MINUTES;

    @Value("${security.jwt.secret-key}")
    private String SECRET_KEY;

    //generacio de token recibe los claims
    public String generateToken(User user, Map<String, Object> extraClaims) {

        Date issuedAt = new Date(System.currentTimeMillis());
        //sumandole 30 minutos
        Date expiration = new Date( issuedAt.getTime() + (EXPIRATION_MINUTES * 60 * 1000) );

        //constructor par poner los claims //construir al incicio
        return Jwts.builder()
                .setClaims(extraClaims)
                .setSubject(user.getUsername())
                .setIssuedAt(issuedAt)
                .setExpiration(expiration)
                //COMO SE GENERA LA FIRMA
                .setHeaderParam(Header.TYPE, Header.JWT_TYPE)
                //generacion de clave segura
                .signWith(generateKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    //key para que se encripte
    private Key generateKey(){
        byte[] secretAsBytes = Decoders.BASE64.decode(SECRET_KEY);
        System.out.println("mi clave es: " + new String(secretAsBytes));
        //MANDANDO LALLAVE SECRETA
        return Keys.hmacShaKeyFor(secretAsBytes);
    }

    public String extractUsername(String jwt) {
        return extractAllClaims(jwt).getSubject();
    }

    private Claims extractAllClaims(String jwt) {
        return Jwts.parserBuilder().setSigningKey(generateKey()).build()
                .parseClaimsJws(jwt).getBody();
    }
}
