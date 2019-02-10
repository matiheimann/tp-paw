package ar.edu.itba.pawddit.webapp.config;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.StringWriter;
import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.access.channel.ChannelProcessingFilter;
import org.springframework.security.web.authentication.Http403ForbiddenEntryPoint;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;

import ar.edu.itba.pawddit.webapp.auth.PawdditLoginSuccessHandler;
import ar.edu.itba.pawddit.webapp.auth.PawdditLogoutSuccessHandler;
import ar.edu.itba.pawddit.webapp.auth.PawdditUserDetailsService;
import ar.edu.itba.pawddit.webapp.auth.WebSecurityCorsFilter;

@Configuration
@EnableWebSecurity
@ComponentScan("ar.edu.itba.pawddit.webapp.auth")
public class WebAuthConfig extends WebSecurityConfigurerAdapter {
	
	@Value("classpath:rememberme.key")
	private Resource rememberMeKey;
	
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
			.authorizeRequests()
				.antMatchers(HttpMethod.POST, "/api/users/login").anonymous()
				.antMatchers(HttpMethod.POST, "/api/users/register").anonymous()
				.antMatchers("/api/users/logout").authenticated()
				.antMatchers("/api/users/me").permitAll()
				.antMatchers("/api/users/me/**").authenticated()
				.antMatchers(HttpMethod.POST).authenticated()
				.antMatchers(HttpMethod.DELETE).authenticated()
				.antMatchers(HttpMethod.PUT).authenticated()
				.antMatchers("/**").permitAll()
			.and().formLogin()
				.usernameParameter("jUsername")
				.passwordParameter("jPassword")
				.loginProcessingUrl("/api/users/login")
				.successHandler(new PawdditLoginSuccessHandler())
				.failureHandler(new SimpleUrlAuthenticationFailureHandler())
			.and().rememberMe()
				.rememberMeParameter("jRememberme")
				.userDetailsService(userDetailsService)
				.key(getRememberMeKey())
				.tokenValiditySeconds((int) TimeUnit.DAYS.toSeconds(30))
			.and().logout()
				.logoutUrl("/api/users/logout")
				.logoutSuccessHandler(new PawdditLogoutSuccessHandler())
			.and().exceptionHandling()
				.accessDeniedPage("/404")
				.authenticationEntryPoint(new Http403ForbiddenEntryPoint())
			.and().csrf().disable()
				.addFilterBefore(new WebSecurityCorsFilter(), ChannelProcessingFilter.class);
	}
	
	private String getRememberMeKey() {
		final StringWriter sw = new StringWriter();
		try (Reader reader = new InputStreamReader(rememberMeKey.getInputStream())) {
			char[] data = new char[1024];
			int len;
			while (( len = reader.read(data)) != -1) {
				sw.write(data, 0, len);
			}
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
		return sw.toString();
	}
	
	@Override
	public void configure(final WebSecurity web) throws Exception {
		web.ignoring()
			.antMatchers("/resources/**");
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
}
