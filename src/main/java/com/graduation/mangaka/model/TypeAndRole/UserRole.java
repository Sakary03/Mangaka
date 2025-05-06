package com.graduation.mangaka.model.TypeAndRole;

public enum UserRole {
    USER,
    ADMIN,
    PUBLISHER;

    public String toAuthority() {
        return "ROLE_" + this.name();
    }
}
