package com.certimetergroup.formazione.security.response;

import com.certimetergroup.formazione.enumeration.ResponseCodeEnum;
import com.certimetergroup.formazione.response.Response;

public class LoginResponse extends Response {

    private String token;


    public LoginResponse(ResponseCodeEnum responseCodeEnum, String token) {
        super(responseCodeEnum);
        this.token = token;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    @Override
    public String toString() {
        return "DTOLoginOutput{" +
                "token='" + token + '\'' +
                '}';
    }
}
