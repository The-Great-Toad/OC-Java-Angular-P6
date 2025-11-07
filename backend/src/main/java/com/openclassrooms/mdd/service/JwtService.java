package com.openclassrooms.mdd.service;

import com.nimbusds.jose.JOSEException;
import com.nimbusds.jose.JWSAlgorithm;
import com.nimbusds.jose.JWSHeader;
import com.nimbusds.jose.JWSSigner;
import com.nimbusds.jose.JWSVerifier;
import com.nimbusds.jose.crypto.MACSigner;
import com.nimbusds.jose.crypto.MACVerifier;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;
import com.openclassrooms.mdd.dto.response.LoginResponse;
import com.openclassrooms.mdd.exception.TokenGenerationException;
import com.openclassrooms.mdd.exception.TokenValidationException;

import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.util.Date;
import java.util.UUID;

@Service
@Slf4j
public class JwtService {

    @Value("${jwt.secret.key}")
    private String secretKey;

    @Value("${jwt.issuer}")
    private String issuer;

    @Value("${jwt.audience}")
    private String audience;

    @Value("${jwt.expiration.time}")
    private int tokenExpirationTime;

    private JWSSigner signer;
    private JWSVerifier verifier;

    @PostConstruct
    public void initSigner() throws JOSEException {
        this.signer = new MACSigner(secretKey);
        this.verifier = new MACVerifier(secretKey);
        log.info("{} - JWT signer and verifier initialized with secret key", log.getName());
    }

    /**
     * Generates a JWT token for the given user email.
     * <a href="https://connect2id.com/products/nimbus-jose-jwt/examples/jwt-with-hmac">Nimbus-jose-jwt library documentation</a>
     *
     * @param uuid the UUID of the user for whom the token is generated
     * @return LoginResponse containing the generated JWT token
     */
    public LoginResponse generateToken(UUID uuid) {
        try {
            /* Prepare JWT with claims set */
            JWTClaimsSet claimsSet = new JWTClaimsSet.Builder()
                    .subject(String.valueOf(uuid))
                    .issuer(issuer)
                    .audience(audience)
                    .issueTime(new Date())
                    .notBeforeTime(new Date())
                    .expirationTime(new Date(System.currentTimeMillis() + tokenExpirationTime))
                    .jwtID(UUID.randomUUID().toString())
                    .build();

            SignedJWT signedJWT = new SignedJWT(new JWSHeader(JWSAlgorithm.HS512), claimsSet);
            signedJWT.sign(signer);

            return new LoginResponse(signedJWT.serialize());

        } catch (JOSEException ex) {
            throw new TokenGenerationException("Failed to generate JWT token: " + ex.getMessage(), ex);
        }
    }

    /**
     * Validates the JWT token.
     * It checks if the token is signed correctly and if the token is not expired.
     *
     * @param token the JWT token to validate
     * @return true if the token is valid, false otherwise
     */
    public boolean isTokenValid(String token) {
        try {
            SignedJWT signedJWT = SignedJWT.parse(token);
            return signedJWT.verify(verifier) && !isTokenExpired(token);

        } catch (Exception e) {
            log.error("Token validation failed: {}", e.getMessage());
            return false;
        }
    }

    /**
     * Extracts the user UUID from the JWT token.
     *
     * @param token the JWT token from which to extract the user UUID
     * @return the UUID extracted from the token
     */
    public String extractUserUuid(String token) {
        try {
            SignedJWT signedJWT = SignedJWT.parse(token);
            return signedJWT.getJWTClaimsSet().getSubject();

        } catch (ParseException ex) {
            throw new TokenValidationException("Failed to parse JWT token: " + ex.getMessage(), ex);
        }
    }

    /**
     * Checks if the token is expired.
     *
     * @param token the JWT token to check
     * @return true if the token is expired, false otherwise
     */
    public boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    /**
     * Extracts the expiration date from the JWT token.
     *
     * @param token the JWT token from which to extract the expiration date
     * @return the expiration date extracted from the token
     */
    private Date extractExpiration(String token) {
        try {
            SignedJWT signedJWT = SignedJWT.parse(token);
            return signedJWT.getJWTClaimsSet().getExpirationTime();

        } catch (ParseException ex) {
            throw new TokenValidationException("Failed to parse JWT token: " + ex.getMessage(), ex);
        }
    }
}
