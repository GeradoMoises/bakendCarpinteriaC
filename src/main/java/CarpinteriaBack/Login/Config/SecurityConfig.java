package CarpinteriaBack.Login.Config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    // ✅ Encoder (lo mismo que ya tenías)
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    // ✅ Provider que le dice a Spring Security: "usa UserDetailsService (BD) + BCrypt"
    @Bean
    public DaoAuthenticationProvider authProvider(UserDetailsService userDetailsService,
                                                  PasswordEncoder passwordEncoder) {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setUserDetailsService(userDetailsService);
        provider.setPasswordEncoder(passwordEncoder);
        return provider;
    }


    // ✅ AuthenticationManager (útil si luego quieres login con /api/auth/login usando authenticate())
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        return http
                .csrf(csrf -> csrf.disable())
                .cors(Customizer.withDefaults())
                .authorizeHttpRequests(auth -> auth

                        // 🔓 Público
                        .requestMatchers(
                                "/api/auth/login",
                                "/",
                                "/index.html",
                                "/assets/**",
                                "/producto/public/**"
                        ).permitAll()

                        // 🛑 Solo ADMINISTRADOR (según tu BD)
                        .requestMatchers(
                                "/api/dashboard/**",
                                "/api/admin/**",
                                "/api/productos/**",
                                "/api/empleados/**",
                                "/api/usuarios/**",
                                "/api/roles/**",
                                "/api/clientes/**",     // ✅ AGREGA ESTO
                                "/categoria/api/**",      // ✅ AGREGAR
                                "/api/pedidos/**",
                                "/lineadiseno/api/**",
                                "/producto/api/**"
                        ).hasRole("ADMINISTRADOR")

                        .anyRequest().authenticated()
                )
                // ✅ Para probar rápido con Angular/Postman usando Basic Auth
                .httpBasic(Customizer.withDefaults())
                .build();
    }

    // ✅ CORS para Angular
    @Bean
    public org.springframework.web.cors.CorsConfigurationSource corsConfigurationSource() {
        var config = new org.springframework.web.cors.CorsConfiguration();

        config.setAllowedOrigins(java.util.List.of(
                "http://localhost:4200",
                "https://geradomoises.github.io"
        ));

        config.setAllowedMethods(java.util.List.of("GET","POST","PUT","PATCH","DELETE","OPTIONS"));
        config.setAllowedHeaders(java.util.List.of("*"));
        config.setExposedHeaders(java.util.List.of("Authorization"));
        config.setAllowCredentials(true);

        var source = new org.springframework.web.cors.UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", config);
        return source;
    }

}
