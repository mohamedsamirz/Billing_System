package Retail_Billing.BillingSoftware.auth.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import Retail_Billing.BillingSoftware.User.DTO.UserDTO;
import Retail_Billing.BillingSoftware.User.Entity.User;
import Retail_Billing.BillingSoftware.User.Repository.UserRepository;
import Retail_Billing.BillingSoftware.auth.dto.LoginRequest;
import Retail_Billing.BillingSoftware.auth.dto.AuthResponse;
import Retail_Billing.BillingSoftware.enums.UserRole;
import Retail_Billing.BillingSoftware.util.JWTService;

@Service
public class AuthService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private JWTService jwtService;
    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    public AuthResponse login(LoginRequest loginRequest) {
        System.out.println("🔍 [AuthService] Attempting login for email: " + loginRequest.getEmail());
        
        User user = userRepository.findByEmail(loginRequest.getEmail())
                .orElseThrow(() -> {
                    System.out.println("❌ [AuthService] User not found for email: " + loginRequest.getEmail());
                    return new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found");
                });

        System.out.println("✅ [AuthService] User found: " + user.getUsername() + " with role: " + user.getRole());

        if (!this.passwordEncoder.matches(loginRequest.getPassword(), user.getPassword())) {
            System.out.println("❌ [AuthService] Password mismatch for user: " + user.getUsername());
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid password");
        }

        System.out.println("✅ [AuthService] Password verified successfully");

        String token = jwtService.generateJwt(user.getUserId(), user.getRole().getDisplayName());
        System.out.println("✅ [AuthService] JWT token generated successfully");
        
        return new AuthResponse(user.getUserId(), user.getUsername(), token, user.getRole().getDisplayName());
    }

    public AuthResponse signup(UserDTO userDto) {
        if (userRepository.existsByEmail(userDto.getEmail())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Email already exists");
        }

        User user = new User();
        user.setEmail(userDto.getEmail());
        user.setUsername(userDto.getUsername());
        user.setPassword(passwordEncoder.encode(userDto.getPassword()));
        user.setRole(userDto.getRole());
        user = userRepository.save(user);
        String token = jwtService.generateJwt(user.getUserId(), user.getRole().getDisplayName());
        return new AuthResponse(user.getUserId(), user.getUsername(), token, user.getRole().getDisplayName());
    }
}
