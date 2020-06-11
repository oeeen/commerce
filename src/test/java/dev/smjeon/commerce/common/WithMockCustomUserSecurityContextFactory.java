package dev.smjeon.commerce.common;

import dev.smjeon.commerce.security.UserContext;
import dev.smjeon.commerce.security.token.PostAuthorizationToken;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.test.context.support.WithSecurityContextFactory;

public class WithMockCustomUserSecurityContextFactory implements WithSecurityContextFactory<WithMockCustomUser> {

    @Override
    public SecurityContext createSecurityContext(WithMockCustomUser customUser) {
        SecurityContext context = SecurityContextHolder.createEmptyContext();

        UserContext userContext = new UserContext(1L, "aabb", customUser.username(), customUser.role());
        PostAuthorizationToken token = new PostAuthorizationToken(userContext);

        context.setAuthentication(token);

        return context;
    }
}
