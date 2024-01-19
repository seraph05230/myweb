package com.tingweichen.applicationsystem.constant;

public enum AuthorizationStatus {

    SUCCESS(true),
    FAILURE(false);

    boolean status;

    AuthorizationStatus(boolean status) {
        this.status = status;
    }

    public boolean getStatus() {
        return status;
    }
}
