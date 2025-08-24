package Retail_Billing.BillingSoftware.auth.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import Retail_Billing.BillingSoftware.User.DTO.UserDTO;
import Retail_Billing.BillingSoftware.auth.dto.LoginRequest;
import Retail_Billing.BillingSoftware.auth.dto.AuthResponse;
import Retail_Billing.BillingSoftware.auth.service.AuthService;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/auth")
public class AuthController {
    @Autowired
    private AuthService authService;

    public AuthController() {
            System.out.println("🟢 AuthController initialized!");
    }


    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login( @RequestBody LoginRequest loginRequest) {
        System.out.println("🟩 [Controller] /auth/login hit with email: " + loginRequest.getEmail());
        return ResponseEntity.ok(authService.login(loginRequest));
    }

    @PostMapping("/signup")
    public ResponseEntity<AuthResponse> signup(@Valid @RequestBody UserDTO signupRequest) {
        return ResponseEntity.ok(authService.signup(signupRequest));
    }
}
