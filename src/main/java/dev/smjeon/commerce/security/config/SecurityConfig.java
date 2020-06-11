package dev.smjeon.commerce.security.config;

import dev.smjeon.commerce.security.filters.FormLoginFilter;
import dev.smjeon.commerce.security.filters.GithubLoginFilter;
import dev.smjeon.commerce.security.filters.KakaoLoginFilter;
import dev.smjeon.commerce.security.providers.FormLoginAuthenticationProvider;
import dev.smjeon.commerce.security.providers.SocialLoginAuthenticationProvider;
import dev.smjeon.commerce.user.domain.UserRole;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
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
    private SocialLoginAuthenticationProvider kakaoLoginAuthenticationProvider;
    private SocialLoginAuthenticationProvider githubLoginAuthenticationProvider;

    public SecurityConfig(AuthenticationSuccessHandler authenticationSuccessHandler,
                          AuthenticationFailureHandler authenticationFailureHandler,
                          FormLoginAuthenticationProvider formLoginAuthenticationProvider,
                          SocialLoginAuthenticationProvider kakaoLoginAuthenticationProvider,
                          SocialLoginAuthenticationProvider githubLoginAuthenticationProvider) {
        this.authenticationSuccessHandler = authenticationSuccessHandler;
        this.authenticationFailureHandler = authenticationFailureHandler;
        this.formLoginAuthenticationProvider = formLoginAuthenticationProvider;
        this.kakaoLoginAuthenticationProvider = kakaoLoginAuthenticationProvider;
        this.githubLoginAuthenticationProvider = githubLoginAuthenticationProvider;
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
        FormLoginFilter filter = new FormLoginFilter("/api/users/signin", authenticationSuccessHandler, authenticationFailureHandler);
        filter.setAuthenticationManager(super.authenticationManagerBean());

        return filter;
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
                .antMatchers("/", "/api/users/signin", "/api/users/signup", "/login", "/login/**", "/signup").permitAll()
                .antMatchers("/api/products/**").permitAll()
                .antMatchers("/api/users").hasRole(UserRole.ADMIN.name())
                .anyRequest().authenticated();

        http
                .csrf().ignoringAntMatchers("/h2-console", "/h2-console**", "/h2-console/", "/h2-console/**", "/api/users/**")
                .and()
                .cors();

        http
                .headers()
                .frameOptions().disable();

        http
                .addFilterBefore(kakaoLoginFilter(), UsernamePasswordAuthenticationFilter.class)
                .addFilterBefore(githubLoginFilter(), UsernamePasswordAuthenticationFilter.class)
                .addFilterBefore(formLoginFilter(), UsernamePasswordAuthenticationFilter.class);
    }
}
