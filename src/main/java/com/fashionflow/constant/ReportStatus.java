package com.fashionflow.constant;

public enum ReportStatus {
    WAITING("1"), COMPLETE("2");

    private final String code;

    ReportStatus(String code) {
        this.code = code;
    }

    public String getCode() {
        return code;
    }
}
