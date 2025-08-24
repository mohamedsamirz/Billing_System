package Retail_Billing.BillingSoftware.User.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/users")
public class UserController {

    @GetMapping("/test")
    public String testProtectedEndpoint() {
        return "✅ This is a protected endpoint! Authentication is working correctly.";
    }
}
