package dev.smjeon.commerce.security.filters;

import org.springframework.http.HttpMethod;
import org.springframework.security.access.intercept.InterceptorStatusToken;
import org.springframework.security.web.FilterInvocation;
import org.springframework.security.web.access.intercept.FilterSecurityInterceptor;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class PermitAllFilter extends FilterSecurityInterceptor {
    private static final String FILTER_APPLIED = "__spring_security_filterSecurityInterceptor_filterApplied";
    private boolean observeOncePerRequest = true;

    private List<RequestMatcher> permitAllRequestMatchers = new ArrayList<>();

    public PermitAllFilter() {
        permitAllRequestMatchers.add(new AntPathRequestMatcher("/"));
        permitAllRequestMatchers.add(new AntPathRequestMatcher("/denied*"));
        permitAllRequestMatchers.add(new AntPathRequestMatcher("/api/user/signup"));
        permitAllRequestMatchers.add(new AntPathRequestMatcher("/login/**"));
        permitAllRequestMatchers.add(new AntPathRequestMatcher("/login*"));
        permitAllRequestMatchers.add(new AntPathRequestMatcher("/signup"));
        permitAllRequestMatchers.add(new AntPathRequestMatcher("/api/categories", HttpMethod.GET.name()));
        permitAllRequestMatchers.add(new AntPathRequestMatcher("/api/products/**", HttpMethod.GET.name()));
    }

    @Override
    protected InterceptorStatusToken beforeInvocation(Object object) {
        HttpServletRequest request = ((FilterInvocation) object).getRequest();

        for (RequestMatcher permitAllRequestMatcher : permitAllRequestMatchers) {
            if (permitAllRequestMatcher.matches(request)) {
                return null;
            }
        }

        return super.beforeInvocation(object);
    }

    public void invoke(FilterInvocation fi) throws IOException, ServletException {
        if ((fi.getRequest() != null)
                && (fi.getRequest().getAttribute(FILTER_APPLIED) != null)
                && observeOncePerRequest) {
            // filter already applied to this request and user wants us to observe
            // once-per-request handling, so don't re-do security checking
            fi.getChain().doFilter(fi.getRequest(), fi.getResponse());
        } else {
            // first time this request being called, so perform security checking
            if (fi.getRequest() != null && observeOncePerRequest) {
                fi.getRequest().setAttribute(FILTER_APPLIED, Boolean.TRUE);
            }

            InterceptorStatusToken token = beforeInvocation(fi);

            try {
                fi.getChain().doFilter(fi.getRequest(), fi.getResponse());
            } finally {
                super.finallyInvocation(token);
            }

            super.afterInvocation(token, null);
        }
    }

}
