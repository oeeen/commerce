package dev.smjeon.commerce.user.domain;

public enum UserRole {
    ADMIN("ROLE_ADMIN"), SELLER("ROLE_SELLER"), BUYER("ROLE_BUYER");

    private String roleName;

    UserRole(String roleName) {
        this.roleName = roleName;
    }
}
