package ar.edu.itba.pawddit.webapp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import ar.edu.itba.pawddit.model.User;
import ar.edu.itba.pawddit.services.GroupService;
import ar.edu.itba.pawddit.services.PostService;
import ar.edu.itba.pawddit.services.UserService;
import ar.edu.itba.pawddit.webapp.exceptions.UserNotFoundException;

@Controller
public class IndexController {

	@Autowired
	private UserService us;
	
	@Autowired
	private GroupService gs;
	
	@Autowired
	private PostService ps;

	@RequestMapping("/")
	public ModelAndView index(@RequestParam(value = "userId", required = false) final Integer id)
	{
		final ModelAndView mav = new ModelAndView("index");
		if (id == null) {
			mav.addObject("posts", ps.findAll());
		}
		else {
			final User u = us.findById(id).orElseThrow(UserNotFoundException::new);
			mav.addObject("user", u);
			mav.addObject("posts", ps.findBySubscriptions(u));
		}
		return mav;
	}

}
