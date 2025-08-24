package Retail_Billing.BillingSoftware.util;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
@Service
public class JWTService {
 @Value("${jwt.secret}")
    private String SECRET_KEY;

    @Value("${jwt.expiration}")
    private Long EXPIRATION_TIME;

    public String generateJwt(Long userId, String role) {
        System.out.println("🔐 [JWTService] Generating JWT for userId: " + userId + ", role: " + role);
        System.out.println("🔐 [JWTService] Using secret key length: " + SECRET_KEY.length());
        System.out.println("🔐 [JWTService] Expiration time: " + EXPIRATION_TIME + "ms");
        
        Map<String, Object> claims = new HashMap<>();
        claims.put("userId", userId);
        claims.put("role", role);

        Date now = new Date();
        Date expirationDate = new Date(now.getTime() + EXPIRATION_TIME);

        try {
            String token = Jwts.builder()
                    .setClaims(claims)
                    .setIssuedAt(now)
                    .setExpiration(expirationDate)
                    .signWith(Keys.hmacShaKeyFor(SECRET_KEY.getBytes()))
                    .compact();
            System.out.println("✅ [JWTService] JWT generated successfully, length: " + token.length());
            return token;
        } catch (Exception e) {
            System.out.println("❌ [JWTService] Error generating JWT: " + e.getMessage());
            e.printStackTrace();
            throw e;
        }
    }

    public boolean isJwtValid(String jwt) {
        try {
            Jwts.parserBuilder().setSigningKey(Keys.hmacShaKeyFor(SECRET_KEY.getBytes())).build().parseClaimsJws(jwt);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public Claims parseJwt(String jwt) {
        return Jwts.parserBuilder().setSigningKey(Keys.hmacShaKeyFor(SECRET_KEY.getBytes())).build().parseClaimsJws(jwt).getBody();
    }

    public Long getUserIdFromJwt(String jwt) {
        return parseJwt(jwt).get("userId", Long.class);
    }

    public String getRoleFromJwt(String jwt) {
        return parseJwt(jwt).get("role", String.class);
    }
}
