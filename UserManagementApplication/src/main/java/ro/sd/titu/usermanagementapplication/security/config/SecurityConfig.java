package ro.sd.titu.usermanagementapplication.security.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import ro.sd.titu.usermanagementapplication.config.AppConfig;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private final AppConfig appConfig;
    private final AuthenticationProvider authenticationProvider;
    private final JwtAuthenticationFilter jwtAuthFilter;

    public SecurityConfig(AppConfig appConfig, AuthenticationProvider authenticationProvider, JwtAuthenticationFilter jwtAuthFilter) {
        this.appConfig = appConfig;
        this.authenticationProvider = authenticationProvider;
        this.jwtAuthFilter = jwtAuthFilter;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http.csrf(AbstractHttpConfigurer::disable)
                .cors(httpSecurityCorsConfigurer -> httpSecurityCorsConfigurer.configurationSource(appConfig.corsConfigurationSource()))
                .authorizeHttpRequests(request -> {
                    request.requestMatchers("/auth/**").permitAll();
                    request.requestMatchers(HttpMethod.OPTIONS, "/**").permitAll();
                    request.requestMatchers(HttpMethod.GET, "/users/{id}").permitAll();
                    request.requestMatchers(HttpMethod.GET, "/users/role/{role}").permitAll();
                    request.requestMatchers(HttpMethod.POST, "/users")
                            .hasAnyAuthority("ADMIN");
                    request.requestMatchers(HttpMethod.DELETE, "/users/{id}")
                            .hasAnyAuthority("ADMIN");
                    request.requestMatchers(HttpMethod.GET, "/users")
                            .hasAnyAuthority("ADMIN");
                    request.requestMatchers(HttpMethod.PUT, "/users")
                            .hasAnyAuthority("ADMIN", "CLIENT");
                    request.anyRequest().authenticated();
                })
                .authenticationProvider(authenticationProvider)
                .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class)
                .httpBasic(Customizer.withDefaults())
                .sessionManagement(httpSecuritySessionManagementConfigurer -> httpSecuritySessionManagementConfigurer.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .build();
    }
}
