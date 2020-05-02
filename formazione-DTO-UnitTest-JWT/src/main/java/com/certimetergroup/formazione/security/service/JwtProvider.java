package com.certimetergroup.formazione.security.service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTDecodeException;
import com.auth0.jwt.exceptions.SignatureVerificationException;
import com.auth0.jwt.exceptions.TokenExpiredException;
import com.certimetergroup.formazione.model.User;
import com.certimetergroup.formazione.security.enumeration.TokenValidationResultEnum;
import com.certimetergroup.formazione.utilities.DateUtility;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class JwtProvider {

    final Logger logger = LoggerFactory.getLogger(this.getClass());


    @Value("${security.secret}")
    private String secret;

    @Value("${security.token.ttl.minute}")
    private int tokenTTLminutes;



    public String generateJwtTokenFor( User user ) {
        LocalDateTime now = LocalDateTime.now();
        return JWT.create()
                .withSubject( "subject-"+user.getIdUser() )
                .withIssuer("issuer-"+user.getIdUser())

                .withClaim("idUser", user.getIdUser())
                .withClaim("username", user.getUsername())

                .withIssuedAt( DateUtility.asDate( now ) )
                .withExpiresAt( DateUtility.asDate( now.plusMinutes(tokenTTLminutes) ) )

                .sign(Algorithm.HMAC256(secret));
    }



    public TokenValidationResultEnum verifyJwtToken(String jwt) {
        try{

            JWT.require(Algorithm.HMAC256(secret)).build().verify(jwt);
            return TokenValidationResultEnum.VALID;

        } catch (TokenExpiredException e) {
            logger.error("[AUTHENTICATION VALIDATION] Expired token - jwt => " + jwt);
            return TokenValidationResultEnum.EXPIRED;
        } catch (SignatureVerificationException e) {
            logger.error("[AUTHENTICATION VALIDATION] Signature invalid token - jwt => " + jwt);
            return TokenValidationResultEnum.INVALID_SIGNATURE;
        } catch (JWTDecodeException e) {
            logger.error("[AUTHENTICATION VALIDATION] Malformed token - jwt => " + jwt);
            return TokenValidationResultEnum.MALFORMED;
        } catch (Exception e) {
            logger.error("[AUTHENTICATION VALIDATION] Unexpected error during token validation - jwt => " + jwt);
            return TokenValidationResultEnum.ERROR_DURING_VALIDATION;
        }
    }

}
