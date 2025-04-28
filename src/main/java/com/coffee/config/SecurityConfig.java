package com.coffee.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
                .csrf(csrf -> csrf.disable())
                .formLogin(form -> form
                        .loginPage("/signin")
                        .successHandler((request, response, authentication) -> {
                            response.sendRedirect(
                                    authentication.getAuthorities().stream()
                                            .anyMatch(a -> a.getAuthority().equals("ADMIN"))
                                            ? "/coffee" : "/orders-form" // 관리자일 경우 상품관리 페이지, 일반 유저는 주문 페이지로 이동
                            );
                        })
                        .failureHandler((request, response, exception) -> {
                            response.sendRedirect("/signin?error");
                        })
                        .permitAll()
                )
                .logout(logout -> logout
                        .logoutUrl("/logout")
                        .logoutSuccessUrl("/signin") // 로그아웃 시 무조건 로그인창으로 이동
                        .invalidateHttpSession(true)
                        .deleteCookies("JSESSIONID")
                        .permitAll()
                )
                .authorizeHttpRequests(
                        auth -> {
                            auth.requestMatchers("/signup", "/signin")
                                    .anonymous()
                                    .requestMatchers("/orders/**", "/orders-form")
                                    .hasAnyAuthority("USER","ADMIN")
                                    .requestMatchers("/coffee/**")
                                    .hasAnyAuthority("ADMIN")
                                    .anyRequest()
                                    .authenticated();
                        }
                )
                .sessionManagement( sm -> {
                            sm.maximumSessions(1) // 한 계정으로 중복 로그인 방지
                                    .maxSessionsPreventsLogin(true);
                            sm.sessionFixation().migrateSession(); // 세션 고정 공격 방지
                        }
                )
                .build();
    }
}
