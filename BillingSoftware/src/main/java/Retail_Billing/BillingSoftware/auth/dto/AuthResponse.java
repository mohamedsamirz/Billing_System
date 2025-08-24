package Retail_Billing.BillingSoftware.auth.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class AuthResponse {
    private Long userID;
    private String username;
    private String token;
    private String role;
}
