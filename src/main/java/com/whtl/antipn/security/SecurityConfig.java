package com.whtl.antipn.security;

import com.whtl.antipn.services.UserService;
import com.whtl.antipn.services.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;


@Configuration
@EnableWebSecurity
@ComponentScan("com.whtl.antipn.security")
public class SecurityConfig {

    private final CustomAuthenticationProvider customAuthenticationProvider;
    private final UserService userService;

    @Autowired
    public SecurityConfig(CustomAuthenticationProvider customAuthenticationProvider, UserService userService) {
        this.customAuthenticationProvider = customAuthenticationProvider;
        this.userService = userService;
    }

    @Bean
    public static PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }


    //https://stackoverflow.com/questions/37671125/how-to-configure-spring-security-to-allow-swagger-url-to-be-accessed-without-aut
    private static final String[] AUTH_WHITELIST = {
            // -- Swagger UI v2
            "/v2/api-docs",
            "/swagger-resources",
            "/swagger-resources/**",
            "/configuration/ui",
            "/configuration/security",
            "/swagger-ui.html",
            "/webjars/**",
            // -- Swagger UI v3 (OpenAPI)
            "/v3/api-docs/**",
            "/swagger-ui/**"
            // other public endpoints of your API may be appended to this array
    };

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        return http
                .csrf(csrf -> csrf.disable())

                .authorizeHttpRequests(auth -> {
                    auth
                            .antMatchers(AUTH_WHITELIST).permitAll()//swagger
                            .antMatchers("/registration", "/registration/save", "/login", "/error").permitAll()

                            .antMatchers(HttpMethod.POST,"/api/v1/restaurants").hasRole("ADMIN")
                            .antMatchers(HttpMethod.PUT,"/api/v1/restaurants").hasRole("ADMIN")
                            .antMatchers(HttpMethod.DELETE,"/api/v1/restaurants").hasRole("ADMIN")

                            .antMatchers(HttpMethod.GET,"/api/v1/vote").hasRole("USER")
                            .antMatchers(HttpMethod.POST,"/api/v1/vote").hasRole("USER")
                            .antMatchers(HttpMethod.PUT,"/api/v1/vote").hasRole("USER")
                            .antMatchers(HttpMethod.DELETE,"/api/v1/vote").hasRole("USER")
                            .anyRequest().authenticated();
                })

                .formLogin(formLogin -> {
                    formLogin.loginPage("/login");
                    formLogin.loginProcessingUrl("/process_login");
                    formLogin.usernameParameter("email");
                    formLogin.defaultSuccessUrl("/welcome", true);
                    formLogin.failureUrl("/login?error");
                })

                .logout()
                .logoutUrl("/logout")
                .logoutSuccessUrl("/html/login")

                .invalidateHttpSession(true)
                .deleteCookies("JSESSIONID")
                .and()
                .httpBasic(Customizer.withDefaults())
                .build();
    }

    @Bean
    public AuthenticationManager authManager(HttpSecurity http) throws Exception {
        AuthenticationManagerBuilder authenticationManagerBuilder =
                http.getSharedObject(AuthenticationManagerBuilder.class);
        authenticationManagerBuilder.authenticationProvider(customAuthenticationProvider);
        return authenticationManagerBuilder.build();
    }

    @Bean
    public DaoAuthenticationProvider authProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(userService);
        authProvider.setPasswordEncoder(passwordEncoder());
        return authProvider;
    }

}
