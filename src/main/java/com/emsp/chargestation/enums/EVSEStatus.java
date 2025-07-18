package com.emsp.chargestation.enums;

public enum EVSEStatus {
    AVAILABLE("可用"),
    BLOCKED("占用"),
    INOPERABLE("故障"),
    REMOVED("报废");

    private final String description;

    EVSEStatus(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}