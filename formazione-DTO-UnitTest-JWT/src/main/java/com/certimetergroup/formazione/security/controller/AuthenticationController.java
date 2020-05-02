package com.certimetergroup.formazione.security.controller;

import com.certimetergroup.formazione.enumeration.ResponseCodeEnum;
import com.certimetergroup.formazione.model.User;
import com.certimetergroup.formazione.security.dto.DTOLoginInput;
import com.certimetergroup.formazione.security.response.LoginResponse;
import com.certimetergroup.formazione.security.service.JwtProvider;
import com.certimetergroup.formazione.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/public")
public class AuthenticationController {

    @Autowired
    private JwtProvider jwtProvider;

    @Autowired
    private UserService userService;



    @PostMapping("/authentication")
    public ResponseEntity<LoginResponse> authenticate(@RequestBody DTOLoginInput body) {
        /*** verifica se l'utente è registrato su db ***/
        User user = userService.getUserByUsernameAndPassword(body.getUsername(), body.getPassword());
        if (user == null)       return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();

        String jwt = jwtProvider.generateJwtTokenFor(user);

        return ResponseEntity.ok().body( new LoginResponse(ResponseCodeEnum.SUCCESS, jwt) );
    }
}

