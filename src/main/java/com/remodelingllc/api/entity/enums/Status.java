package com.remodelingllc.api.entity.enums;

public enum Status {

    ACTIVE("A"), INACTIVE("I");
    private final String status;

    Status(final String status) {
        this.status = status;
    }

    public String getStatus() {
        return status;
    }
}
