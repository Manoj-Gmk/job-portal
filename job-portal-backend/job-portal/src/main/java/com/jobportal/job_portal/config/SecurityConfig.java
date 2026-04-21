// package com.jobportal.job_portal.config;

// import com.jobportal.job_portal.security.JwtFilter;
// import lombok.RequiredArgsConstructor;
// import org.springframework.context.annotation.Bean;
// import org.springframework.context.annotation.Configuration;
// import org.springframework.security.authentication.AuthenticationManager;
// import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
// import org.springframework.security.config.annotation.web.builders.HttpSecurity;
// import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
// import org.springframework.security.config.http.SessionCreationPolicy;
// import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
// import org.springframework.security.crypto.password.PasswordEncoder;
// import org.springframework.security.web.SecurityFilterChain;
// import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
// import org.springframework.web.cors.CorsConfiguration;

// import java.util.List;

// @Configuration
// @EnableWebSecurity
// @RequiredArgsConstructor
// public class SecurityConfig {

//     private final JwtFilter jwtFilter;

//     @Bean
//     public SecurityFilterChain securityFilterChain(HttpSecurity http)
//             throws Exception {

//         http
//                 .cors(cors -> cors.configurationSource(request -> {
//                     CorsConfiguration config = new CorsConfiguration();
//                     config.setAllowedOrigins(List.of(
//                             "http://localhost:5173",
//                             "https://job-portal-pi-wheat-78.vercel.app"));
//                     config.setAllowedMethods(List.of("GET", "POST", "PUT",
//                             "DELETE", "OPTIONS"));
//                     config.setAllowedHeaders(List.of("*"));
//                     config.setAllowCredentials(true);
//                     return config;
//                 }))
//                 .csrf(csrf -> csrf.disable())
//                 .sessionManagement(session -> session
//                         .sessionCreationPolicy(SessionCreationPolicy.STATELESS))
//                 .authorizeHttpRequests(auth -> auth
//                         .requestMatchers("/api/auth/**").permitAll()
//                         .requestMatchers("/api/jobs/search").permitAll()
//                         .requestMatchers("/api/jobs/all").permitAll()
//                         .requestMatchers("/api/jobs/post").hasRole("RECRUITER")
//                         .requestMatchers("/api/jobs/delete/**").hasRole("RECRUITER")
//                         .requestMatchers("/api/applications/**")
//                         .hasAnyRole("CANDIDATE", "RECRUITER")
//                         .anyRequest().authenticated())
//                 .addFilterBefore(jwtFilter,
//                         UsernamePasswordAuthenticationFilter.class);

//         return http.build();
//     }

//     @Bean
//     public PasswordEncoder passwordEncoder() {
//         return new BCryptPasswordEncoder();
//     }

//     @Bean
//     public AuthenticationManager authenticationManager(
//             AuthenticationConfiguration config) throws Exception {
//         return config.getAuthenticationManager();
//     }
// }

// package com.jobportal.job_portal.config;

// import com.jobportal.job_portal.security.JwtFilter;
// import lombok.RequiredArgsConstructor;
// import org.springframework.context.annotation.Bean;
// import org.springframework.context.annotation.Configuration;
// import org.springframework.security.authentication.AuthenticationManager;
// import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
// import org.springframework.security.config.annotation.web.builders.HttpSecurity;
// import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
// import org.springframework.security.config.http.SessionCreationPolicy;
// import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
// import org.springframework.security.crypto.password.PasswordEncoder;
// import org.springframework.security.web.SecurityFilterChain;
// import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
// import org.springframework.web.cors.CorsConfiguration;
// import org.springframework.web.cors.CorsConfigurationSource;
// import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

// import java.util.Arrays;
// import java.util.List;

// @Configuration
// @EnableWebSecurity
// @RequiredArgsConstructor
// public class SecurityConfig {

//         private final JwtFilter jwtFilter;

//         @Bean
//         public CorsConfigurationSource corsConfigurationSource() {
//                 CorsConfiguration config = new CorsConfiguration();
//                 config.setAllowedOrigins(Arrays.asList(
//                                 "http://localhost:5173",
//                                 "https://job-portal-pi-wheat-78.vercel.app"));
//                 config.setAllowedMethods(Arrays.asList(
//                                 "GET", "POST", "PUT", "DELETE", "OPTIONS", "HEAD", "PATCH"));
//                 config.setAllowedHeaders(Arrays.asList(
//                                 "Authorization",
//                                 "Content-Type",
//                                 "Accept",
//                                 "Origin",
//                                 "Access-Control-Request-Method",
//                                 "Access-Control-Request-Headers"));
//                 config.setExposedHeaders(List.of("Authorization"));
//                 config.setAllowCredentials(true);
//                 config.setMaxAge(3600L);

//                 UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
//                 source.registerCorsConfiguration("/**", config);
//                 return source;
//         }

//         @Bean
//         public SecurityFilterChain securityFilterChain(HttpSecurity http)
//                         throws Exception {

//                 http
//                                 .cors(cors -> cors.configurationSource(
//                                                 corsConfigurationSource()))
//                                 .csrf(csrf -> csrf.disable())
//                                 .sessionManagement(session -> session
//                                                 .sessionCreationPolicy(
//                                                                 SessionCreationPolicy.STATELESS))
//                                 .authorizeHttpRequests(auth -> auth
//                                                 .requestMatchers("/api/auth/**").permitAll()
//                                                 .requestMatchers("/api/jobs/search").permitAll()
//                                                 .requestMatchers("/api/jobs/all").permitAll()
//                                                 .requestMatchers("/api/jobs/**").permitAll()
//                                                 .requestMatchers(org.springframework.http.HttpMethod.OPTIONS, "/**")
//                                                 .permitAll()
//                                                 .requestMatchers("/api/jobs/post").hasRole("RECRUITER")
//                                                 .requestMatchers("/api/jobs/delete/**").hasRole("RECRUITER")
//                                                 .requestMatchers("/api/applications/**")
//                                                 .hasAnyRole("CANDIDATE", "RECRUITER")
//                                                 .anyRequest().authenticated())
//                                 .addFilterBefore(jwtFilter,
//                                                 UsernamePasswordAuthenticationFilter.class);

//                 return http.build();
//         }

//         @Bean
//         public PasswordEncoder passwordEncoder() {
//                 return new BCryptPasswordEncoder();
//         }

//         @Bean
//         public AuthenticationManager authenticationManager(
//                         AuthenticationConfiguration config) throws Exception {
//                 return config.getAuthenticationManager();
//         }
// }
/////////////////////////////
/// 
package com.jobportal.job_portal.config;

import com.jobportal.job_portal.security.JwtFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;
import java.util.List;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

        private final JwtFilter jwtFilter;

        @Bean
        public CorsConfigurationSource corsConfigurationSource() {
                CorsConfiguration config = new CorsConfiguration();
                config.setAllowedOrigins(Arrays.asList(
                                "http://localhost:5173",
                                "https://job-portal-pi-wheat-78.vercel.app"));
                config.setAllowedMethods(Arrays.asList(
                                "GET", "POST", "PUT", "DELETE", "OPTIONS", "HEAD", "PATCH"));
                config.setAllowedHeaders(List.of("*"));
                config.setExposedHeaders(List.of("Authorization"));
                config.setAllowCredentials(true);
                config.setMaxAge(3600L);

                UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
                source.registerCorsConfiguration("/**", config);
                return source;
        }

        @Bean
        public SecurityFilterChain securityFilterChain(HttpSecurity http)
                        throws Exception {
                http
                                .cors(cors -> cors.configurationSource(corsConfigurationSource()))
                                .csrf(csrf -> csrf.disable())
                                .sessionManagement(session -> session
                                                .sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                                .authorizeHttpRequests(auth -> auth
                                                // Allow OPTIONS preflight first
                                                .requestMatchers(HttpMethod.OPTIONS, "/**").permitAll()
                                                // Allow auth endpoints
                                                .requestMatchers("/api/auth/**").permitAll()
                                                // Allow GET requests for jobs publicly
                                                .requestMatchers(HttpMethod.GET, "/api/jobs/all").permitAll()
                                                .requestMatchers(HttpMethod.GET, "/api/jobs/search").permitAll()
                                                .requestMatchers(HttpMethod.GET, "/api/jobs/{id}").permitAll()
                                                // Recruiter only
                                                .requestMatchers(HttpMethod.POST, "/api/jobs/post").hasRole("RECRUITER")
                                                .requestMatchers(HttpMethod.DELETE, "/api/jobs/delete/**")
                                                .hasRole("RECRUITER")
                                                .requestMatchers(HttpMethod.GET, "/api/jobs/my-jobs")
                                                .hasRole("RECRUITER")
                                                // Applications
                                                .requestMatchers("/api/applications/**")
                                                .hasAnyRole("CANDIDATE", "RECRUITER")
                                                // Everything else needs auth
                                                .anyRequest().authenticated())
                                .addFilterBefore(jwtFilter,
                                                UsernamePasswordAuthenticationFilter.class);

                return http.build();
        }

        @Bean
        public PasswordEncoder passwordEncoder() {
                return new BCryptPasswordEncoder();
        }

        @Bean
        public AuthenticationManager authenticationManager(
                        AuthenticationConfiguration config) throws Exception {
                return config.getAuthenticationManager();
        }
}