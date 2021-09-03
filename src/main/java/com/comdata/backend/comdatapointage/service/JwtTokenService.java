package com.comdata.backend.comdatapointage.service;

import com.comdata.backend.comdatapointage.security.SecurityConstants;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.Map;
import java.util.function.Function;

@Service
public class JwtTokenService {

    public String getMatriculeFromToken(String token) {
        Claims claims = getAllClaimsFromToken(token);
        return claims.getSubject();
    }

    public Boolean isTokenExpired(String token) {
        final Date expiration = getExpirationDateFromToken(token);
        return expiration.before(new Date());
    }

    private Date getExpirationDateFromToken(String token) {
        Claims claims = getAllClaimsFromToken(token);
        return claims.getExpiration();
    }

    public boolean checkTheRole(String token, String role) {
        Claims claims = getAllClaimsFromToken(token);
        ArrayList<Map<String, String>> roles = (ArrayList<Map<String, String>>)claims.get("roles");
        for (Map<String, String> r : roles) {
            if (r.get("authority").equals(role)) {
                return true;
            }
            System.out.println("role : " + r.get("authority"));
        }
        return false;
    }

    private Claims getAllClaimsFromToken(String token) {
        return Jwts.parser()
                .setSigningKey(SecurityConstants.TOKEN_SECRET)
                .parseClaimsJws(token)
                .getBody();
    }



}