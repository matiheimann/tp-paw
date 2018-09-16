package ar.edu.itba.pawddit.webapp.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import ar.edu.itba.pawddit.model.User;
import ar.edu.itba.pawddit.services.GroupService;
import ar.edu.itba.pawddit.services.PostService;
import ar.edu.itba.pawddit.services.UserService;
import ar.edu.itba.pawddit.webapp.exceptions.UserNotFoundException;
import ar.edu.itba.pawddit.webapp.form.UserRegisterForm;

@Controller
public class UserController {
	
	@Autowired
	private UserService us;
	
	@Autowired
	private GroupService gs;
	
	@Autowired
	private PostService ps;
	
	@RequestMapping("/register")
	public ModelAndView register(@ModelAttribute("registerForm") final UserRegisterForm form) {
		return new ModelAndView("register");
	}
		
	@RequestMapping(value = "/register", method = { RequestMethod.POST })
	public ModelAndView registerPost(@Valid @ModelAttribute("registerForm") final UserRegisterForm form, final BindingResult errors) {
		if(errors.hasErrors()) {
			return register(form);
		}
		
		us.create(form.getUsername(), form.getPassword(), form.getEmail(), 0);
		return new ModelAndView("redirect:/");
	}
	
	@RequestMapping("/login")
	public ModelAndView login() {
		return new ModelAndView("login");
	}
	
	@RequestMapping("/profile")
	public ModelAndView profile() {
		final ModelAndView mav = new ModelAndView("profile");
		final Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		final User user = us.findByUsername(auth.getName()).orElseThrow(UserNotFoundException::new);
		mav.addObject("user", user);
		mav.addObject("posts", ps.findByUser(user));
		return mav;
	}
}
