package dev.smjeon.commerce.security.config;

import dev.smjeon.commerce.security.filters.FormLoginFilter;
import dev.smjeon.commerce.security.filters.SocialLoginFilter;
import dev.smjeon.commerce.security.providers.FormLoginAuthenticationProvider;
import dev.smjeon.commerce.security.providers.SocialLoginAuthenticationProvider;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private AuthenticationSuccessHandler authenticationSuccessHandler;
    private AuthenticationFailureHandler authenticationFailureHandler;
    private FormLoginAuthenticationProvider formLoginAuthenticationProvider;
    private SocialLoginAuthenticationProvider socialLoginAuthenticationProvider;

    public SecurityConfig(AuthenticationSuccessHandler authenticationSuccessHandler,
                          AuthenticationFailureHandler authenticationFailureHandler,
                          FormLoginAuthenticationProvider formLoginAuthenticationProvider,
                          SocialLoginAuthenticationProvider socialLoginAuthenticationProvider) {
        this.authenticationSuccessHandler = authenticationSuccessHandler;
        this.authenticationFailureHandler = authenticationFailureHandler;
        this.formLoginAuthenticationProvider = formLoginAuthenticationProvider;
        this.socialLoginAuthenticationProvider = socialLoginAuthenticationProvider;
    }

    @Bean
    public SocialLoginFilter socialLoginFilter() throws Exception {
        SocialLoginFilter filter = new SocialLoginFilter("/oauth/**");
        filter.setAuthenticationManager(super.authenticationManagerBean());

        return filter;
    }

    @Bean
    public FormLoginFilter formLoginFilter() throws Exception {
        FormLoginFilter filter = new FormLoginFilter("/api/users/signin", authenticationSuccessHandler, authenticationFailureHandler);
        filter.setAuthenticationManager(super.authenticationManagerBean());

        return filter;
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth
                .authenticationProvider(this.formLoginAuthenticationProvider)
                .authenticationProvider(this.socialLoginAuthenticationProvider);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .csrf().disable()
                .cors();

        http
                .addFilterBefore(socialLoginFilter(), UsernamePasswordAuthenticationFilter.class)
                .addFilterBefore(formLoginFilter(), UsernamePasswordAuthenticationFilter.class);
    }
}
