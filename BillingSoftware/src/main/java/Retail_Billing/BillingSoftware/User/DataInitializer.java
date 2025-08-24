package Retail_Billing.BillingSoftware.User;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import Retail_Billing.BillingSoftware.User.Entity.User;
import Retail_Billing.BillingSoftware.User.Repository.UserRepository;
import Retail_Billing.BillingSoftware.enums.UserRole;

@Component
public class DataInitializer implements CommandLineRunner {

    @Autowired
    private UserRepository userRepository;
    
    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    @Override
    public void run(String... args) throws Exception {
        System.out.println("🚀 [DataInitializer] Starting data initialization...");
        
        // Check if admin user already exists
        if (!userRepository.existsByEmail("admin@billing.com")) {
            User adminUser = User.builder()
                .username("admin")
                .email("admin@billing.com")
                .password(passwordEncoder.encode("admin123"))
                .role(UserRole.ADMIN)
                .build();
            
            userRepository.save(adminUser);
            System.out.println("✅ [DataInitializer] Admin user created: admin@billing.com / admin123");
        } else {
            System.out.println("ℹ️ [DataInitializer] Admin user already exists");
        }
        
        // Check if test user already exists
        if (!userRepository.existsByEmail("user@billing.com")) {
            User testUser = User.builder()
                .username("testuser")
                .email("user@billing.com")
                .password(passwordEncoder.encode("user123"))
                .role(UserRole.USER)
                .build();
            
            userRepository.save(testUser);
            System.out.println("✅ [DataInitializer] Test user created: user@billing.com / user123");
        } else {
            System.out.println("ℹ️ [DataInitializer] Test user already exists");
        }
        
        System.out.println("🎯 [DataInitializer] Data initialization completed!");
    }
}
