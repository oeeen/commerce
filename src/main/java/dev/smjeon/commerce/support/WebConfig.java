package dev.smjeon.commerce.support;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import java.util.Arrays;
import java.util.List;

import static org.modelmapper.config.Configuration.AccessLevel.PRIVATE;

@Configuration
@Order(1)
public class WebConfig implements WebMvcConfigurer {
    private final HandlerInterceptorAdapter interceptor;

    public WebConfig(HandlerInterceptorAdapter interceptor) {
        this.interceptor = interceptor;
    }

    @Bean
    public ModelMapper modelMapper() {
        ModelMapper modelMapper = new ModelMapper();
        modelMapper.getConfiguration()
                .setFieldAccessLevel(PRIVATE)
                .setFieldMatchingEnabled(true);

        return modelMapper;
    }

    @Bean
    public PasswordEncoder getPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        List<String> patterns = Arrays.asList("/", "/api/users/signin", "/api/users/signup", "/js/**", "/css/**", "/images/**");
        registry.addInterceptor(this.interceptor)
                .excludePathPatterns(patterns)
                .addPathPatterns("/**");
    }
}
