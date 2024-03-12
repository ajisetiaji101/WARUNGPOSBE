package com.warungposbespring.warungposbe.controller;

import com.warungposbespring.warungposbe.config.AppAuthentication;
import com.warungposbespring.warungposbe.dto.*;
import com.warungposbespring.warungposbe.service.UserService;
import com.warungposbespring.warungposbe.utils.HttpResponse;
import com.warungposbespring.warungposbe.utils.JwtUtil;
import com.warungposbespring.warungposbe.utils.SecurityUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.concurrent.TimeUnit;

@RestController
@RequestMapping("/api/v1")
@CrossOrigin("*")
public class AuthController {

    @Autowired
    private UserService userService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @PostMapping("/login")
    public ResponseEntity<?> authLogin(@RequestBody LoginDtoRequest request) {

        LoginDtoResponse result  = userService.authentication(request);

        return HttpResponse.generateResponse("Berhasil", HttpStatus.OK, result, true);
    }

    @GetMapping("/session")
    public ResponseEntity<?> sessionAuth(){
        return HttpResponse.generateResponse("Berhasil", HttpStatus.OK, "Online", true);
    }

    @GetMapping("/sessionJwt")
    public ResponseEntity<?> sessionLogin(@RequestParam(name = "token") String token){
        UserForJwtResponse dataAuth = SecurityUtil.getAuthUser();
        userService.sessionLogin(token);

        return HttpResponse.generateResponse("Berhasil", HttpStatus.OK, "Online", true);
    }

    @PostMapping("/logout")
    public void logout(@RequestParam(name = "token") String token) {
        userService.logoutSession(token);
    }

}
