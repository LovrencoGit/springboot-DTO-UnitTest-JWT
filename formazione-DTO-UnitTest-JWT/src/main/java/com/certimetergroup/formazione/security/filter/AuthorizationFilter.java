package com.certimetergroup.formazione.security.filter;

import com.auth0.jwt.exceptions.JWTDecodeException;
import com.auth0.jwt.exceptions.SignatureVerificationException;
import com.auth0.jwt.exceptions.TokenExpiredException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.certimetergroup.formazione.enumeration.ResponseCodeEnum;
import com.certimetergroup.formazione.exception.FailureException;
import com.certimetergroup.formazione.security.enumeration.TokenValidationResultEnum;
import com.certimetergroup.formazione.security.service.JwtProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collections;

public class AuthorizationFilter extends BasicAuthenticationFilter {

    final Logger logger = LoggerFactory.getLogger(this.getClass());


    private JwtProvider jwtProvider;
    private String prefix;
    private String param;

    @Autowired
    public AuthorizationFilter(AuthenticationManager authenticationManager, JwtProvider jwtProvider, String prefix, String param) {
        super(authenticationManager);
        this.jwtProvider = jwtProvider;
        this.prefix = prefix;
        this.param = param;
    }



    @Override
    protected void doFilterInternal(HttpServletRequest req, HttpServletResponse res, FilterChain chain) throws IOException, ServletException {
        String headerAuthParam = req.getHeader(param);
        if (headerAuthParam == null || !headerAuthParam.startsWith(prefix)) {
            chain.doFilter(req, res);
            logger.error("[ERROR] Missing or invalid '"+param+"' in header => " + headerAuthParam );
            return;
        }

        String token = headerAuthParam.replace(prefix, "").trim();

        UsernamePasswordAuthenticationToken authentication = this.getAuthentication(token, req);
        SecurityContextHolder.getContext().setAuthentication(authentication);

        chain.doFilter(req, res);
    }

    private UsernamePasswordAuthenticationToken getAuthentication(String token, HttpServletRequest req) {
        TokenValidationResultEnum tokenValidationResultEnum = jwtProvider.verifyJwtToken(token);
        
        if( tokenValidationResultEnum == TokenValidationResultEnum.EXPIRED )
            req.setAttribute("expired",true);   // checked and used in CustomAuthenticationFailureResponseHandler.commence(...)

        if( tokenValidationResultEnum != TokenValidationResultEnum.VALID )
            throw new BadCredentialsException(tokenValidationResultEnum.value);    // => extends AuthenticationException

        String principal = token;
        return new UsernamePasswordAuthenticationToken(principal, null, Collections.emptyList());
    }
}
