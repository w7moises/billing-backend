package com.app.billing.controller;

import com.app.billing.dto.UserDto;
import com.app.billing.models.Role;
import com.app.billing.models.auth.AuthenticationRequest;
import com.app.billing.models.auth.AuthenticationResponse;
import com.app.billing.service.impl.AuthenticationServiceImpl;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RequestMapping("/auth")
public class AuthenticationController {

    private final AuthenticationServiceImpl service;

    public AuthenticationController(AuthenticationServiceImpl service) {
        this.service = service;
    }

    @PostMapping("/register/role/{role}")
    public ResponseEntity<AuthenticationResponse> register(@RequestBody @Valid UserDto request, @PathVariable Role role) {
        return ResponseEntity.ok(service.register(request, role));
    }

    @PostMapping("/authenticate")
    public ResponseEntity<AuthenticationResponse> authenticate(@RequestBody @Valid AuthenticationRequest request) {
        return ResponseEntity.ok(service.authenticate(request));
    }
}
