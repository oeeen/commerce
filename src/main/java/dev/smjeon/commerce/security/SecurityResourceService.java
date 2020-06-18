package dev.smjeon.commerce.security;

import dev.smjeon.commerce.user.domain.UserRole;
import org.springframework.http.HttpMethod;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.access.SecurityConfig;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;

@Service
public class SecurityResourceService {
    private LinkedHashMap<RequestMatcher, List<ConfigAttribute>> resources = new LinkedHashMap<>();

    {
        resources.put(new AntPathRequestMatcher("/api/users", HttpMethod.GET.name()), Collections.singletonList(new SecurityConfig(UserRole.ADMIN.getRoleName())));
        resources.put(new AntPathRequestMatcher("/api/users/**", HttpMethod.DELETE.name()), Collections.singletonList(new SecurityConfig(UserRole.BUYER.getRoleName())));
        resources.put(new AntPathRequestMatcher("/users", HttpMethod.GET.name()), Collections.singletonList(new SecurityConfig(UserRole.ADMIN.getRoleName())));
        resources.put(new AntPathRequestMatcher("/api/products**", HttpMethod.POST.name()), Collections.singletonList(new SecurityConfig(UserRole.SELLER.getRoleName())));
        resources.put(new AntPathRequestMatcher("/api/products**", HttpMethod.PUT.name()), Collections.singletonList(new SecurityConfig(UserRole.SELLER.getRoleName())));
        resources.put(new AntPathRequestMatcher("/api/products**", HttpMethod.DELETE.name()), Collections.singletonList(new SecurityConfig(UserRole.SELLER.getRoleName())));
        resources.put(new AntPathRequestMatcher("/api/categories", HttpMethod.POST.name()), Collections.singletonList(new SecurityConfig(UserRole.ADMIN.getRoleName())));
    }

    public LinkedHashMap<RequestMatcher, List<ConfigAttribute>> getResources() {
        return this.resources;
    }
}
