package Retail_Billing.BillingSoftware.enums;

public enum PaymentMethod {
    CASH("CASH"),
    UPI("UPI");
    private final String displayName;

    PaymentMethod(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }
    public static PaymentMethod fromString(String name) {
        for (PaymentMethod method : values()) {
            if (method.displayName.equalsIgnoreCase(name)) {
                return method;
            }
        }
        throw new IllegalArgumentException("No enum constant with displayName " + name);
    }
}
