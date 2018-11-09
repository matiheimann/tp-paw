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

import ar.edu.itba.pawddit.webapp.auth.PawdditUserDetailsService;

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
				.antMatchers("/login").anonymous()
				.antMatchers("/register").anonymous()
				.antMatchers("/admin/**").hasRole("ADMIN")
				.antMatchers("/recommendedGroups").authenticated()
				.antMatchers("/createGroup").authenticated()
				.antMatchers("/createPost").authenticated()
				.antMatchers(HttpMethod.POST, "/group/*").authenticated()
				.antMatchers("/**/subscribe").authenticated()
				.antMatchers("/**/unsubscribe").authenticated()
				.antMatchers("/**/upvote").authenticated()
				.antMatchers("/**/downvote").authenticated()
				.antMatchers("/**/delete").authenticated()
				.antMatchers("/**").permitAll()
			.and().formLogin()
				.usernameParameter("j_username")
				.passwordParameter("j_password")
				.defaultSuccessUrl("/all", false)
				.failureUrl("/login?error=true")
				.loginPage("/login")
			.and().rememberMe()
				.rememberMeParameter("j_rememberme")
				.userDetailsService(userDetailsService)
				.key(getRememberMeKey())
				.tokenValiditySeconds((int) TimeUnit.DAYS.toSeconds(30))
			.and().logout()
				.logoutUrl("/logout")
				.logoutSuccessUrl("/")
			.and().exceptionHandling()
				.accessDeniedPage("/invalidUrl")
			.and().csrf().disable();
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
