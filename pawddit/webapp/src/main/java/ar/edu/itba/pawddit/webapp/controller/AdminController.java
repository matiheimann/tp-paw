package ar.edu.itba.pawddit.webapp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import ar.edu.itba.pawddit.model.User;
import ar.edu.itba.pawddit.services.UserService;
import ar.edu.itba.pawddit.webapp.exceptions.UserNotFoundException;

@Controller
@RequestMapping("/admin")
public class AdminController {
	
	@Autowired
	private UserService us;
	
	@RequestMapping(value =  "/deleteUser/{username}", method = { RequestMethod.POST })
	public ModelAndView deletePost(@PathVariable final String username) {
		final User user = us.findByUsername(username).orElseThrow(UserNotFoundException::new);
		
		us.deleteUser(user);
		
		final ModelAndView mav = new ModelAndView("redirect:/");
		return mav;
	}
	
}
