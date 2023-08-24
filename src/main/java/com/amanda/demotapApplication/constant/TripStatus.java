package com.amanda.demotapApplication.constant;

public enum TripStatus {

    COMPLETED("COMP", "Completed"),
    INCOMPLETE("INCOMP", "Incomplete"),
    CANCELLED("CANCL", "Cancelled");

    private String code;
    private String description;

    TripStatus(String code, String description) {
        this.code = code;
        this.description = description;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public static TripStatus getEnum(String code) {
        switch (code) {
            case "COMP":
                return COMPLETED;
            case "INCOMP":
                return INCOMPLETE;
            case "CANCL":
                return CANCELLED;
            default:
                return COMPLETED;
        }
    }
}
