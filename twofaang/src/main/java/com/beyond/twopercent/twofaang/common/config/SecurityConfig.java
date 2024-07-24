package com.beyond.twopercent.twofaang.common.config;

import com.beyond.twopercent.twofaang.auth.handler.CustomFormSuccessHandler;
import com.beyond.twopercent.twofaang.auth.handler.CustomLogoutFilter;
import com.beyond.twopercent.twofaang.auth.handler.CustomOAuth2SuccessHandler;
import com.beyond.twopercent.twofaang.auth.jwt.JWTFilter;
import com.beyond.twopercent.twofaang.auth.jwt.JWTUtil;
import com.beyond.twopercent.twofaang.auth.repository.RefreshRepository;
import com.beyond.twopercent.twofaang.auth.service.CustomOAuth2UserService;
import com.beyond.twopercent.twofaang.auth.service.RefreshTokenService;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.logout.LogoutFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;

import java.io.IOException;
import java.util.Collections;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class    SecurityConfig {

    private final AuthenticationConfiguration authenticationConfiguration;
    private final JWTUtil jwtUtil;
    private final RefreshRepository refreshRepository;
    private final RefreshTokenService refreshTokenService;
    private final CustomOAuth2UserService customOAuth2UserService;

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {

        return configuration.getAuthenticationManager();
    }

    @Bean
    public AuthenticationFailureHandler authenticationFailureHandler(){
        return new AuthenticationFailureHandler() {
            @Override
            public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {
                System.out.println("exception = " + exception);
                response.setStatus(HttpStatus.UNAUTHORIZED.value());
            }
        };
    }

    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {

        return new BCryptPasswordEncoder();
    }

    @Bean
    public WebSecurityCustomizer webSecurityCustomizer() {
        return (web) -> web.ignoring().requestMatchers(
                "/favicon.ico",
                "/swagger-ui/**",
                "/",
                "/swagger-config",
                "/swagger.yaml",
                "/requestBodies/**",
                "/swagger-*.yaml",
                "/error",
                "/v3/api-docs/**",
                "/files/**",
                "/assets/**",
                "/css/**",
                "/images/**",
                "/js/**",
                "/product/**",
                "/admin/**",
                "/inquiries/**"
        );
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        // disable
        http
                .httpBasic((basic) -> basic.disable())
                .csrf((csrf) -> csrf.disable());

        // form
        http
                .formLogin((form) -> form.loginPage("/login")
                        .loginProcessingUrl("/login")
                        .successHandler(new CustomFormSuccessHandler(jwtUtil, refreshTokenService))
                        .failureHandler(authenticationFailureHandler())
                        .permitAll());

        // oauth2
        http
                .oauth2Login((oauth2) -> oauth2
                        .loginPage("/login")
                        .userInfoEndpoint((userinfo) -> userinfo
                                .userService(customOAuth2UserService))
                        .successHandler(new CustomOAuth2SuccessHandler(jwtUtil, refreshTokenService))
                        .failureHandler(authenticationFailureHandler())
                        .permitAll());

        // logout
        http
                .logout((auth) -> auth
                        .logoutSuccessUrl("/")
                        .permitAll());

        http
                .authorizeHttpRequests((auth) -> auth
                        .requestMatchers("/login", "/", "/join", "/reissue").permitAll()
//                        .requestMatchers("/admin/**").hasRole("ADMIN") // 관리자만 접근할 수 있도록 설정
                        .anyRequest().authenticated());
        http
                .addFilterBefore(new JWTFilter(jwtUtil), UsernamePasswordAuthenticationFilter.class);
        http
                .addFilterBefore(new CustomLogoutFilter(jwtUtil, refreshRepository), LogoutFilter.class);
        //세션 설정
        http
                .sessionManagement((session) -> session
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS));

        return http.build();
    }
}
