package ar.edu.itba.pawddit.webapp.config;

import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import ar.edu.itba.pawddit.webapp.auth.PawdditUserDetailsService;

@Configuration
@EnableWebSecurity
@ComponentScan("ar.edu.itba.pawddit.webapp.auth")
public class WebAuthConfig extends WebSecurityConfigurerAdapter {
	
	@Autowired
	private PawdditUserDetailsService userDetailsService;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
	        auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder);
	}
	
	@Override
	protected void configure(final HttpSecurity http) throws Exception {
		http.userDetailsService(userDetailsService)
			.sessionManagement()
				.invalidSessionUrl("/login")
			.and().authorizeRequests()
				.antMatchers("/login").anonymous()
				.antMatchers("/**/createGroup").authenticated()
				.antMatchers("/**/createPost").authenticated()
				.antMatchers("/profile").authenticated()
				.antMatchers("/admin/**").hasRole("ADMIN")
				.antMatchers("/**").permitAll()
			.and().formLogin()
				.usernameParameter("j_username")
				.passwordParameter("j_password")
				.defaultSuccessUrl("/", false)
				.loginPage("/login")
			.and().rememberMe()
				.rememberMeParameter("j_rememberme")
				.userDetailsService(userDetailsService)
				.key("mysupersecretkeythatnobodyknowsabout") // CAMBIAR
				.tokenValiditySeconds((int) TimeUnit.DAYS.toSeconds(30))
			.and().logout()
				.logoutUrl("/logout")
				.logoutSuccessUrl("/login")
			.and().exceptionHandling()
				.accessDeniedPage("/403")
			.and().csrf().disable();
	}
	
	@Override
	public void configure(final WebSecurity web) throws Exception {
		web.ignoring()
			.antMatchers("/resources/**");
	}
	
	@Bean
	public PasswordEncoder passwordEncoder() {
	    return new BCryptPasswordEncoder();
	}
}
