package com.lesson.memo.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import com.lesson.memo.security.AdminDetailService;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
	
	private final AdminDetailService adminDetailService;
	public SecurityConfig(AdminDetailService adminDetailService) {
		  this.adminDetailService = adminDetailService;
		 }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
    	http
    		.userDetailsService(adminDetailService)
    		.authorizeHttpRequests(authorize -> authorize
    				.requestMatchers("/css/**","/js/**").permitAll()
    				.requestMatchers("/admin/signup","/admin/signin").permitAll()
    				.anyRequest().authenticated()
    				)
    		.formLogin(login -> login
    				.loginPage("/admin/signin")
    				.loginProcessingUrl("/admin/signin")
    				.defaultSuccessUrl("/memo",true)
    				.permitAll()
    				)
    		.logout(logout -> logout
    				.logoutUrl("/admin/logout")
    				.logoutSuccessUrl("/admin/signin?logout")
    				.invalidateHttpSession(true)
    				.deleteCookies("JSESSIONID")
    				.permitAll()
    				);
    				
    	return http.build();
    }
    
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}