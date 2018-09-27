package ar.edu.itba.pawddit.webapp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import ar.edu.itba.pawddit.model.User;
import ar.edu.itba.pawddit.services.PostService;
import ar.edu.itba.pawddit.services.UserService;
import ar.edu.itba.pawddit.webapp.exceptions.UserNotFoundException;

@Controller
public class IndexController {

	@Autowired
	private UserService us;

	@Autowired
	private PostService ps;

	@RequestMapping("/")
	public ModelAndView index(@RequestParam(defaultValue = "1", value="page") int page)
	{
		final ModelAndView mav = new ModelAndView("index");
		final Authentication auth = SecurityContextHolder.getContext().getAuthentication();

		if (auth.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_ANONYMOUS"))) {
			mav.addObject("posts", ps.findAll(5, (page-1)*5));
		}
		else {
			final User u = us.findByUsername(auth.getName()).orElseThrow(UserNotFoundException::new);
			mav.addObject("user", u);
			mav.addObject("posts", ps.findBySubscriptions(u, 5, (page-1)*5));
		}
		return mav;
	}

	@RequestMapping("/all")
	public ModelAndView all(@RequestParam(defaultValue = "1", value="page") int page)
	{
		final Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		final ModelAndView mav = new ModelAndView("index");
		final User u = us.findByUsername(auth.getName()).orElseThrow(UserNotFoundException::new);
		mav.addObject("user", u);
		mav.addObject("posts", ps.findAll(5, (page-1)*5));
		return mav;
	}

}
