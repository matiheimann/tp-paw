package ar.edu.itba.pawddit.webapp.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.servlet.ModelAndView;

import ar.edu.itba.pawddit.model.Group;
import ar.edu.itba.pawddit.model.User;
import ar.edu.itba.pawddit.services.GroupService;
import ar.edu.itba.pawddit.services.UserService;
import ar.edu.itba.pawddit.webapp.exceptions.GroupNotFoundException;
import ar.edu.itba.pawddit.webapp.exceptions.ImageNotFoundException;
import ar.edu.itba.pawddit.webapp.exceptions.PostNotFoundException;
import ar.edu.itba.pawddit.webapp.exceptions.UserNotFoundException;
import ar.edu.itba.pawddit.webapp.exceptions.VerificationTokenNotFoundException;

@ControllerAdvice
public class GlobalController {
	
	@Autowired
	private UserService us;
	
	@Autowired
	private GroupService gs;
	
	@ModelAttribute("user")
	public User loggedUser() {
		final Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		if (!auth.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_USER"))) {
			return null;
		}
		
		return us.findByUsername(auth.getName()).orElseThrow(UserNotFoundException::new);
	} 
	
	@ModelAttribute("groups")
	public List<Group> groups(@ModelAttribute("user") final User user) {
		final Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		if (!auth.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_USER"))) {
			return null;
		}
		
		return gs.getSuscribed(user);
	} 
	
	@ExceptionHandler(UserNotFoundException.class)
	public ModelAndView userNotFound() {
		final ModelAndView mav = new ModelAndView("redirect:/invalidUrl");
		return mav;
	}

	@ExceptionHandler(GroupNotFoundException.class)
	public ModelAndView groupNotFound() {
		final ModelAndView mav = new ModelAndView("redirect:/invalidUrl");
		return mav;
	}
	
	@ExceptionHandler(PostNotFoundException.class)
	public ModelAndView postNotFound() {
		final ModelAndView mav = new ModelAndView("redirect:/invalidUrl");
		return mav;
	}
	
	@ExceptionHandler(VerificationTokenNotFoundException.class)
	public ModelAndView verificationTokenNotFound() {
		final ModelAndView mav = new ModelAndView("redirect:/invalidUrl");
		return mav;
	}
	
	@ExceptionHandler(ImageNotFoundException.class)
	public ModelAndView imageNotFound() {
		final ModelAndView mav = new ModelAndView("redirect:/invalidUrl");
		return mav;
	}
	
}
