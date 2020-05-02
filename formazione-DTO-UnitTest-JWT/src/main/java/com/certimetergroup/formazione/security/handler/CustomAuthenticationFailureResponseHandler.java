package com.certimetergroup.formazione.security.handler;

import com.certimetergroup.formazione.enumeration.ResponseCodeEnum;
import com.certimetergroup.formazione.response.Response;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class CustomAuthenticationFailureResponseHandler implements AuthenticationEntryPoint {

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException {

        boolean expired = request.getAttribute("expired")!=null;
        ResponseCodeEnum responseCodeEnum = expired ? ResponseCodeEnum.ERR_EXPIRED_TOKEN : ResponseCodeEnum.ERR_INVALID_TOKEN;

        Response data = new Response(responseCodeEnum);

        response.setStatus( HttpStatus.UNAUTHORIZED.value() );
        response.setContentType( MediaType.APPLICATION_JSON_VALUE );
        response.getOutputStream().println( new ObjectMapper().writeValueAsString(data) );
    }

}
