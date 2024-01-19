package com.tingweichen.applicationsystem.constant;

public enum AccountStatus {

    PASS(true),
    BLOCKED(false);

    boolean status;

    AccountStatus(boolean status) {
        this.status = status;
    }

    public boolean getStatus() {
        return status;
    }
}
