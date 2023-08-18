package com.appsdeveloperblog.clients.sociallogin.SocialLoginWebClient.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.oauth2.client.oidc.web.logout.OidcClientInitiatedLogoutSuccessHandler;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
public class WebSecurity {

	@Autowired
	ClientRegistrationRepository clientRegistrationRepository;

	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

	    http.authorizeHttpRequests(authorizeRequests ->
	            authorizeRequests
	                .requestMatchers(new AntPathRequestMatcher("/"))
	                .permitAll()
	                .anyRequest()
	                .authenticated()
	    )
	    .oauth2Login(Customizer.withDefaults())
	    .logout(logout -> logout
	        .logoutSuccessHandler(oidcLogoutSuccessHandler())
	        .invalidateHttpSession(true)
	        .clearAuthentication(true)
	        .deleteCookies("JSESSIONID")
	    );

	    return http.build();
	}


	private OidcClientInitiatedLogoutSuccessHandler oidcLogoutSuccessHandler() {
		OidcClientInitiatedLogoutSuccessHandler successHandler = new OidcClientInitiatedLogoutSuccessHandler(
				clientRegistrationRepository);
		successHandler.setPostLogoutRedirectUri("http://localhost:8080/");
		return successHandler;
	}

}
