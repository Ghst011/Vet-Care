package edu.pe.cibertec.config;

import edu.pe.cibertec.security.CustomUserDetailService;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    private final CustomUserDetailService userDetailService;

    public SecurityConfig(CustomUserDetailService userDetailService) {
        this.userDetailService = userDetailService;
    }

    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider(userDetailService);
        provider.setPasswordEncoder(passwordEncoder());
        return provider;
    }

    @Bean
    SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.authenticationProvider(authenticationProvider())
            .cors(cors -> {})
            .csrf(csrf -> csrf.ignoringRequestMatchers("/api/**", "/login", "/logout"))
            .authorizeHttpRequests(auth -> auth
                    .requestMatchers("/login", "/css/**", "/js/**", "/img/**", "/webjars/**").permitAll()
                    .requestMatchers("/api/**").permitAll()
                    .requestMatchers("/").authenticated()
                    .requestMatchers("/citas/nuevo", "/citas/guardar").hasAnyRole("ADMIN", "ASISTENTE")
                    .requestMatchers("/clientes/**", "/mascotas/**", "/citas/**").hasAnyRole("ADMIN", "ASISTENTE", "VETERINARIO")
                    .requestMatchers("/veterinarios/**", "/asistentes/**").hasRole("ADMIN")
                    .requestMatchers("/especies/**").hasAnyRole("ADMIN", "ASISTENTE")
                    .requestMatchers("/servicios/**").hasRole("ADMIN")
                    .requestMatchers("/usuarios/**").hasRole("ADMIN")
                    .requestMatchers("/pagos/**").hasRole("ADMIN")
                    .anyRequest().authenticated())

            .formLogin(login -> login
                    .loginPage("/login")
                    .loginProcessingUrl("/login")
                    .successHandler((request, response, authentication) -> {
                        if (esPeticionAjax(request)) {
                            response.setStatus(HttpServletResponse.SC_OK);
                        } else {
                            response.sendRedirect(request.getContextPath() + "/");
                        }
                    })
                    .failureHandler((request, response, exception) -> {
                        if (esPeticionAjax(request)) {
                            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                        } else {
                            response.sendRedirect(request.getContextPath() + "/login?error");
                        }
                    })
                    .permitAll())

            .logout(logout -> logout
                    .logoutSuccessHandler((request, response, authentication) -> {
                        if (esPeticionAjax(request)) {
                            response.setStatus(HttpServletResponse.SC_OK);
                        } else {
                            response.sendRedirect(request.getContextPath() + "/login?logout");
                        }
                    })
                    .permitAll());
        return http.build();
    }

    private boolean esPeticionAjax(jakarta.servlet.http.HttpServletRequest request) {
        return "XMLHttpRequest".equals(request.getHeader("X-Requested-With"));
    }
}