package ar.edu.itba.pawddit.webapp.controller;

import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import ar.edu.itba.pawddit.model.User;
import ar.edu.itba.pawddit.services.GroupService;
import ar.edu.itba.pawddit.services.PostService;
import ar.edu.itba.pawddit.services.UserService;
import ar.edu.itba.pawddit.webapp.exceptions.UserNotFoundException;
import ar.edu.itba.pawddit.webapp.form.UserLoginForm;
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
		final ModelAndView mav = new ModelAndView("register");
		return mav;
	}
		
	@RequestMapping(value = "/register", method = { RequestMethod.POST })
	public ModelAndView registerPost(@Valid @ModelAttribute("registerForm") final UserRegisterForm form, final BindingResult errors) {
		if(errors.hasErrors()) {
			return register(form);
		}
		
		final User u = us.create(form.getUsername(), form.getPassword(), form.getEmail(), 0);
		return new ModelAndView("redirect:/?userId=" + u.getUserid());
	}
	
	@RequestMapping("/login")
	public ModelAndView login(@ModelAttribute("loginForm") final UserLoginForm form) {
		final ModelAndView mav = new ModelAndView("login");
		return mav;
	}
	
	@RequestMapping(value = "/login", method = { RequestMethod.POST })
	public ModelAndView loginPost(@Valid @ModelAttribute("loginForm") final UserLoginForm form, final BindingResult errors) {
		if(errors.hasErrors()) {
			return login(form);
		}
		
		final User u = us.login(form.getEmail(), form.getPassword()).orElseThrow(UserNotFoundException::new);
		return new ModelAndView("redirect:/?userId=" + u.getUserid());
	}
	
	@RequestMapping("/profile")
	public ModelAndView profile(@RequestParam(value = "userId", required = true) final Integer id) {
		final ModelAndView mav = new ModelAndView("profile");
		mav.addObject("user", us.findById(id).orElseThrow(UserNotFoundException::new));
		mav.addObject("posts", ps.findByUser(new User(null, null, null, null, id)));
		return mav;
	}
}
