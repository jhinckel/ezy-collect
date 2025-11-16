package com.ezy.collect.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {
	
	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
	    http.authorizeHttpRequests(auth -> auth.anyRequest().permitAll())
	      .csrf(AbstractHttpConfigurer::disable);
	    return http.build();
	}
	
//    @Bean
//    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
//    	http.authorizeHttpRequests(authorized -> {
//            // authorized.requestMatchers("/user/**").authenticated();
//            authorized.anyRequest().permitAll();
//        });
//    	http.cors(cors -> cors.configurationSource(corsConfigurationSource()));
//    	
//        return http.build();
//    }
//    
//    @Bean
//    public CorsConfigurationSource corsConfigurationSource() {
//    	UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
//        CorsConfiguration config = new CorsConfiguration();
//
//        config.addAllowedOrigin("");
//        config.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE", "OPTIONS"));
//        config.setAllowedHeaders(Arrays.asList("*"));
//        config.setAllowCredentials(true);
//        // config.setMaxAge(3600L);
//
//        source.registerCorsConfiguration("/**", config);
//        
//        return source;
//    }
	
//    @Override
//    public void configure(HttpSecurity http) throws Exception {
//        http.csrf()
//                .disable().authorizeRequests()
//                    .antMatchers("/api/error/**").permitAll()
//                    .antMatchers("/etl/**").permitAll()
//                    .antMatchers("/actuator/health").permitAll()
//                    .antMatchers("/oauth/token").permitAll()
//                    .antMatchers("/v1/webhooks/**").permitAll()
//                    .antMatchers("/v1/version/**").permitAll()
//                    .antMatchers("/v2/version/**").permitAll()
//                    .antMatchers("/swagger/**", "/api-docs/**",
//                            "/v3/api-docs/**", "/swagger-ui/**").permitAll()
//                    .and()
//                .authorizeRequests()
//                    .anyRequest().authenticated()
//                .and().cors()
//                .and().oauth2ResourceServer().jwt();
//    }

}