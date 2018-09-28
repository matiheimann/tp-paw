package ar.edu.itba.pawddit.webapp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;

import ar.edu.itba.pawddit.model.User;
import ar.edu.itba.pawddit.services.UserService;
import ar.edu.itba.pawddit.webapp.exceptions.UserNotFoundException;

@Controller
public class BaseController {
	
	@Autowired
	private UserService us;
	
	@ModelAttribute("user")
	public User loggedUser() {
		final Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		if (!auth.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_USER"))) {
			return null;
		}
		
		return us.findByUsername(auth.getName()).orElseThrow(UserNotFoundException::new);
	}
}
