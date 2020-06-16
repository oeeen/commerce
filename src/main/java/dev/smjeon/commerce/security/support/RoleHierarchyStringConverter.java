package dev.smjeon.commerce.security.support;

import dev.smjeon.commerce.user.domain.UserRole;

public class RoleHierarchyStringConverter {
    private RoleHierarchyStringConverter() {
    }

    public static String getRoleHierarchyStringPresentation() {
        StringBuilder roleHierarchy = new StringBuilder();

        roleHierarchy.append(UserRole.ADMIN.getRoleName()).append(" > ").append(UserRole.SELLER.getRoleName());
        roleHierarchy.append("\n");
        roleHierarchy.append(UserRole.SELLER.getRoleName()).append(" > ").append(UserRole.BUYER.getRoleName());

        return roleHierarchy.toString();
    }
}
