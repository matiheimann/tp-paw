package ar.edu.itba.pawddit.webapp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import ar.edu.itba.pawddit.services.UserService;

@Controller
public class HelloWorldController {
	
	@Autowired
	@Qualifier("userServiceImpl")
	private UserService us;
	
	@RequestMapping("/user/{userId}")
	public ModelAndView helloWorld(@PathVariable("userId") String userId, @RequestParam(defaultValue = "John") String firstName, @RequestParam(defaultValue = "Doe") String lastName) {
		final ModelAndView mav = new ModelAndView("index");
		mav.addObject("greeting", firstName + " " + lastName + " (" + userId + ")");
		return mav;
	}
}
