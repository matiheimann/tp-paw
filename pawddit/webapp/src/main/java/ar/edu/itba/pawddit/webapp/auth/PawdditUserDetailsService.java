package ar.edu.itba.pawddit.webapp.auth;

import java.util.Collection;
import java.util.HashSet;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;

import ar.edu.itba.pawddit.model.User;
import ar.edu.itba.pawddit.services.UserService;
import ar.edu.itba.pawddit.webapp.exceptions.UserNotFoundException;

@Component
public class PawdditUserDetailsService implements UserDetailsService {
	
	@Autowired
	private UserService us;
	
	private static final Logger LOGGER = LoggerFactory.getLogger(PawdditUserDetailsService.class);
	
	@Override
	public UserDetails loadUserByUsername(final String username) {
		final User user = us.findByUsername(username).orElseThrow(UserNotFoundException::new);
		
		final Collection<GrantedAuthority> authorities = new HashSet<>();
		authorities.add(new SimpleGrantedAuthority("ROLE_USER"));
		if (user.getIsAdmin())
			authorities.add(new SimpleGrantedAuthority("ROLE_ADMIN"));

		return new org.springframework.security.core.userdetails.User(username, user.getPassword(), user.getEnabled(), true, true, true, authorities);
	}
	
	public User getLoggedUser() {
		final Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		if (auth == null || !auth.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_USER"))) {
			return null;
		}
		
		final User user = us.findByUsername(auth.getName()).orElseThrow(UserNotFoundException::new);
		LOGGER.debug("Currently logged user is {}", user.getUserid());
		return user;
	}
}
