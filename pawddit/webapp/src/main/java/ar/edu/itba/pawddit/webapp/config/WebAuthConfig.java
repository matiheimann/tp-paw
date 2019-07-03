package ar.edu.itba.pawddit.webapp.config;

import java.util.Base64;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.Http403ForbiddenEntryPoint;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import ar.edu.itba.pawddit.webapp.auth.PawdditUserDetailsService;
import ar.edu.itba.pawddit.webapp.auth.StatelessAuthenticationFilter;
import ar.edu.itba.pawddit.webapp.auth.StatelessAuthenticationSuccessHandler;

@Configuration
@EnableWebSecurity
@ComponentScan("ar.edu.itba.pawddit.webapp.auth")
public class WebAuthConfig extends WebSecurityConfigurerAdapter {
	
	@Autowired
	private PawdditUserDetailsService userDetailsService;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@Autowired
	private StatelessAuthenticationSuccessHandler statelessAuthenticationSuccessHandler;
	
	@Autowired
	private StatelessAuthenticationFilter statelessAuthenticationFilter;
	
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
	        auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder);
	}
	
	@Override
	protected void configure(final HttpSecurity http) throws Exception {
		http.userDetailsService(userDetailsService)
			.authorizeRequests()
				.antMatchers(HttpMethod.POST, "/api/users/login").anonymous()
				.antMatchers(HttpMethod.POST, "/api/users/register").anonymous()
				.antMatchers("/api/users/me").permitAll()
				.antMatchers("/api/users/me/**").authenticated()
				.antMatchers(HttpMethod.POST).authenticated()
				.antMatchers(HttpMethod.DELETE).authenticated()
				.antMatchers(HttpMethod.PUT).authenticated()
				.antMatchers("/**").permitAll()
			.and().sessionManagement()
				.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
			.and().formLogin()
				.usernameParameter("jUsername")
				.passwordParameter("jPassword")
				.loginProcessingUrl("/api/users/login")
				.successHandler(statelessAuthenticationSuccessHandler)
				.failureHandler(new SimpleUrlAuthenticationFailureHandler())
			.and().exceptionHandling()
				.authenticationEntryPoint(new Http403ForbiddenEntryPoint())
			.and().csrf().disable()
				.addFilterBefore(statelessAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)
				.headers().cacheControl().disable();
	}
	
	@Override
	public void configure(final WebSecurity web) throws Exception {
		web.ignoring()
			.antMatchers("/index.html", "/bower_components/**", "/images/**", "/scripts/**", "/styles/**", "/views/**");
	}
	
	@Bean
	@Override
	public AuthenticationManager authenticationManagerBean() throws Exception {
		return super.authenticationManagerBean();
	}
	
	@Bean
	public PasswordEncoder passwordEncoder() {
	    return new BCryptPasswordEncoder();
	}
	
	@Bean
	public String authTokenSecretKey() {
		return Base64.getEncoder().encodeToString("576e5a7134743777217a25432a462d4a".getBytes());
	}
}
