package Retail_Billing.BillingSoftware.enums;

import java.util.Locale;
import java.util.Optional;


public enum UserRole {
    ADMIN("ADMIN"),
    USER("USER");

    private final String displayName;

    UserRole(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }

    @Override
    public String toString() {
        return displayName;
    }

    /**
     * Returns the UserRole matching the exact display name.
     * Throws IllegalArgumentException if none match.
     */
    public static UserRole fromDisplayName(String name) {
        return fromDisplayNameOptional(name)
                .orElseThrow(() -> new IllegalArgumentException("No enum constant with displayName " + name));
    }

    /**
     * Case-insensitive lookup returning an Optional.
     */
    public static Optional<UserRole> fromDisplayNameOptional(String name) {
        if (name == null) return Optional.empty();
        String n = name.trim();
        for (UserRole r : values()) {
            if (r.displayName.equals(n) || r.displayName.equalsIgnoreCase(n)) {
                return Optional.of(r);
            }
        }
        return Optional.empty();
    }

    /**
     * Convenience case-insensitive lookup that throws when not found.
     */
    public static UserRole fromDisplayNameIgnoreCase(String name) {
        return fromDisplayNameOptional(name)
                .orElseThrow(() -> new IllegalArgumentException("No enum constant with displayName (ignore case) " + name));
    }
}
