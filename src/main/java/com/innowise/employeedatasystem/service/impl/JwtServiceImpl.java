package com.innowise.employeedatasystem.service.impl;

import com.innowise.employeedatasystem.exception.InvalidTokenException;
import com.innowise.employeedatasystem.service.JwtService;
import com.innowise.employeedatasystem.util.GeneralConstant;
import io.jsonwebtoken.*;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.HashMap;

@Slf4j
@Service
@NoArgsConstructor
@Transactional(propagation = Propagation.REQUIRED)
public class JwtServiceImpl extends JwtService {

    @Override
    public String generateToken(UserDetails userDetails) {

        log.info("Generating JwtToken for UserDetails: {}", userDetails.getUsername());

        String generatedToken = Jwts
                .builder()
                .setClaims(new HashMap<>())
                .setSubject(userDetails.getUsername())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + GeneralConstant.JwtFeature.JWT_TOKEN_VALIDITY))   // 2 hours
                .signWith(getSignInKey(), SignatureAlgorithm.HS256)
                .compact();

        log.info("Successfully generate JwtToken for UserDetails: {}\n Token: {}", userDetails.getUsername(), generatedToken);

        return generatedToken;
    }

    @Override
    public boolean isJwtTokenValid(String jwtToken, UserDetails userDetails) throws MalformedJwtException, ExpiredJwtException, UnsupportedJwtException, IllegalArgumentException, InvalidTokenException {

        log.info("Validating JwtToken: {} for UserDetails: {}", jwtToken, userDetails.getUsername());

        final String username = extractUsername(jwtToken);

        boolean isTokenValid = username.equals(userDetails.getUsername()) && !isTokenExpired(jwtToken);

        log.info("End token validation. JwtToken: {} for UserDetails: {} isValid: {}", jwtToken, userDetails.getUsername(), isTokenValid);

        return isTokenValid;
    }

    @Override
    public boolean isTokenExpired(String jwtToken) throws InvalidTokenException {
        return extractExpiration(jwtToken).before(new Date());
    }

}

