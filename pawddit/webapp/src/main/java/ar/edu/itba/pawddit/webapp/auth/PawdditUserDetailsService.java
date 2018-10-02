package ar.edu.itba.pawddit.webapp.auth;

import java.util.Collection;
import java.util.HashSet;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
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
	
	@Override
	public UserDetails loadUserByUsername(final String username) {
		final User user = us.findByUsername(username).orElseThrow(() -> new UserNotFoundException());
		
		final Collection<GrantedAuthority> authorities = new HashSet<>();
		authorities.add(new SimpleGrantedAuthority("ROLE_USER"));
		if (user.getIsAdmin())
			authorities.add(new SimpleGrantedAuthority("ROLE_ADMIN"));

		return new org.springframework.security.core.userdetails.User(username, user.getPassword(), user.getEnabled(), true, true, true, authorities);
	}
}
