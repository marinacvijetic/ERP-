package bakery.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import lombok.RequiredArgsConstructor;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
@EnableMethodSecurity(securedEnabled = true)
public class SecurityConfig {
	
	@Autowired
	private JwtFilter jwtAuthFilter;
	
	@Autowired
	private AuthenticationProvider authenticationProvider;
	
	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception{
		http
				.cors(Customizer.withDefaults())
				.csrf(AbstractHttpConfigurer::disable)
				.authorizeHttpRequests(req -> 
							req.requestMatchers(
									"/auth/**",
									"/webhook/**",
									"/activate-account",
									"/v2/api-docs",
                                    "/v3/api-docs",
                                    "/v3/api-docs/**",
                                    "/swagger-resources",
                                    "/swagger-resources/**",
                                    "/configuration/ui",
                                    "/configuration/security",
                                    "/swagger-ui/**",
                                    "/webjars/**",
                                    "/swagger-ui.html"
							).permitAll()
							.requestMatchers(HttpMethod.GET, "/product/**").permitAll()
							.requestMatchers(HttpMethod.POST, "/product/**").hasRole("ADMIN")
							.requestMatchers(HttpMethod.PUT, "/product/**").hasRole("ADMIN")
							.requestMatchers(HttpMethod.DELETE, "/product/**").hasRole("ADMIN")
							.requestMatchers(HttpMethod.POST, "/orderArrivalDetails/**").hasRole("USER")
							.requestMatchers(HttpMethod.PUT, "/orderArrivalDetails/**").hasRole("USER")
							.requestMatchers(HttpMethod.GET, "/orderArrivalDetails/**").hasRole("ADMIN")
							.requestMatchers(HttpMethod.DELETE, "/orderArrivalDetails/**").hasRole("ADMIN")
							.requestMatchers(HttpMethod.GET, "/order/**").hasRole("ADMIN")
							.requestMatchers(HttpMethod.POST, "/order/**").authenticated()
							.requestMatchers(HttpMethod.GET, "/payments/**").authenticated()
							.requestMatchers(HttpMethod.GET, "/client-secret/**").authenticated()
							.requestMatchers(HttpMethod.PUT, "/order/**").hasRole("ADMIN")
							.requestMatchers(HttpMethod.DELETE, "/order/**").hasRole("ADMIN")
							.requestMatchers(HttpMethod.GET, "/orderItem/**").hasRole("ADMIN")
							.requestMatchers(HttpMethod.POST, "/orderItem/**").hasRole("ADMIN")
							.requestMatchers(HttpMethod.PUT, "/orderItem/**").hasRole("ADMIN")
							.requestMatchers(HttpMethod.DELETE, "/orderItem/**").hasRole("ADMIN")
							.requestMatchers(HttpMethod.GET, "/category/**").hasRole("ADMIN")
							.requestMatchers(HttpMethod.POST, "/category/**").hasRole("ADMIN")
							.requestMatchers(HttpMethod.PUT, "/category/**").hasRole("ADMIN")
							.requestMatchers(HttpMethod.DELETE, "/category/**").hasRole("ADMiN")
							.requestMatchers(HttpMethod.GET, "/orderStatus/**").hasRole("ADMIN")
							.requestMatchers(HttpMethod.POST, "/orderStatus/**").hasRole("ADMIN")
							.requestMatchers(HttpMethod.PUT, "/orderStatus/**").hasRole("ADMIN")
							.requestMatchers(HttpMethod.DELETE, "/orderStatus/**").hasRole("ADMIN")
							.requestMatchers(HttpMethod.GET, "/user/**").hasRole("ADMIN")
							.requestMatchers(HttpMethod.POST, "/user/**").hasRole("ADMIN")
							.requestMatchers(HttpMethod.PUT, "/user/**").hasRole("ADMIN")
							.requestMatchers(HttpMethod.DELETE, "/user/**").hasRole("ADMIN")
									.anyRequest()
										.authenticated()
						)
				.sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
				.authenticationProvider(authenticationProvider)
				.addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);
		
		return http.build();
	}

}
