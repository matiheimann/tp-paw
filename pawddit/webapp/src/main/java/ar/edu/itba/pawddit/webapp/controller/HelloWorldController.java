package ar.edu.itba.pawddit.webapp.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import ar.edu.itba.pawddit.model.Group;
import ar.edu.itba.pawddit.model.User;
import ar.edu.itba.pawddit.services.GroupService;
import ar.edu.itba.pawddit.services.PostService;
import ar.edu.itba.pawddit.services.UserService;
import ar.edu.itba.pawddit.webapp.form.UserRegisterForm;

@Controller
public class HelloWorldController {

	@Autowired
	private UserService us;
	
	@Autowired
	private GroupService gs;
	
	@Autowired
	private PostService ps;

	@RequestMapping("/")
	public ModelAndView index(@RequestParam(value = "userId", required = false) final Integer id)
	{
		if (id == null) {
			final ModelAndView mav = new ModelAndView("welcome");
			mav.addObject("posts", ps.findAll());
			return mav;
		}
		else {
			final ModelAndView mav = new ModelAndView("index");
			mav.addObject("user", us.findById(id).orElseThrow(UserNotFoundException::new));
			return mav;
		}
	}
	
	@RequestMapping("/profile")
	public ModelAndView profile(@RequestParam(value = "userId", required = true) final Integer id) {
		final ModelAndView mav = new ModelAndView("profile");
		mav.addObject("user", us.findById(id).orElseThrow(UserNotFoundException::new));
		return mav;
	}
	
	@RequestMapping("/group/{groupName}")
	public ModelAndView group(@PathVariable final String groupName) {
		final ModelAndView mav = new ModelAndView("welcome");
		mav.addObject("posts", ps.findByGroup(new Group(groupName, null, null, null)));
		return mav;
	}
	
	@RequestMapping("/login")
	public ModelAndView login() {
		final ModelAndView mav = new ModelAndView("login");
		return mav;
	}
	
	@RequestMapping("/register")
	public ModelAndView register(@ModelAttribute("registerForm") final UserRegisterForm form) {
		final ModelAndView mav = new ModelAndView("register");
		return mav;
	}
		
	@RequestMapping(value = "/createUser", method = { RequestMethod.POST })
	public ModelAndView create(@Valid @ModelAttribute("registerForm") final UserRegisterForm form, final BindingResult errors) {
		if(errors.hasErrors()) {
			return register(form);
		}
		
		final User u = us.create(form.getUsername(), form.getPassword(), form.getEmail(), 0);
		return new ModelAndView("redirect:/?userId=" + u.getUserid());
	}
}
