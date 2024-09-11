package com.enigma.challengebookingroom.service.impl;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.enigma.challengebookingroom.dto.response.JWTClaims;
import com.enigma.challengebookingroom.entity.User;
import com.enigma.challengebookingroom.repository.UserRepository;
import com.enigma.challengebookingroom.service.JwtService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.Instant;
import java.util.Objects;

@Service
///@RequiredArgsConstructor
@Slf4j
public class JwtServiceImpl implements JwtService {
    private final String JWT_SECRET;
    private final String JWT_ISSUE;
    private final Long JWT_EXPIRED_AT;
    private final UserRepository userRepository;

    public JwtServiceImpl(@Value(value = "${challengebookingroom.jwt.secret_key}") String JWT_SECRET,
                          @Value(value = "${challengebookingroom.jwt.issuer}") String JWT_ISSUE,
                          @Value(value = "${challengebookingroom.jwt.expirationInSecond}") Long JWT_EXPIRED_AT,
                          UserRepository userAccountRepository) {
        this.JWT_SECRET = JWT_SECRET;
        this.JWT_ISSUE = JWT_ISSUE;
        this.JWT_EXPIRED_AT = JWT_EXPIRED_AT;
        this.userRepository = userAccountRepository;
    }

    @Override
    public String generateToken(User userAccount) {
        try {
            Algorithm algorithm = Algorithm.HMAC512(JWT_SECRET);
            return JWT.create()
                    .withSubject(userAccount.getUserId())
                    .withClaim("Role", userAccount.getAuthorities().stream().map(
                            GrantedAuthority::getAuthority
                    ).toList())
                    .withIssuedAt(Instant.now())
                    .withExpiresAt(Instant.now().plusSeconds(JWT_EXPIRED_AT))
                    .withIssuer(JWT_ISSUE)
                    .sign(algorithm);
        } catch (JWTCreationException exception) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Error: While Creating Token");
        }
    }

    @Override
    public Boolean verifyToken(String token) {
        DecodedJWT decodedJWT;
        try {
            Algorithm algorithm = Algorithm.HMAC512(JWT_SECRET);
            JWTVerifier verifier = JWT.require(algorithm)
                    .withIssuer(JWT_ISSUE)
                    .build();

            verifier.verify(parseJWTToken(token));
            return true;
        } catch (JWTVerificationException exception) {
            log.info(exception.getLocalizedMessage());
        }
        return false;
    }

    @Override
    public JWTClaims claimToken(String token) {
        DecodedJWT decodedJWT;
        try {
            Algorithm algorithm = Algorithm.HMAC512(JWT_SECRET);
            JWTVerifier verifier = JWT.require(algorithm)
                    .withIssuer(JWT_ISSUE)
                    .build();

            decodedJWT = verifier.verify(parseJWTToken(token));
            log.info(parseJWTToken(token));
            return JWTClaims.builder()
                    .roles(decodedJWT.getClaim("Role").asList(String.class))
                    .idUser(decodedJWT.getSubject())
                    .build();
        } catch (JWTVerificationException exception) {
            // Invalid signature/claims
            log.info(exception.getLocalizedMessage());
        }
        return null;
    }

    private String parseJWTToken(String token) {
        if (Objects.nonNull(token) && token.startsWith("Bearer ")) {
            return token.substring(7);
        }
        return null;
    }
}
