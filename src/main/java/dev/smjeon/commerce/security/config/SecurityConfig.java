package dev.smjeon.commerce.security.config;

import dev.smjeon.commerce.security.SecurityResourceService;
import dev.smjeon.commerce.security.factory.UrlResourcesMapFactoryBean;
import dev.smjeon.commerce.security.filters.FormLoginFilter;
import dev.smjeon.commerce.security.filters.GithubLoginFilter;
import dev.smjeon.commerce.security.filters.KakaoLoginFilter;
import dev.smjeon.commerce.security.filters.PermitAllFilter;
import dev.smjeon.commerce.security.handlers.CustomAccessDeniedHandler;
import dev.smjeon.commerce.security.handlers.CustomLogoutSuccessHandler;
import dev.smjeon.commerce.security.handlers.FormLoginAuthenticationFailureHandler;
import dev.smjeon.commerce.security.handlers.FormLoginAuthenticationSuccessHandler;
import dev.smjeon.commerce.security.metadatasource.UrlFilterInvocationSecurityMetadataSource;
import dev.smjeon.commerce.security.providers.FormLoginAuthenticationProvider;
import dev.smjeon.commerce.security.providers.SocialLoginAuthenticationProvider;
import dev.smjeon.commerce.security.support.RoleHierarchyStringConverter;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.access.AccessDecisionManager;
import org.springframework.security.access.AccessDecisionVoter;
import org.springframework.security.access.hierarchicalroles.RoleHierarchyImpl;
import org.springframework.security.access.vote.AffirmativeBased;
import org.springframework.security.access.vote.RoleHierarchyVoter;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.access.intercept.FilterInvocationSecurityMetadataSource;
import org.springframework.security.web.access.intercept.FilterSecurityInterceptor;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.util.ArrayList;
import java.util.List;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private FormLoginAuthenticationProvider formLoginAuthenticationProvider;
    private SocialLoginAuthenticationProvider kakaoLoginAuthenticationProvider;
    private SocialLoginAuthenticationProvider githubLoginAuthenticationProvider;
    private SecurityResourceService securityResourceService;

    public SecurityConfig(FormLoginAuthenticationProvider formLoginAuthenticationProvider,
                          SocialLoginAuthenticationProvider kakaoLoginAuthenticationProvider,
                          SocialLoginAuthenticationProvider githubLoginAuthenticationProvider,
                          SecurityResourceService securityResourceService) {
        this.formLoginAuthenticationProvider = formLoginAuthenticationProvider;
        this.kakaoLoginAuthenticationProvider = kakaoLoginAuthenticationProvider;
        this.githubLoginAuthenticationProvider = githubLoginAuthenticationProvider;
        this.securityResourceService = securityResourceService;
    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        web
                .ignoring().requestMatchers(PathRequest.toStaticResources().atCommonLocations());

        web
                .ignoring().requestMatchers(PathRequest.toH2Console());
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth
                .authenticationProvider(this.formLoginAuthenticationProvider)
                .authenticationProvider(this.kakaoLoginAuthenticationProvider)
                .authenticationProvider(this.githubLoginAuthenticationProvider);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .authorizeRequests()
                .anyRequest().authenticated()
                .and()
                .formLogin()
                .loginPage("/login")
                .loginProcessingUrl("/api/users/signin")
                .defaultSuccessUrl("/")
                .permitAll()
                .and()
                .logout()
                .logoutUrl("/api/users/logout")
                .logoutSuccessUrl("/")
                .clearAuthentication(true)
                .invalidateHttpSession(true)
                .logoutSuccessHandler(new CustomLogoutSuccessHandler())
                .and()
                .exceptionHandling()
                .accessDeniedHandler(accessDeniedHandler())
        ;

        http
                .csrf().disable()
                .cors();

        http
                .headers()
                .frameOptions().disable();

        http
                .addFilterBefore(kakaoLoginFilter(), UsernamePasswordAuthenticationFilter.class)
                .addFilterBefore(githubLoginFilter(), UsernamePasswordAuthenticationFilter.class)
                .addFilterBefore(formLoginFilter(), UsernamePasswordAuthenticationFilter.class)
                .addFilterAt(permitAllFilter(), FilterSecurityInterceptor.class)
        ;
    }

    @Bean
    public KakaoLoginFilter kakaoLoginFilter() throws Exception {
        KakaoLoginFilter filter = new KakaoLoginFilter("/oauth/kakao");
        filter.setAuthenticationManager(super.authenticationManagerBean());

        return filter;
    }

    @Bean
    public GithubLoginFilter githubLoginFilter() throws Exception {
        GithubLoginFilter filter = new GithubLoginFilter("/oauth/github");
        filter.setAuthenticationManager(super.authenticationManagerBean());

        return filter;
    }

    @Bean
    public FormLoginFilter formLoginFilter() throws Exception {
        FormLoginFilter filter = new FormLoginFilter("/api/users/signin",
                formLoginAuthenticationSuccessHandler(),
                formLoginAuthenticationFailureHandler());
        filter.setAuthenticationManager(super.authenticationManagerBean());

        return filter;
    }

    @Bean
    public AuthenticationSuccessHandler formLoginAuthenticationSuccessHandler() {
        return new FormLoginAuthenticationSuccessHandler();
    }

    @Bean
    public AuthenticationFailureHandler formLoginAuthenticationFailureHandler() {
        return new FormLoginAuthenticationFailureHandler();
    }

    @Bean
    public AccessDeniedHandler accessDeniedHandler() {
        return new CustomAccessDeniedHandler();
    }

    @Bean
    public PermitAllFilter permitAllFilter() throws Exception {
        PermitAllFilter permitAllFilter = new PermitAllFilter();
        permitAllFilter.setSecurityMetadataSource(urlFilterInvocationSecurityMetadataSource());
        permitAllFilter.setAccessDecisionManager(affirmativeBased());
        permitAllFilter.setAuthenticationManager(super.authenticationManagerBean());

        return permitAllFilter;
    }

    @Bean
    public FilterInvocationSecurityMetadataSource urlFilterInvocationSecurityMetadataSource() throws Exception {
        return new UrlFilterInvocationSecurityMetadataSource(urlResourcesMapFactoryBean().getObject());
    }

    private UrlResourcesMapFactoryBean urlResourcesMapFactoryBean() {
        return new UrlResourcesMapFactoryBean(securityResourceService);
    }

    private AccessDecisionManager affirmativeBased() {
        return new AffirmativeBased(getAccessDecisionVoters());
    }

    private List<AccessDecisionVoter<?>> getAccessDecisionVoters() {
        List<AccessDecisionVoter<?>> accessDecisionVoters = new ArrayList<>();
        accessDecisionVoters.add(roleVoter());

        return accessDecisionVoters;
    }

    @Bean
    public AccessDecisionVoter<?> roleVoter() {
        return new RoleHierarchyVoter(roleHierarchy());
    }

    @Bean
    public RoleHierarchyImpl roleHierarchy() {
        RoleHierarchyImpl roleHierarchy = new RoleHierarchyImpl();
        roleHierarchy.setHierarchy(RoleHierarchyStringConverter.getRoleHierarchyStringPresentation());
        return roleHierarchy;
    }


}
