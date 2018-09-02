package ar.edu.itba.pawddit.webapp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import ar.edu.itba.pawddit.model.User;
import ar.edu.itba.pawddit.services.UserService;

@Controller
public class HelloWorldController {

	@Autowired
	@Qualifier("userServiceImpl")
	private UserService us;

	@RequestMapping("/")
	public ModelAndView index(@RequestParam(value = "userId", required = true) final int id) {
		final ModelAndView mav = new ModelAndView("index");
		mav.addObject("user", us.findById(id).orElseThrow(UserNotFoundException::new));
		return mav;
	}
	
	@RequestMapping("/welcome")
	public ModelAndView welcome() {
		final ModelAndView mav = new ModelAndView("welcome");
		return mav;
	}
	
	@RequestMapping("/login")
	public ModelAndView login() {
		final ModelAndView mav = new ModelAndView("login");
		return mav;
	}
	
	@RequestMapping("/register")
	public ModelAndView register() {
		final ModelAndView mav = new ModelAndView("register");
		return mav;
	}
		
	@RequestMapping("/create")
	public ModelAndView create(@RequestParam(value = "name", required = true) final String username) {
		final User u = us.create(username, "a", "a", 0);
		return new ModelAndView("redirect:/?userId=" + u.getUserid());
	}
}
