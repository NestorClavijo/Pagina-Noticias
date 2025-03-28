package com.app.noticias.controller;

import com.app.noticias.DTOsecurity.AuthResponse;
import com.app.noticias.DTOsecurity.LoginRequest;
import com.app.noticias.DTOsecurity.RegisterRequest;
import com.app.noticias.Service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
@CrossOrigin(origins="*")
public class AuthController {

    private final AuthService authService;

    @CrossOrigin(origins="*")
    @PostMapping(value = "/login")
    public ResponseEntity<AuthResponse> login(@RequestBody LoginRequest request)
    {
        return ResponseEntity.ok(authService.login(request));
    }

    @CrossOrigin(origins="*")
    @PostMapping(value = "/register")
    public ResponseEntity<AuthResponse> register(@RequestBody RegisterRequest request)
    {
        return ResponseEntity.ok(authService.register(request));
    }
}
