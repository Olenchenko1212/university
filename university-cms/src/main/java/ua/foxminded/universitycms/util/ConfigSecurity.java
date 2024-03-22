package ua.foxminded.universitycms.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import ua.foxminded.universitycms.service.CustomUserDetailsService;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(securedEnabled = true)
public class ConfigSecurity {
	
	@Autowired
    private CustomUserDetailsService customUserDetailsService;
	
	@Bean
	public static BCryptPasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
	@Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(customUserDetailsService).passwordEncoder(passwordEncoder());
    }
	
	@Autowired
	private CustomSuccessHandler successHandler;

	@Bean
	public SecurityFilterChain configufe(HttpSecurity httpSecurity) throws Exception {
		return httpSecurity.csrf().disable()
				.authorizeHttpRequests()
					.antMatchers("/login/**").permitAll()
					.antMatchers("/registration/**").permitAll()
					.and()
				.authorizeHttpRequests().antMatchers("/admin-panel/**").hasRole("ADMIN").and()
				.authorizeHttpRequests().antMatchers("/menu/**").hasAnyRole("USER", "ADMIN", "STUDENT", "TEACHER", "STUFF")
				.and()
				.formLogin()
					.loginPage("/login")
					.successHandler(successHandler)
					.permitAll()
					.and()
				.logout().permitAll()
				.and()
				.build();
	}
}
